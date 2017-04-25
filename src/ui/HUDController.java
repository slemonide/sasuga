package ui;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import model.Player;
import model.World;

import javax.annotation.Nonnull;
import java.util.Observable;
import java.util.Observer;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Updates the in-game HUD
 */
public class HUDController implements ScreenController, Observer {
    private static final Color TRANSPARENT_COLOR = new Color("#0000");
    private Nifty nifty;
    private Player player;
    private EventHandlers eventHandlers;

    private Color pausePanelColor;
    private Color pauseTextColor;
    private Color selectedInventorySlotColor = new Color("#00f8");
    private Color unselectedInventorySlotColor = TRANSPARENT_COLOR;

    private int currentlySelectedInventorySlot;
    private Camera cam;

    HUDController(EventHandlers eventHandlers, Camera cam) {
        this.eventHandlers = eventHandlers;
        this.cam = cam;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
        this.player = Player.getInstance();

        readColorsFromTheNifty();
        currentlySelectedInventorySlot = Player.getInstance().getSelectedInventorySlot();

        update(null, null);
    }

    private void readColorsFromTheNifty() {
        Element pausePanel = nifty.getCurrentScreen().findElementById("pause_panel");
        pausePanelColor = pausePanel.getRenderer(PanelRenderer.class).getBackgroundColor();

        Element pause = nifty.getCurrentScreen().findElementById("pause");
        pauseTextColor = pause.getRenderer(TextRenderer.class).getColor();
    }

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}

    @Override
    public void update(Observable o, Object arg) {
        updateGeneration();
        updatePopulation();
        updateTickTime();
        updateGrowthRate();

        updateLeft();
        updateUp();
        updateGaze();
        updateRotation();
        updatePosition();

        updateHealth();
        updateStrength();
        updateAgility();
        updateHunger();
        updateInventoryItems();
        updateSelectedInventorySlot();

        updatePause();
    }

    private void updatePause() {
        if (eventHandlers.isPaused()) {
            setPaused();
        } else {
            setUnPaused();
        }
    }

    private void setPaused() {
        Element pausePanel = nifty.getCurrentScreen().findElementById("pause_panel");
        pausePanel.getRenderer(PanelRenderer.class).setBackgroundColor(pausePanelColor);

        Element pause = nifty.getCurrentScreen().findElementById("pause");
        pause.getRenderer(TextRenderer.class).setColor(pauseTextColor);
    }

    private void setUnPaused() {
        Element pausePanel = nifty.getCurrentScreen().findElementById("pause_panel");
        pausePanel.getRenderer(PanelRenderer.class).setBackgroundColor(TRANSPARENT_COLOR);

        Element pause = nifty.getCurrentScreen().findElementById("pause");
        pause.getRenderer(TextRenderer.class).setColor(TRANSPARENT_COLOR);
    }

    private void updateGeneration() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("generation");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Generation", World.getInstance().getGeneration()));
    }

    private void updatePopulation() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("population");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Population", World.getInstance().getPopulationSize(), "cells"));
    }

    private void updateTickTime() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("tick_time");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Tick time", World.getInstance().getTickTime(), "s"));
    }

    private void updateGrowthRate() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("growth_rate");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Growth rate", World.getInstance().getGrowthRate(), "cells/tick"));
    }

    private void updateLeft() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("left_direction");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Left", cam.getLeft()));
    }

    private void updateUp() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("up_direction");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Up", cam.getUp()));
    }

    private void updateGaze() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("gaze_direction");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Gaze", cam.getDirection()));
    }

    private void updateRotation() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("rotation");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Rotation", Player.getInstance().getRotation()));
    }

    private void updatePosition() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("position");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Position", cam.getLocation()));
    }



    private void updateHealth() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("health");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Health", player.getHealth(), "%"));
    }

    private void updateStrength() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("strength");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Strength", player.getStrength(), "%"));
    }

    private void updateAgility() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("agility");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Agility", player.getAgility(), "%"));
    }

    private void updateHunger() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("hunger");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Hunger", player.getHunger(), "%"));
    }

    private String getLabel(String property, Object value, String units) {
        return property + ": " + value + " " + units;
    }
    private String getLabel(String property, Object value) {
        return property + ": " + value;
    }

    private void updateInventoryItems() {
        Element niftyElement;

        for (int i = 0; i < Player.INVENTORY_SIZE; i++) {
            niftyElement = nifty.getCurrentScreen().findElementById("cell_" + i);

            if (Player.getInstance().getInventoryItem(i) != null) {
                niftyElement.getRenderer(TextRenderer.class)
                        .setText(Player.getInstance().getInventoryItem(i).getName());
            } else {
                niftyElement.getRenderer(TextRenderer.class)
                        .setText("...");
            }
        }
    }


    /**
     * Updates displayed selection in inventory window:
     *
     * Unhighlights all unselected slots, and highlights the selected slot.
     */
    private void updateSelectedInventorySlot() {
        Element unselectedSlot = nifty.getCurrentScreen()
                .findElementById("cell_" + currentlySelectedInventorySlot);
        unselectedSlot.getRenderer(PanelRenderer.class).setBackgroundColor(unselectedInventorySlotColor);

        currentlySelectedInventorySlot = Player.getInstance().getSelectedInventorySlot();

        Element selectedSlot = nifty.getCurrentScreen()
                .findElementById("cell_" + currentlySelectedInventorySlot);
        selectedSlot.getRenderer(PanelRenderer.class).setBackgroundColor(selectedInventorySlotColor);
    }
}
