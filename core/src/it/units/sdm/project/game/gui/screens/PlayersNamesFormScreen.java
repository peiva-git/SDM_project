package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.widget.*;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * {@link FreedomGame}'s game customization {@link Screen}.
 * This {@link Screen} provides the methods to set the {@link Player}s and the {@link it.units.sdm.project.board.Board}
 * for the current {@link FreedomGame} from the GUI.
 */
public class PlayersNamesFormScreen implements Screen {

    private static final int FORM_SCREEN_WORLD_WIDTH = 800;
    private static final int FORM_SCREEN_WORLD_HEIGHT = 600;

    @NotNull
    private final Stage stage;

    /**
     * Creates an instance of a {@link PlayersNamesFormScreen}.
     *
     * @param game The {@link FreedomGame} using {@code this} {@link Screen}
     */
    public PlayersNamesFormScreen(@NotNull FreedomGame game) {
        stage = new Stage(new FitViewport(FORM_SCREEN_WORLD_WIDTH, FORM_SCREEN_WORLD_HEIGHT));

        FormLayout form = new FormLayout();
        form.setFillParent(true);

        form.getContinueButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setWhitePlayer(new Player(Color.WHITE, form.getWhitePlayerUsername().getText()));
                game.setBlackPlayer(new Player(Color.BLACK, form.getBlackPlayerUsername().getText()));
                game.setBoardSize((int) form.getBoardSizeSlider().getValue());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        form.getBoardSizeSlider().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                form.getBoardSizeText().setText(String.valueOf((int) ((VisSlider) actor).getValue()));
            }
        });

        stage.addActor(form);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // do nothing
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        // do nothing
    }

    @Override
    public void resume() {
        // do nothing
    }

    @Override
    public void hide() {
        // do nothing
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
