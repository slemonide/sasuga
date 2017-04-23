package ui;

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
    private Nifty nifty;
    private Player player;
    private EventHandlers eventHandlers;

    HUDController(EventHandlers eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
        this.player = Player.getInstance();

        update(null, null);
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

        updateHealth();
        updateStrength();
        updateAgility();
        updateHunger();
        updateCells();

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
        pausePanel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color("#0fff"));

        Element pause = nifty.getCurrentScreen().findElementById("pause");
        pause.getRenderer(TextRenderer.class).setColor(new Color("#f00f"));
    }

    private void setUnPaused() {
        Element pausePanel = nifty.getCurrentScreen().findElementById("pause_panel");
        pausePanel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color("#0ff0"));

        Element pause = nifty.getCurrentScreen().findElementById("pause");
        pause.getRenderer(TextRenderer.class).setColor(new Color("#f000"));
    }

    private void updateGeneration() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("generation");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Generation", World.getInstance().getGeneration()));
    }

    private void updatePopulation() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("population");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getLabel("Population", World.getInstance().getPopulationSize()));
    }

    private void updateTickTime() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("tick_time");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getSecondsLabel("Tick time", World.getInstance().getTickTime()));
    }

    private void updateHealth() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("health");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getPercentageLabel("Health", player.getHealth()));
    }

    private void updateStrength() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("strength");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getPercentageLabel("Strength", player.getStrength()));
    }

    private void updateAgility() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("agility");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getPercentageLabel("Agility", player.getAgility()));
    }

    private void updateHunger() {
        Element niftyElement = nifty.getCurrentScreen().findElementById("hunger");
        niftyElement.getRenderer(TextRenderer.class)
                .setText(getPercentageLabel("Hunger", player.getHunger()));
    }

    private String getLabel(String property, int value) {
        return property + ": " + value;
    }
    private String getPercentageLabel(String property, int value) {
        return property + ": " + value + "%";
    }
    private String getSecondsLabel(String property, double value) {
        return property + ": " + value + " s";
    }

    private void updateCells() {

    }
}
