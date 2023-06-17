package graphic.hud.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import graphic.hud.*;
import java.util.HashSet;
import tools.Constants;
import tools.Point;

public class Menu<T extends Actor> extends ScreenController<T> {

    private String headline;
    private HashSet<IMenuItem> items;

    /**
     * The Menu constructor. Builds a new menu screen from a given headline and a set of screen
     * elements. Creates a Screencontroller with a ScalingViewport which stretches the
     * ScreenElements on resize.
     *
     * @param title the menu headline
     * @param elements the set of elements that make up the menu screen
     */
    public Menu(String title, HashSet<IMenuItem> elements) {
        this(new SpriteBatch(), title, elements);
    }

    /**
     * The Menu constructor. Builds a new menu screen from a given headline and a set of screen
     * elements. Creates a Screencontroller with a ScalingViewport which stretches the
     * ScreenElements on resize.
     *
     * @param batch the batch which should be used to draw with
     * @param title the menu headline
     * @param elements the set of elements that make up the menu screen
     */
    public Menu(SpriteBatch batch, String title, HashSet<IMenuItem> elements) {
        super(batch);

        headline = title;
        add((T) setUpHeadline(headline));

        items = elements;

        buildMenu(items); // adding all items to the ScreenController

        hideMenu();
    }

    private ScreenText setUpHeadline(String title) {
        ScreenText menuTitle =
                new ScreenText(
                        title,
                        new Point(0.0f, 0.0f),
                        3,
                        new LabelStyleBuilder((FontBuilder.DEFAULT_FONT))
                                .setFontcolor(Color.RED)
                                .build());

        menuTitle.setFontScale(2.5f);
        menuTitle.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - menuTitle.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f + menuTitle.getHeight(),
                Align.center | Align.bottom);

        return menuTitle;
    }

    private void buildMenu(HashSet<IMenuItem> items) {
        for (IMenuItem item : items) {
            add((T) item);
        }
    }

    /** Makes the menu visible. */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** Hides the menu. */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    private void addItem(IMenuItem item) {
        items.add(item);
    }

    private void removeItem(IMenuItem item) {
        items.remove(item);
    }
}
