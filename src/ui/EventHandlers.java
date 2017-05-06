package ui;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import model.*;

import java.util.Observable;

public class EventHandlers extends Observable {
    private static final long ROTATION_DELAY = 20; // in ms
    private final VisualGUI visualGUI;
    private boolean isPaused = false;

    boolean isPaused() {
        return isPaused;
    }

    private ActionListener pauseActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            isPaused = !isPaused;

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener addCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Player.getInstance().getInventory().getSelectedItem().use();

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener removeCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = Player.getInstance().getSelectedBlock();
            World.getInstance().remove(currentSelection);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener inventorySelectionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Player.getInstance().getInventory().setSelectedSlot(Integer.valueOf(name));

            setChanged();
            notifyObservers();
        }
    };
    private AnalogListener rotateCCWActionListener = (name, value, tpf) -> {
        try {
            Thread.sleep(ROTATION_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Player.getInstance().rotateCounterClockWise();
    };
    private AnalogListener rotateCWActionListener = (name, value, tpf) -> {
        try {
            Thread.sleep(ROTATION_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Player.getInstance().rotateClockWise();
    };

    EventHandlers(VisualGUI visualGUI) {
        this.visualGUI = visualGUI;
    }

    void initializeEventHandlers() {
        visualGUI.getInputManager().addMapping("PAUSE", new KeyTrigger(KeyInput.KEY_SPACE));
        visualGUI.getInputManager().addListener(pauseActionListener, "PAUSE");

        visualGUI.getInputManager().addMapping("ADD_CELL", new KeyTrigger(KeyInput.KEY_RETURN));
        visualGUI.getInputManager().addMapping("ADD_CELL",
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        visualGUI.getInputManager().addListener(addCellActionListener, "ADD_CELL");

        visualGUI.getInputManager().addMapping("REMOVE_CELL", new KeyTrigger(KeyInput.KEY_L));
        visualGUI.getInputManager().addMapping("REMOVE_CELL",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        visualGUI.getInputManager().addListener(removeCellActionListener, "REMOVE_CELL");

        // Inventory selection
        visualGUI.getInputManager().addMapping("1", new KeyTrigger(KeyInput.KEY_1));
        visualGUI.getInputManager().addMapping("2", new KeyTrigger(KeyInput.KEY_2));
        visualGUI.getInputManager().addMapping("3", new KeyTrigger(KeyInput.KEY_3));
        visualGUI.getInputManager().addMapping("4", new KeyTrigger(KeyInput.KEY_4));
        visualGUI.getInputManager().addMapping("5", new KeyTrigger(KeyInput.KEY_5));
        visualGUI.getInputManager().addMapping("6", new KeyTrigger(KeyInput.KEY_6));
        visualGUI.getInputManager().addMapping("7", new KeyTrigger(KeyInput.KEY_7));
        visualGUI.getInputManager().addMapping("8", new KeyTrigger(KeyInput.KEY_8));
        visualGUI.getInputManager().addMapping("9", new KeyTrigger(KeyInput.KEY_9));
        visualGUI.getInputManager().addMapping("10", new KeyTrigger(KeyInput.KEY_0));
        visualGUI.getInputManager().addListener(inventorySelectionListener,
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        visualGUI.getInputManager().addMapping("Rotate CCW", new KeyTrigger(KeyInput.KEY_SLASH));
        visualGUI.getInputManager().addListener(rotateCCWActionListener, "Rotate CCW");

        visualGUI.getInputManager().addMapping("Rotate CW", new KeyTrigger(KeyInput.KEY_RSHIFT));
        visualGUI.getInputManager().addListener(rotateCWActionListener, "Rotate CW");
    }
}