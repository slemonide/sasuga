package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import config.Options;
import de.lessvoid.nifty.Nifty;
import model.Player;
import model.Position;
import model.World;

import java.util.Observable;
import java.util.Observer;

import static config.Options.CURSOR_DISTANCE;
import static config.Options.SCALE;

public class VisualGUI extends SimpleApplication implements Observer {
    private static VisualGUI app;
    private final EventHandlers eventHandlers = new EventHandlers(this);
    private final Environment environment = new Environment(this);
    private Spatial cursor;

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
        setDisplayFps(false);
        //getFlyByCamera().setEnabled(false);

        flyCam.setMoveSpeed(10);

        environment.initializeEnvironment();
        eventHandlers.initializeEventHandlers();
        if (Options.ENABLE_HUD) {
            initializeHUD();
        }
        if (Options.ENABLE_CROSS_HAIR) {
            initCrossHairs();
        }
        initCursor();

        Player.getInstance().addObserver(this);
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
        //Sphere sphere = new Sphere(8, 8, 0.125f);
        WireBox cursorBox = new WireBox(
                SCALE/2 * 1.01f,
                SCALE/2 * 1.01f,
                SCALE/2 * 1.01f);
        cursor = new Geometry("Cursor", cursorBox);
        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        cursor.setMaterial(mat);

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
    }

    private void updateCursor() {
        Position currentPosition = Position.fromUIVector(cam.getLocation()
                .add(cam.getDirection().mult(CURSOR_DISTANCE)));
        Position nextPosition = Position.fromUIVector(cam.getLocation()
                .add(cam.getDirection().mult(CURSOR_DISTANCE)));

        int cellsToCheck = (int) (CURSOR_DISTANCE / SCALE);
        for (int i = 0; i < cellsToCheck; i++) {
            currentPosition = Position.fromUIVector(cam.getLocation()
                    .add(cam.getDirection().mult(i * SCALE)));
            nextPosition = Position.fromUIVector(cam.getLocation()
                    .add(cam.getDirection().mult((i + 1) * SCALE)));

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
}

