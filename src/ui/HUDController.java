package ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import exceptions.NiftyNotInitializedException;
import model.World;
import sun.java2d.pipe.TextRenderer;

import javax.annotation.Nonnull;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Updates the in-game HUD
 */
public class HUDController implements ScreenController {
    private Nifty nifty;

    HUDController() {}

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;

        // find old text
        Element niftyElement = nifty.getCurrentScreen().findElementById("cell_1");
        // swap old with new text
        niftyElement.getRenderer(de.lessvoid.nifty.elements.render.TextRenderer.class).setText("124");
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

}
