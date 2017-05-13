package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Sphere;
import config.Options;
import de.lessvoid.nifty.Nifty;
import cells.Player;
import geometry.Position;
import world.World;

import java.util.Observable;
import java.util.Observer;

public class VisualGUI extends SimpleApplication implements Observer {
    private static final float SCALE = Options.getInstance().getFloat("SCALE");
    private static final int CURSOR_DISTANCE = Options.getInstance().getInt("CURSOR_DISTANCE");
    private static final int CURSOR_SAMPLE_RATE = 2;
    private static VisualGUI app;
    private final EventHandlers eventHandlers = new EventHandlers(this);
    private final Environment environment = new Environment(this);
    private Spatial cursor;
    private Spatial cursorDebug;

    /**
     * Singleton pattern
     * @return the current active app of the world
     */
    public static VisualGUI getInstance() {
        if (app == null){
            app = new VisualGUI();
        }
        return app;
    }

    public static void main(String[] args) {
        app = new VisualGUI();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        //setDisplayFps(false);
        //getFlyByCamera().setEnabled(false);

        flyCam.setMoveSpeed(Options.getInstance().getInt("MOVE_SPEED"));

        environment.initializeEnvironment();
        eventHandlers.initializeEventHandlers();
        if (Options.getInstance().getBoolean("ENABLE_HUD")) {
            initializeHUD();
        }
        if (Options.getInstance().getBoolean("ENABLE_CROSS_HAIR")) {
            initCrossHairs();
        }
        if (Options.getInstance().getBoolean("ENABLE_CURSOR_DEBUG")) {
            initCursorDebug();
        }

        initCursor();

        Player.getInstance().addObserver(this);
    }

    private void initCursorDebug() {
        Sphere cursorSphere = new Sphere(8, 8, SCALE / 10);
        cursorDebug = new Geometry("Cursor", cursorSphere);
        Material unshaded = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.Black);
        cursorDebug.setMaterial(unshaded);

        cursorDebug.setLocalTranslation(
                Player.getInstance().getSelectedBlock().x * SCALE,
                Player.getInstance().getSelectedBlock().y * SCALE,
                Player.getInstance().getSelectedBlock().z * SCALE);

        rootNode.attachChild(cursorDebug);
    }

    private void initializeHUD() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();

        HUDController hudController = new HUDController(eventHandlers, cam);

        nifty.fromXml("Interface/hud.xml", "hud", hudController);
        guiViewPort.addProcessor(niftyDisplay);
    }

    /** A centred plus sign to help the player aim.
     * Credit: jMonkeyEngine tutorials*/
    private void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
        guiNode.attachChild(ch);
    }

    private void initCursor() {
        WireBox cursorBox = new WireBox(
                SCALE/2 * 1.01f,
                SCALE/2 * 1.01f,
                SCALE/2 * 1.01f);
        cursor = new Geometry("Cursor", cursorBox);
        Material unshaded = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.Brown);
        cursor.setMaterial(unshaded);

        cursor.setLocalTranslation(
                Player.getInstance().getSelectedBlock().x * SCALE,
                Player.getInstance().getSelectedBlock().y * SCALE,
                Player.getInstance().getSelectedBlock().z * SCALE);

        rootNode.attachChild(cursor);
    }


    @Override
    public void simpleUpdate(float tpf) {
        if (!eventHandlers.isPaused()) {
            environment.update(tpf);
        }

        updateCursor();
        if (Options.getInstance().getBoolean("ENABLE_CURSOR_DEBUG")) {
            updateCursorDebug();
        }
    }

    private void updateCursorDebug() {
        Vector3f currentPosition = cam.getLocation()
                .add(cam.getDirection().mult(CURSOR_DISTANCE * SCALE));

        cursorDebug.setLocalTranslation(currentPosition);
    }

    private void updateCursor() {
        Position currentPosition = Position.fromUIVector(cam.getLocation()
                .add(cam.getDirection().mult(CURSOR_DISTANCE * SCALE)));
        Position nextPosition = Position.fromUIVector(cam.getLocation()
                .add(cam.getDirection().mult(CURSOR_DISTANCE * SCALE)));

        for (int i = 0; i < CURSOR_DISTANCE * CURSOR_SAMPLE_RATE; i++) {
            currentPosition = Position.fromUIVector(cam.getLocation()
                    .add(cam.getDirection().mult((float) i / CURSOR_SAMPLE_RATE * SCALE)));
            nextPosition = Position.fromUIVector(cam.getLocation()
                    .add(cam.getDirection().mult((float) (i + 1) / CURSOR_SAMPLE_RATE * SCALE)));

            if (World.getInstance().containsCellAt(nextPosition)) {
                break;
            }
            nextPosition = currentPosition;
        }

        Player.getInstance().setSelectedBlock(nextPosition);
        Player.getInstance().setSelectedBlockFace(currentPosition);

        cursor.setLocalTranslation(Player.getInstance().getSelectedBlock().getUIVector());
    }

    /**
     * Stop the world thread when the application closes
     */
    @Override
    public void destroy() {
        World.getInstance().interrupt();
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    public int getNumberOfParallelepipeds() {
        return environment.getNumberOfParallelepipeds();
    }

    public long getVolumeOfParallelepipeds() {
        return environment.getVolumeOfParallelepipeds();
    }
}

