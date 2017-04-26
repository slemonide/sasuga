package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
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

        Player.getInstance().addObserver(this);
    }

    private void initializeHUD() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();

        HUDController hudController = new HUDController(eventHandlers, cam);

        nifty.fromXml("assets/Interface/hud.xml", "hud", hudController);
        guiViewPort.addProcessor(niftyDisplay);

        // initialize observers
        Player.getInstance().addObserver(hudController);
        World.getInstance().addObserver(hudController);
        eventHandlers.addObserver(hudController);
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

    @Override
    public void simpleUpdate(float tpf) {
        if (!eventHandlers.isPaused()) {
            environment.update(tpf);
        }
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
        rotateCamera();
        rotateFloor();
    }

    private void rotateFloor() {
        // stub
    }

    private void rotateCamera() {
        if (lastGaze == null || lastGaze.distance(cam.getDirection()) >= 0.01f) {
            lastGaze = cam.getDirection();
            up = cam.getUp();
            left = cam.getLeft();
        }
        
        cam.lookAtDirection(cam.getDirection(),
                up.mult((float) Math.cos(Player.getInstance().getRotation()))
                        .add(left.mult((float) Math.sin(Player.getInstance().getRotation()))));
    }
}

