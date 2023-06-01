package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import it.units.sdm.project.board.gui.GuiFreedomBoard;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_COLUMNS;
import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

public class GameScreen implements Screen {

    public static final int TILE_SIZE = 75;
    public static final Color DARK_TILE = new Color(181 / 255f, 136 / 255f, 99 / 255f, 1);
    public static final Color LIGHT_TILE = new Color(240 / 255f, 217 / 255f, 181 / 255f, 1);
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Skin skin;
    @NotNull
    private final TextArea firstTextArea;
    @NotNull
    private final GuiFreedomBoard boardLayout;

    public GameScreen(@NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        Table container = new Table();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        firstTextArea = new TextArea("Welcome to Freedom! Tap anywhere on the board to begin!\n", skin);
        boardLayout = new GuiFreedomBoard(game, skin, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, DARK_TILE, LIGHT_TILE);
        skin.addRegions(atlas);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
        container.setFillParent(true);
        Drawable background = skin.getDrawable("default-window");
        container.setBackground(background);
        firstTextArea.setAlignment(Align.topLeft);
        firstTextArea.setDisabled(true);
        container.add(firstTextArea).expand().fill();
        container.add(boardLayout).width(NUMBER_OF_COLUMNS * TILE_SIZE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        boardLayout.clearBoard();
        game.getPlayersMovesHistory().clear();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.getBatch().dispose();
        skin.dispose();
        // the skin disposes of the atlas
    }

    public @NotNull TextArea getFirstTextArea() {
        return firstTextArea;
    }

    @NotNull
    public GuiFreedomBoard getBoard() {
        return boardLayout;
    }
}
