package graphic.hud.menus;

import java.util.HashSet;

public class Menu {

    private String headline;
    private HashSet<IMenuItem> items;

    public Menu(String headline) {
        // TODO: add functionality
    }

    public Menu(String headline, HashSet<IMenuItem> items) {
        // TODO: add functionality
    }

    public void addItem(IMenuItem item) {
        items.add(item);
    }

    public void removeItem(IMenuItem item) {
        items.remove(item);
    }
}
