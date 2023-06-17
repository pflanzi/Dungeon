package graphic.hud.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;

import java.util.HashSet;

public abstract class Menu<T extends Actor> extends ScreenController<T> {

    private String headline;
    private HashSet<IMenuItem> items;

    /**
     * Creates a Screencontroller with a ScalingViewport which stretches the ScreenElements on
     * resize
     *
     * @param batch the batch which should be used to draw with
     */
    public Menu(SpriteBatch batch) {
        super(batch);
        buildMenu();
    }

    public abstract void buildMenu();

    public void addItem(IMenuItem item) {
        items.add(item);
    }

    public void removeItem(IMenuItem item) {
        items.remove(item);
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
