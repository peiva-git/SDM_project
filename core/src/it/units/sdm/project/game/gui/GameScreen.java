package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_COLUMNS;
import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

public class GameScreen implements Screen {


    public static final int TILE_SIZE = 75;
    @NotNull
    private final Table boardLayout;
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Texture blackStoneImage = new Texture(Gdx.files.internal("circle2.png"));
    @NotNull
    private final Texture whiteStoneImage = new Texture(Gdx.files.internal("redCircle.png"));
    private final Texture blackSquareTexture;
    private final Texture whiteSquareTexture;
    private final Skin skin;
    private final Table tableLayout;

    public GameScreen(@NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        Pixmap blackSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquareTexture = new Texture(blackSquare);
        whiteSquareTexture = new Texture(whiteSquare);
        blackSquare.dispose();
        whiteSquare.dispose();
        tableLayout = new Table();
        tableLayout.setFillParent(true);
        tableLayout.setDebug(true);
        stage.addActor(tableLayout);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label firstLabel = new Label("test first label", skin);
        firstLabel.setDebug(true);
        firstLabel.setAlignment(Align.topLeft);
        firstLabel.setWidth(400f);
        firstLabel.setWrap(true);
        tableLayout.add(firstLabel).expand(true, true).fill();
        boardLayout = new Table();
        boardLayout.setWidth(400f);
        tableLayout.add(boardLayout);
        initBoard();
        Label secondLabel = new Label("Welcome to the Freedom board game!", skin);
        secondLabel.setDebug(true);
        secondLabel.setAlignment(Align.topLeft);
        secondLabel.setWrap(true);
        firstLabel.setWidth(400f);
        tableLayout.add(secondLabel).expand(true, true).fill();
    }

    private void initBoard() {
        TextureRegion blackTextureRegion = new TextureRegion(blackSquareTexture, 0, 0, TILE_SIZE, TILE_SIZE);
        TextureRegion whiteTextureRegion = new TextureRegion(whiteSquareTexture, 0, 0, TILE_SIZE, TILE_SIZE);
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            boardLayout.row();
            if (isIndexEven(i)) {
                initBoardColumns(blackTextureRegion, whiteTextureRegion);
            } else {
                initBoardColumns(whiteTextureRegion, blackTextureRegion);
            }
        }
    }

    private void initBoardColumns(TextureRegion oddTilesColor, TextureRegion evenTilesColor) {
        for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
            Image tile;
            if (isIndexEven(j)) {
                tile = new Image(evenTilesColor);
            } else {
                tile = new Image(oddTilesColor);
            }
            Stack tileAndPiece = new Stack(tile);
            tileAndPiece.addListener(new TileClickListener(tileAndPiece));
            boardLayout.add(tileAndPiece);
        }
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        switch (game.getGameStatus()) {
            case GAME_OVER:
                ScreenUtils.clear(Color.BLACK);
                Player winner = getWinner(game.getCurrentScore(Color.WHITE), game.getCurrentScore(Color.BLACK));
                System.out.println(winner);
                return;
            case LAST_MOVE:
                game.setGameStatus(GameStatus.GAME_OVER);
            default:
        }
    }

    @Nullable
    private Player getWinner(int whitePlayerScore, int blackPlayerScore) {
        if (whitePlayerScore > blackPlayerScore) {
            return game.getWhitePlayer();
        } else if (blackPlayerScore > whitePlayerScore) {
            return game.getBlackPlayer();
        }
        return null;
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
        whiteStoneImage.dispose();
        blackStoneImage.dispose();
        blackSquareTexture.dispose();
        whiteSquareTexture.dispose();
        skin.dispose();
    }

    private class TileClickListener extends ClickListener {
        @NotNull
        private final Stack tileAndPiece;

        public TileClickListener(@NotNull Stack tileAndPiece) {
            this.tileAndPiece = tileAndPiece;
        }

        @Override
        public void clicked(@NotNull InputEvent event, float x, float y) {
            Player currentPlayer = game.nextPlayer();
            Actor clickedActor = event.getListenerActor();
            Cell<Actor> clickedTile = boardLayout.getCell(clickedActor);
            Position inputPosition = Position.fromCoordinates(NUMBER_OF_ROWS - clickedTile.getRow() - 1, clickedTile.getColumn());
            if (game.getGameStatus() == GameStatus.NO_FREEDOM) {
                Set<Position> validPositions = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition());
                if (!validPositions.contains(inputPosition)) return;
            }

            if (currentPlayer.getColor() == Color.BLACK) {
                game.getBoard().putPiece(new Stone(Color.BLACK), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getBlackPlayer(), inputPosition));
                Image blackStone = new Image(blackStoneImage);
                tileAndPiece.addActor(blackStone);
            } else {
                game.getBoard().putPiece(new Stone(Color.WHITE), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getWhitePlayer(), inputPosition));
                Image whiteStone = new Image(whiteStoneImage);
                tileAndPiece.addActor(whiteStone);
            }
            game.updateCurrentGameStatus();
            super.clicked(event, x, y);
        }
    }
}
