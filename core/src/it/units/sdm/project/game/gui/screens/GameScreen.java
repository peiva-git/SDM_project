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
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.board.gui.GuiBoard.TILE_SIZE;

/**
 * Main {@link FreedomGame} {@link Screen}.
 * Should be displayed after the {@link it.units.sdm.project.game.Player}s and the {@link it.units.sdm.project.board.Board} are set.
 */
public class GameScreen implements Screen {
    public static final int BOARD_PADDING = 10;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Skin skin;
    @NotNull
    private final TextArea logArea;

    /**
     * Creates an instance of a {@link GameScreen} and it sets the {@link it.units.sdm.project.board.Board} layout and the log area to be displayed.
     * @param game The {@link FreedomGame} using this {@link Screen}
     */
    public GameScreen(@NotNull FreedomGame game) {
        int GAME_SCREEN_WORLD_WIDTH = TILE_SIZE * game.getNumberOfRowsAndColumns() + 300;
        int GAME_SCREEN_WORLD_HEIGHT = TILE_SIZE * game.getNumberOfRowsAndColumns() + 50;
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
        container.add((Actor) game.getBoard()).width(game.getNumberOfRowsAndColumns() * TILE_SIZE).pad(BOARD_PADDING);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
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
     * Gets this {@link Screen}'s log area, used to display messages to the user while playing
     * @return The {@link Screen}'s log area
     */
    public @NotNull TextArea getLogArea() {
        return logArea;
    }

}
