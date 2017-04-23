package ui;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.Position;
import model.RandomWalkCell;
import model.World;

public class EventHandlers {
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
        }
    };
    private ActionListener addCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = getCurrentSelection();
            World.getInstance().add(new RandomWalkCell(currentSelection));
        }
    };
    private ActionListener removeCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = getCurrentSelection();
            World.getInstance().remove(currentSelection);
        }
    };
    private ActionListener moveCursorUPActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 1, 0);
        }
    };
    private ActionListener moveCursorDOWNActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, -1, 0);
        }
    };
    private ActionListener moveCursorPXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(1, 0, 0);
        }
    };
    private ActionListener moveCursorNXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(-1, 0, 0);
        }
    };
    private ActionListener moveCursorPZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, 1);
        }
    };
    private ActionListener moveCursorNZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, -1);
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
    }

    /**
     * Produces currently selected cell position
     *
     * @return position
     */
    Position getCurrentSelection() {
        return cursorPosition;
    }
}