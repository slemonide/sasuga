package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import model.Position;
import model.World;

public class VisualGUI extends SimpleApplication {
    private final EventHandlers eventHandlers = new EventHandlers(this);
    private final Environment environment = new Environment(this);

    public static void main(String[] args) {
        VisualGUI app = new VisualGUI();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10);

        eventHandlers.setCursorPosition(new Position());
        environment.initializeEnvironment();
        eventHandlers.initializeEventHandlers();
        initializeHUD();
    }

    private void initializeHUD() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("assets/Interface/hud.xml", "hud", new HUDController());
        // nifty.fromXml("Interface/helloworld.xml", "start", new MySettingsScreen(data));
        // attach the Nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
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
}
