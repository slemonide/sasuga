package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
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
    private final EventHandlers eventHandlers = new EventHandlers(this);
    private final Environment environment = new Environment(this);
    private Vector3f lastGaze;
    private Vector3f up;
    private Vector3f left;
    private Spatial cursor;

    public static void main(String[] args) {
        VisualGUI app = new VisualGUI();
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
                Player.getInstance().getCursor().getComponent(0) * Environment.SCALE,
                Player.getInstance().getCursor().getComponent(1) * Environment.SCALE,
                Player.getInstance().getCursor().getComponent(2) * Environment.SCALE);

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
        cursor.setLocalTranslation(
                Player.getInstance().getCursor().getComponent(0) * Environment.SCALE,
                Player.getInstance().getCursor().getComponent(1) * Environment.SCALE,
                Player.getInstance().getCursor().getComponent(2) * Environment.SCALE);
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

