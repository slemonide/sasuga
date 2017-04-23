package ui;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.Player;
import model.Position;
import model.RandomWalkCell;
import model.World;

import java.util.Observable;

public class EventHandlers extends Observable {
    private final VisualGUI visualGUI;
    private boolean isPaused = false;

    boolean isPaused() {
        return isPaused;
    }

    private Position cursorPosition;

    public Position getCursorPosition() {
        return cursorPosition;
    }

    void setCursorPosition(Position cursorPosition) {
        this.cursorPosition = cursorPosition;
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
            Position currentSelection = getCurrentSelection();
            World.getInstance().add(new RandomWalkCell(currentSelection));

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener removeCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = getCurrentSelection();
            World.getInstance().remove(currentSelection);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorUPActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 1, 0);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorDOWNActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, -1, 0);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorPXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(1, 0, 0);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorNXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(-1, 0, 0);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorPZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, 1);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener moveCursorNZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, -1);

            setChanged();
            notifyObservers();
        }
    };
    private ActionListener inventorySelectionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Player.getInstance().setSelectedInventorySlot(Integer.valueOf(name));
        }
    };

    EventHandlers(VisualGUI visualGUI) {
        this.visualGUI = visualGUI;
    }

    void initializeEventHandlers() {
        visualGUI.getInputManager().addMapping("PAUSE", new KeyTrigger(KeyInput.KEY_SPACE));
        visualGUI.getInputManager().addListener(pauseActionListener, "PAUSE");

        visualGUI.getInputManager().addMapping("ADD_CELL", new KeyTrigger(KeyInput.KEY_RETURN));
        visualGUI.getInputManager().addListener(addCellActionListener, "ADD_CELL");

        visualGUI.getInputManager().addMapping("REMOVE_CELL", new KeyTrigger(KeyInput.KEY_L));
        visualGUI.getInputManager().addListener(removeCellActionListener, "REMOVE_CELL");

        visualGUI.getInputManager().addMapping("UP", new KeyTrigger(KeyInput.KEY_Y));
        visualGUI.getInputManager().addListener(moveCursorUPActionListener, "UP");

        visualGUI.getInputManager().addMapping("DOWN", new KeyTrigger(KeyInput.KEY_G));
        visualGUI.getInputManager().addListener(moveCursorDOWNActionListener, "DOWN");

        visualGUI.getInputManager().addMapping("PX", new KeyTrigger(KeyInput.KEY_T));
        visualGUI.getInputManager().addListener(moveCursorPXActionListener, "PX");

        visualGUI.getInputManager().addMapping("NX", new KeyTrigger(KeyInput.KEY_F));
        visualGUI.getInputManager().addListener(moveCursorNXActionListener, "NX");

        visualGUI.getInputManager().addMapping("PZ", new KeyTrigger(KeyInput.KEY_U));
        visualGUI.getInputManager().addListener(moveCursorPZActionListener, "PZ");

        visualGUI.getInputManager().addMapping("NZ", new KeyTrigger(KeyInput.KEY_H));
        visualGUI.getInputManager().addListener(moveCursorNZActionListener, "NZ");

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
    }

    /**
     * Produces currently selected cell position
     *
     * @return position
     */
    private Position getCurrentSelection() {
        return cursorPosition;
    }
}