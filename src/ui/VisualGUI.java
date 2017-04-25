package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import model.Player;
import model.Position;
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

        eventHandlers.setCursorPosition(new Position());
        environment.initializeEnvironment();
        eventHandlers.initializeEventHandlers();
        initializeHUD();

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

