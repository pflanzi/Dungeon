package graphic.hud.menus;

import graphic.hud.ScreenButton;
import graphic.hud.TextButtonListener;
import tools.Point;

public class MenuButton extends ScreenButton implements IMenuItem {
    public MenuButton(
            String text, Point position, TextButtonListener listener, TextButtonStyle style) {
        super(text, position, listener, style);
    }

    public MenuButton(String text, Point position, TextButtonListener listener) {
        super(text, position, listener);
    }

    @Override
    public void executeAction() {
        // called when the text button listener is called

    }
}
