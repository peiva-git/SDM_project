package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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
    @NotNull
    private Texture blackSquareTexture;
    @NotNull
    private Texture whiteSquareTexture;
    @NotNull
    private final Skin skin;
    @NotNull
    private final Table container;
    @NotNull
    private final TextureAtlas atlas;
    @NotNull
    private final TextArea firstTextArea;
    private Texture highlightedSquareTexture;

    public GameScreen(@NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        boardLayout = new Table();
        container = new Table();
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        firstTextArea = new TextArea("Welcome to Freedom! Tap anywhere on the board to begin!\n", skin);
        // init tile textures //
        initTextures();
        // the above part may be incorporated in a custom texture pack //
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
        initBoard();

        // debugging
        container.setDebug(true);
        firstTextArea.setDebug(true);
    }

    private void initTextures() {
        Pixmap blackSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquareTexture = new Texture(blackSquare);
        whiteSquareTexture = new Texture(whiteSquare);
        Pixmap highlightedSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        highlightedSquare.setColor(154 / 255f, 200 / 255f, 50 / 255f, 50 / 255f);
        highlightedSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        highlightedSquareTexture = new Texture(highlightedSquare);
        highlightedSquare.dispose();
        blackSquare.dispose();
        whiteSquare.dispose();
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
            boardLayout.add(tileAndPiece).size(TILE_SIZE);
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
        highlightedSquareTexture.dispose();
        skin.dispose();
        // the skin disposes of the atlas
        atlas.dispose();
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
            Position inputPosition = getPositionFromTile(clickedTile);
            if (game.getGameStatus() == GameStatus.FREEDOM || game.getGameStatus() == GameStatus.STARTED) {
                if (game.getBoard().isCellOccupied(inputPosition)) {
                    System.out.println(inputPosition + " is occupied!");
                    return;
                } else {
                    putStoneOnTheBoard(currentPlayer, inputPosition);
                    highlightValidPositionsForNextMove();
                }
            } else if (game.getGameStatus() == GameStatus.NO_FREEDOM) {
                Set<Position> allowedPositions = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition()).stream()
                        .filter(position -> !game.getBoard().isCellOccupied(position))
                        .collect(Collectors.toSet());
                if (!allowedPositions.contains(inputPosition)) {
                    System.out.println(inputPosition + " is occupied!");
                    return;
                } else {
                    putStoneOnTheBoard(currentPlayer, inputPosition);
                    highlightValidPositionsForNextMove();
                }
            }
            super.clicked(event, x, y);
        }

        private void putStoneOnTheBoard(Player currentPlayer, Position inputPosition) {
            if (currentPlayer.getColor() == Color.WHITE) {
                game.getBoard().putPiece(new Stone(Color.WHITE), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getWhitePlayer(), inputPosition));
                Image whiteStone = new Image(whiteStoneImage);
                tileAndPiece.addActor(whiteStone);
                long currentStep = game.getPlayersMovesHistory().stream()
                        .filter(move -> move.getPlayer().getColor() == Color.WHITE)
                        .count();
                if (currentStep < 10) {
                    firstTextArea.appendText("  " + currentStep + ". " + inputPosition);
                } else if (currentStep < 100) {
                    firstTextArea.appendText(" " + currentStep + ". " + inputPosition);
                } else {
                    firstTextArea.appendText(currentStep + ". " + inputPosition);
                }
            } else {
                game.getBoard().putPiece(new Stone(Color.BLACK), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getBlackPlayer(), inputPosition));
                Image blackStone = new Image(blackStoneImage);
                tileAndPiece.addActor(blackStone);
                firstTextArea.appendText("     " + inputPosition + "\n");
            }
            game.updateCurrentGameStatus();
        }

        @NotNull
        private Position getPositionFromTile(@NotNull Cell<Actor> tile) {
            return Position.fromCoordinates(NUMBER_OF_ROWS - tile.getRow() - 1, tile.getColumn());
        }

        private void highlightValidPositionsForNextMove() {
            // TODO reset previously highlighted positions
            Set<Position> positionsToHighlight = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition()).stream()
                    .filter(position -> !game.getBoard().isCellOccupied(position))
                    .collect(Collectors.toSet());
            List<Cell<Actor>> validCells = new ArrayList<>();
            for (int i = 0; i < boardLayout.getCells().size; i++) {
                Cell<Actor> cell = boardLayout.getCells().get(i);
                if (positionsToHighlight.contains(getPositionFromTile(cell))) {
                    validCells.add(cell);
                }
            }
            System.out.println("Valid positions for the next move: " + positionsToHighlight);
            System.out.println("Valid cells for the next move: " + validCells);
            System.out.println(game.getBoard());
            for (Cell<Actor> validCell : validCells) {
                Stack tileAndPiece = (Stack) validCell.getActor();
                Image tile = (Image) tileAndPiece.getChild(0);
                tile.setColor(204 / 255f, 0 / 255f, 0 / 255f, 50 / 255f);
            }
        }
    }
}
