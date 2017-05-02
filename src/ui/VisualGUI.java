package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import de.lessvoid.nifty.Nifty;
import model.Player;
import model.World;

import java.util.Observable;
import java.util.Observer;

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
        initializeHUD();
        initCrossHairs();
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
    protected void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
        guiNode.attachChild(ch);
    }

    private void initCursor() {
        Sphere sphere = new Sphere(8, 8, 0.125f);
        cursor = new Geometry("Sphere", sphere);
        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        cursor.setMaterial(mat);

        cursor.setLocalTranslation(
                Player.getInstance().getCursor().x * Environment.SCALE,
                Player.getInstance().getCursor().y * Environment.SCALE,
                Player.getInstance().getCursor().z * Environment.SCALE);

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
        cursor.setLocalTranslation(Player.getInstance().getCursor().getUIVector());
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

