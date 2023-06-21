package graphic.hud.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import graphic.hud.*;
import java.util.HashSet;
import java.util.logging.Logger;
import starter.Game;
import tools.Constants;
import tools.Point;

public class Menu<T extends Actor> extends ScreenController<T> {

    private String headline;
    private HashSet<Actor> items;
    private boolean isVisible = false;

    private static final Logger menuLogger = Logger.getLogger(Menu.class.getName());

    /**
     * The Menu constructor. Builds a new menu screen from a given headline and a set of screen
     * elements. Creates a Screencontroller with a ScalingViewport which stretches the
     * ScreenElements on resize.
     *
     * @param title the menu headline
     * @param elements the set of elements that make up the menu screen
     */
    public Menu(String title, HashSet<Actor> elements) {
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
    public Menu(SpriteBatch batch, String title, HashSet<Actor> elements) {
        super(batch);

        headline = title;
        items = elements;

        add((T) setUpHeadline(headline));

        for (Actor item : items) {
            add((T) item);
        }

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

    /**
     * Generates all elements needed to build the Main Menu
     *
     * @return a set of elements that make up the main menu
     */
    public static HashSet<Actor> generateMainMenu() {
        HashSet<Actor> mainMenuItems = new HashSet<>();

        MenuButton startGameButton =
                new MenuButton(
                        "Start",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("Starting game ...");
                                Game.getGame().toggleMainMenu();
                                Game.toggleSystems();
                            }
                        });
        startGameButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - startGameButton.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 2.0f * startGameButton.getHeight(),
                Align.center | Align.bottom);
        startGameButton.getLabel().setFontScale(1.5f);

        mainMenuItems.add(startGameButton);

        MenuButton optionsButton =
                new MenuButton(
                        "Options",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("Closing main menu and opening options ...");
                                Game.getGame().toggleMainMenu();
                                Game.getGame().toggleOptions();
                            }
                        });

        optionsButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - startGameButton.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 4.0f * startGameButton.getHeight(),
                Align.center | Align.bottom);
        optionsButton.getLabel().setFontScale(1.5f);

        mainMenuItems.add(optionsButton);

        MenuButton closeGameButton =
                new MenuButton(
                        "Close",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("Closing game ...");
                                Gdx.app.exit();
                            }
                        });

        closeGameButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - startGameButton.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 6.0f * startGameButton.getHeight(),
                Align.center | Align.bottom);
        closeGameButton.getLabel().setFontScale(1.5f);

        mainMenuItems.add(closeGameButton);

        return mainMenuItems;
    }

    /**
     * Generates all elements needed to build the Options Menu
     *
     * @return a set of elements that make up the options menu
     */
    public static HashSet<Actor> generateOptionsMenu() {
        HashSet<Actor> optionsMenuItems = new HashSet<>();

        MenuButton optionA =
                new MenuButton(
                        "Option A",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("'Option A' button was clicked.");
                            }
                        });

        optionA.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - optionA.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 2.0f * optionA.getHeight(),
                Align.center | Align.bottom);

        optionA.getLabel().setFontScale(1.5f);
        optionsMenuItems.add(optionA);

        MenuButton optionB =
                new MenuButton(
                        "Option B",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("'Option B' button was clicked.");
                            }
                        });

        optionB.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - optionA.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 4.0f * optionA.getHeight(),
                Align.center | Align.bottom);

        optionB.getLabel().setFontScale(1.5f);
        optionsMenuItems.add(optionB);

        MenuButton optionC =
                new MenuButton(
                        "Option C",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info("'Option C' button was clicked.");
                            }
                        });

        optionC.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - optionA.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 6.0f * optionA.getHeight(),
                Align.center | Align.bottom);
        optionC.getLabel().setFontScale(1.5f);

        optionsMenuItems.add(optionC);

        MenuButton backButton =
                new MenuButton(
                        "Back",
                        new Point(0.0f, 0.0f),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                menuLogger.info(
                                        "Closing the options and opening the main menu ...");
                                Game.getGame().toggleOptions();
                                Game.getGame().toggleMainMenu();
                            }
                        });

        backButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - optionA.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f - 8.0f * optionA.getHeight(),
                Align.center | Align.bottom);
        backButton.getLabel().setFontScale(1.5f);

        optionsMenuItems.add(backButton);

        return optionsMenuItems;
    }

    /** Makes the menu visible. */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** Hides the menu. */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    private void addItem(Actor item) {
        items.add(item);
    }

    private void removeItem(Actor item) {
        items.remove(item);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
