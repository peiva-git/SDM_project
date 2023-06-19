package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.widget.*;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.board.gui.GuiBoard.TILE_SIZE;

/**
 * Main {@link FreedomGame} {@link Screen}.
 * Should be displayed after the {@link it.units.sdm.project.game.Player}s and the {@link it.units.sdm.project.board.Board} are set
 * in the {@link FreedomGame}.
 */
public class GameScreen implements Screen {
    private static final float BOARD_PADDING = 10f;
    @NotNull
    private final Stage stage;
    @NotNull
    private final VisTable mainContainer;
    @NotNull
    private final VisTextArea logArea;
    @NotNull
    private final FreedomGame freedomGame;

    /**
     * Creates an instance of a {@link GameScreen} and it sets the {@link it.units.sdm.project.board.Board} layout and the log area to be displayed.
     * @param game The {@link FreedomGame} using {@code this} {@link Screen}
     */
    public GameScreen(@NotNull FreedomGame game) {
        this.freedomGame = game;
        final int GAME_SCREEN_WORLD_WIDTH = TILE_SIZE * game.getNumberOfRowsAndColumns() + FreedomGame.SCREEN_WORLD_WIDTH;
        final int GAME_SCREEN_WORLD_HEIGHT = TILE_SIZE * game.getNumberOfRowsAndColumns() + FreedomGame.SCREEN_WORLD_HEIGHT;
        stage = new Stage(new FitViewport(GAME_SCREEN_WORLD_WIDTH, GAME_SCREEN_WORLD_HEIGHT), new SpriteBatch());
        logArea = new VisTextArea();
        mainContainer = new VisTable();
        setUpLogArea();
        setUpMainContainer();
        Gdx.input.setInputProcessor(stage);
    }
    private void setUpLogArea() {
        logArea.setPrefRows(freedomGame.getNumberOfRowsAndColumns() * 2);
        mainContainer.setFillParent(true);
        logArea.setReadOnly(true);
        logArea.appendText("FREEDOM\n");
        logArea.appendText("White player: " + freedomGame.getWhitePlayer() + "\n");
        logArea.appendText("Black player: " + freedomGame.getBlackPlayer() + "\n\n");
        logArea.appendText(freedomGame.getWhitePlayer() + ", tap anywhere on the board to begin!\n\n");
        logArea.appendText("Players moves history:\n");
    }

    private void setUpMainContainer() {
        VisScrollPane logAreaScrollPane = new VisScrollPane(logArea);
        mainContainer.add(logAreaScrollPane).growX().height(TILE_SIZE * freedomGame.getNumberOfRowsAndColumns()).pad(BOARD_PADDING);
        mainContainer.add((Actor) freedomGame.getBoard()).pad(BOARD_PADDING);
        mainContainer.row();
        mainContainer.add(new VisLabel("Check out the rules if you don't remember how to play: ")).left().pad(BOARD_PADDING);
        mainContainer.row();
        mainContainer.add(new LinkLabel("link to the official repository", "https://github.com/peiva-git/SDM_project#rules")).left().pad(BOARD_PADDING);
        stage.addActor(mainContainer);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
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
    }

    /**
     * Appends {@code textToAppend} to {@code this} {@link Screen}'s log area,
     * used to display messages to the user while playing
     * @param textToAppend The {@link String} to append to the log area
     */
    public void appendTextToLogArea(@NotNull String textToAppend) {
        logArea.appendText(textToAppend);
    }

}
