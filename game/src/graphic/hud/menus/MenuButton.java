package graphic.hud.menus;

import graphic.hud.ScreenButton;
import graphic.hud.TextButtonListener;
import tools.Point;

public class MenuButton extends ScreenButton implements IMenuItem {

    /**
     * Creates a ScreenButton which can be used with the ScreenController.
     *
     * @param text the text for the ScreenButton
     * @param position the Position where the ScreenButton should be placed 0,0 is bottom left
     * @param listener the TextButtonListener which handles the button press
     * @param style the TextButtonStyle to use
     */
    public MenuButton(
            String text, Point position, TextButtonListener listener, TextButtonStyle style) {
        super(text, position, listener, style);
    }

    /**
     * Creates a ScreenButton which can be used with the ScreenController.
     *
     * <p>Uses the DEFAULT_BUTTON_STYLE
     *
     * @param text the text for the ScreenButton
     * @param position the Position where the ScreenButton should be placed 0,0 is bottom left
     * @param listener the TextButtonListener which handles the button press
     */
    public MenuButton(String text, Point position, TextButtonListener listener) {
        super(text, position, listener);
    }
}
