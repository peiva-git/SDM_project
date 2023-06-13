package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.board.gui.GuiBoard.TILE_SIZE;

public class PlayersFormScreen implements Screen {

    @NotNull
    private final Stage stage;

    public PlayersFormScreen(@NotNull FreedomGame game) {
        int FORM_SCREEN_WORLD_WIDTH = TILE_SIZE * game.getNumberOfRowsAndColumns() + 550;
        int FORM_SCREEN_WORLD_HEIGHT = TILE_SIZE * game.getNumberOfRowsAndColumns() + 300;
        stage = new Stage(new FitViewport(FORM_SCREEN_WORLD_WIDTH, FORM_SCREEN_WORLD_HEIGHT));
        VisUI.load(VisUI.SkinScale.X2);

        FormWindow form = new FormWindow();
        form.setFillParent(true);

        form.getContinueButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setWhitePlayer(new Player(Color.WHITE, form.getWhitePlayerName().getText(), form.getWhitePlayerSurname().getText()));
                game.setBlackPlayer(new Player(Color.BLACK, form.getBlackPlayerName().getText(), form.getBlackPlayerSurname().getText()));
                game.setNumberOfRowsAndColumns((int) form.getBoardSize().getValue());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        form.getBoardSize().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                form.getBoardSizeText().setText(String.format("%d", (int) ((VisSlider) actor).getValue()));
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
        VisUI.dispose();
    }
}