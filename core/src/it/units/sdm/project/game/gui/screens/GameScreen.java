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
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.board.gui.GuiBoard.TILE_SIZE;
import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_COLUMNS;
import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

/**
 * Main {@link FreedomGame} screen. Should be displayed after the players and the board are set.
 */
public class GameScreen implements Screen {
    private static final int GAME_SCREEN_WORLD_WIDTH = TILE_SIZE * NUMBER_OF_COLUMNS * 2;
    public static final int GAME_SCREEN_WORLD_HEIGHT = TILE_SIZE * NUMBER_OF_ROWS;
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Skin skin;
    @NotNull
    private final TextArea logArea;

    /**
     * Creates an instance of {@link GameScreen} and it sets the board layout and the log area to be displayed.
     * @param game The game using this screen
     */
    public GameScreen(@NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(GAME_SCREEN_WORLD_WIDTH, GAME_SCREEN_WORLD_HEIGHT), new SpriteBatch());
        Table container = new Table();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        logArea = new TextArea("Welcome to Freedom! Tap anywhere on the board to begin!\n", skin);
        skin.addRegions(atlas);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
        container.setFillParent(true);
        Drawable background = skin.getDrawable("textfield");
        container.setBackground(background);
        logArea.setAlignment(Align.topLeft);
        logArea.setDisabled(true);
        container.add(logArea).expand().fill();
        container.add((GuiBoard) game.getBoard()).width(NUMBER_OF_COLUMNS * TILE_SIZE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        game.reset();
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

    /**
     * Gets the screen log area, used to display messages to the user while playing
     * @return The screen log area
     */
    public @NotNull TextArea getLogArea() {
        return logArea;
    }

}
