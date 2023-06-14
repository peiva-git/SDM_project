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

public class PlayersNamesFormScreen implements Screen {

    private static final int FORM_SCREEN_WORLD_WIDTH = 800;
    private static final int FORM_SCREEN_WORLD_HEIGHT = 600;

    @NotNull
    private final Stage stage;

    public PlayersNamesFormScreen(@NotNull FreedomGame game) {
        stage = new Stage(new FitViewport(FORM_SCREEN_WORLD_WIDTH, FORM_SCREEN_WORLD_HEIGHT));

        FormLayout form = new FormLayout();
        form.setFillParent(true);

        form.getContinueButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setWhitePlayer(new Player(Color.WHITE, form.getWhitePlayerName().getText(), form.getWhitePlayerSurname().getText()));
                game.setBlackPlayer(new Player(Color.BLACK, form.getBlackPlayerName().getText(), form.getBlackPlayerSurname().getText()));
                game.setNumberOfRowsAndColumns((int) form.getBoardSizeSlider().getValue());
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
