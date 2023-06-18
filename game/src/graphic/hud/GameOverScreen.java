package graphic.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import starter.Game;
import tools.Constants;
import tools.Point;

public class GameOverScreen<T extends Actor> extends ScreenController<T> {

    public GameOverScreen() {
        this(new SpriteBatch(), new Game());
    }

    /**
     * Creates a Screencontroller with a ScalingViewport which stretches the ScreenElements on
     * resize
     *
     * @param batch the batch which should be used to draw with
     */
    public GameOverScreen(SpriteBatch batch, Game game) {
        super(batch);

        ScreenText screenText =
                new ScreenText(
                        "You Died",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder((FontBuilder.DEFAULT_FONT))
                                .setFontcolor(Color.RED)
                                .build());

        screenText.setFontScale(3);
        screenText.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f + screenText.getHeight(),
                Align.center | Align.bottom);

        ScreenButton restartButton =
                new ScreenButton(
                        "Restart",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.reset();
                            }
                        });

        restartButton.setPosition(
                (Constants.WINDOW_WIDTH) / 3.0f,
                (Constants.WINDOW_HEIGHT) / 2.0f + screenText.getHeight(),
                Align.left | Align.bottom);

        restartButton.getLabel().setFontScale(1.25f);

        ScreenButton quitButton =
                new ScreenButton(
                        "Quit",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Gdx.app.exit();
                            }
                        });

        quitButton.setPosition(
                (Constants.WINDOW_WIDTH) - ((Constants.WINDOW_WIDTH) / 3.0f),
                (Constants.WINDOW_HEIGHT) / 2.0f + screenText.getHeight(),
                Align.right | Align.bottom);

        quitButton.getLabel().setFontScale(1.25f);

        add((T) screenText);
        add((T) restartButton);
        add((T) quitButton);

        hideMenu();
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
