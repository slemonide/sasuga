package ui;

import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import model.Player;
import model.World;

import javax.annotation.Nonnull;
import javax.xml.soap.Text;
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
    private static final Color SELECTION_COLOR = new Color("#00f8");
    private static final String EMPTY_INVENTORY_SLOT_VALUE = "...";
    private Nifty nifty;
    private EventHandlers eventHandlers;

    private Color pausePanelColor;
    private Color pauseTextColor;

    private int currentlySelectedInventorySlot;
    private Camera cam;

    HUDController(EventHandlers eventHandlers, Camera cam) {
        this.eventHandlers = eventHandlers;
        this.cam = cam;

        Player.getInstance().addObserver(this);
        World.getInstance().addObserver(this);
        eventHandlers.addObserver(this);
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;

        readColorsFromTheNifty();
        currentlySelectedInventorySlot = Player.getInstance().getInventory().getSelectedSlot();

        update(null, null);
    }

    private void readColorsFromTheNifty() {
        if (nifty == null) {
            return;
        }

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
        // I kept id and property separate for the sake of ease of localization
        abstractUpdate("generation", "Generation", World.getInstance().getGeneration());
        abstractUpdate("population", "Population", World.getInstance().getPopulationSize(), "cells");
        abstractUpdate("tick_time", "Tick time", World.getInstance().getTickTime(), "s");
        abstractUpdate("growth_rate", "Growth rate", World.getInstance().getGrowthRate(), "cells/tick");
        abstractUpdate("number_of_parallelepipeds", "Parallelepipeds",
                VisualGUI.getInstance().getNumberOfParallelepipeds());
        abstractUpdate("total_volume_of_parallelepipeds", "Volume",
                VisualGUI.getInstance().getVolumeOfParallelepipeds(), "m^3");

        abstractUpdate("left_direction","Left", cam.getLeft());
        abstractUpdate("up_direction","Up", cam.getUp());
        abstractUpdate("gaze_direction","Gaze", cam.getDirection());
        abstractUpdate("rotation","Rotation", Player.getInstance().getRotation());
        abstractUpdate("position","Position", cam.getLocation());

        abstractUpdate("health","Health", Player.getInstance().getHealth(), "%");
        abstractUpdate("strength","Strength", Player.getInstance().getStrength(), "%");
        abstractUpdate("agility","Agility", Player.getInstance().getAgility(), "%");
        abstractUpdate("hunger","Hunger", Player.getInstance().getHunger(), "%");
        updateInventoryItems();
        updateSelectedInventorySlot();

        updatePause();
    }

    // 2nd degree helpers
    private void updateText(String id, String value) {
        if (nifty == null) {
            return;
        }

        Element niftyElement = nifty.getCurrentScreen().findElementById(id);
        niftyElement.getRenderer(TextRenderer.class)
                .setText(value);
    }
    private void abstractUpdate(String id, String property, Object value, String units) {
        updateText(id, getLabel(property, value, units));
    }
    private void abstractUpdate(String id, String property, Object value) {
        abstractUpdate(id, property, value, "");
    }
    private void updateBackgroundColor(String id, Color color) {
        if (nifty == null) {
            return;
        }

        Element pausePanel = nifty.getCurrentScreen().findElementById(id);
        pausePanel.getRenderer(PanelRenderer.class).setBackgroundColor(color);
    }
    private void updateColor(String id, Color color) {
        if (nifty == null) {
            return;
        }

        Element pause = nifty.getCurrentScreen().findElementById(id);
        pause.getRenderer(TextRenderer.class).setColor(color);
    }
    private String getLabel(String property, Object value, String units) {
        return property + ": " + value + " " + units;
    }

    // 1st degree helpers
    private void updatePause() {
        if (eventHandlers.isPaused()) {
            setPaused();
        } else {
            setUnPaused();
        }
    }

    private void setPaused() {
        updateBackgroundColor("pause_panel", pausePanelColor);
        updateColor("pause", pauseTextColor);
    }

    private void setUnPaused() {
        updateBackgroundColor("pause_panel", TRANSPARENT_COLOR);
        updateColor("pause", TRANSPARENT_COLOR);
    }

    private void updateInventoryItems() {
        for (int i = 0; i < Player.INVENTORY_SIZE; i++) {
            if (Player.getInstance().getInventory().getInventoryItem(i) != null) {
                updateText("cell_" + i, Player.getInstance().getInventory().getInventoryItem(i).getName());
            } else {
                updateText("cell_" + i, EMPTY_INVENTORY_SLOT_VALUE);
            }
        }
    }

    /**
     * Updates displayed selection in inventory window:
     *
     * Highlights the selected slot.
     */
    private void updateSelectedInventorySlot() {
        updateBackgroundColor("cell_" + currentlySelectedInventorySlot, TRANSPARENT_COLOR);
        currentlySelectedInventorySlot = Player.getInstance().getInventory().getSelectedSlot();
        updateBackgroundColor("cell_" + currentlySelectedInventorySlot, SELECTION_COLOR);
    }
}
