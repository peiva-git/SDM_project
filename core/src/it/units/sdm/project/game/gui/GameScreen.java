package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
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
    public static final Color DARK_TILE = new Color(181 / 255f, 136 / 255f, 99 / 255f, 1);
    public static final Color LIGHT_TILE = new Color(240 / 255f, 217 / 255f, 181 / 255f, 1);
    public static final Color HIGHLIGHT_DARK_TILE = new Color(105 / 255f, 105 / 255f, 105 / 255f, 255 / 255f);
    public static final Color HIGHLIGHT_LIGHT_TILE = new Color(169 / 255f, 169 / 255f, 169 / 255f, 255 / 255f);
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
    private Texture whiteSquareTexture;
    @NotNull
    private final Skin skin;
    @NotNull
    private final Table container;
    @NotNull
    private final TextureAtlas atlas;
    @NotNull
    private final TextArea firstTextArea;
    @NotNull
    private final FreedomGameDialog lastMoveDialog;
    @NotNull
    private final FreedomGameDialog gameOverDialog;

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
        lastMoveDialog = new FreedomGameDialog("Do you want to put the last stone?", "Yes", "No", skin);
        gameOverDialog = new FreedomGameDialog("", "Play again", "Exit", skin);
        lastMoveDialog.setSize(500, 200);
        gameOverDialog.setSize(500,200);
        addDialogListeners();
    }

    private void addDialogListeners() {
        lastMoveDialog.addPositiveButtonListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setGameStatus(GameStatus.FREEDOM);
                lastMoveDialog.hide();
                super.clicked(event, x, y);
            }
        });
        lastMoveDialog.addNegativeButtonListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setGameStatus(GameStatus.GAME_OVER);
                lastMoveDialog.hide();
                super.clicked(event, x, y);
            }
        });
        gameOverDialog.addPositiveButtonListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getBoard().clearBoard();
                game.getPlayersMovesHistory().clear();
                game.setGameStatus(GameStatus.STARTED);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        gameOverDialog.addNegativeButtonListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void initTextures() {
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        whiteSquareTexture = new Texture(whiteSquare);
        whiteSquare.dispose();
    }

    private void initBoard() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            boardLayout.row();
            if (isIndexEven(i)) {
                initBoardColumns(DARK_TILE, LIGHT_TILE);
            } else {
                initBoardColumns(LIGHT_TILE, DARK_TILE);
            }
        }
    }

    private void initBoardColumns(Color oddTilesColor, Color evenTilesColor) {
        for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
            TextureRegion whiteTextureRegion = new TextureRegion(whiteSquareTexture, 0, 0, TILE_SIZE, TILE_SIZE);
            Image tile = new Image(whiteTextureRegion);
            if (isIndexEven(j)) {
                tile.setColor(evenTilesColor);
            } else {
                tile.setColor(oddTilesColor);
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
        stage.draw();
        stage.act(delta);
        switch (game.getGameStatus()) {
            case GAME_OVER:
                Player winner = getWinner(game.getCurrentScore(Color.WHITE), game.getCurrentScore(Color.BLACK));
                if(winner == null) {
                    gameOverDialog.setMainLabel("Tie!");
                } else {
                    gameOverDialog.setMainLabel("The winner is: " + winner);
                }
                gameOverDialog.setPosition(container.getWidth()/2 - lastMoveDialog.getWidth()/2, container.getHeight()/2 - lastMoveDialog.getHeight()/2);                stage.addActor(gameOverDialog);
                stage.addActor(gameOverDialog);
                break;
            case LAST_MOVE:
                lastMoveDialog.setPosition(container.getWidth() / 2 - lastMoveDialog.getWidth() / 2, container.getHeight() / 2 - lastMoveDialog.getHeight() / 2);
                stage.addActor(lastMoveDialog);
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
        whiteSquareTexture.dispose();
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
                    return;
                } else {
                    resetCurrentlyHighlightedCells();
                    putStoneOnTheBoard(currentPlayer, inputPosition);
                    highlightValidPositionsForNextMove();
                }
            } else if (game.getGameStatus() == GameStatus.NO_FREEDOM) {
                Set<Position> allowedPositions = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition()).stream()
                        .filter(position -> !game.getBoard().isCellOccupied(position))
                        .collect(Collectors.toSet());
                if (!allowedPositions.contains(inputPosition)) {
                    return;
                } else {
                    resetCurrentlyHighlightedCells();
                    putStoneOnTheBoard(currentPlayer, inputPosition);
                    highlightValidPositionsForNextMove();
                }
            }
            super.clicked(event, x, y);
        }

        private void putStoneOnTheBoard(@NotNull Player currentPlayer, @NotNull Position inputPosition) {
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
            Set<Position> positionsToHighlight = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition()).stream()
                    .filter(position -> !game.getBoard().isCellOccupied(position))
                    .collect(Collectors.toSet());
            List<Cell<Actor>> cellsToHighlight = new ArrayList<>();
            for (int i = 0; i < boardLayout.getCells().size; i++) {
                Cell<Actor> cell = boardLayout.getCells().get(i);
                if (positionsToHighlight.contains(getPositionFromTile(cell))) {
                    cellsToHighlight.add(cell);
                }
            }
            for (Cell<Actor> cellToHighlight : cellsToHighlight) {
                Stack tileAndPiece = (Stack) cellToHighlight.getActor();
                Actor tile = tileAndPiece.getChild(0);
                if (isIndexEven(cellToHighlight.getRow())) {
                    if (isIndexEven(cellToHighlight.getColumn())) {
                        tile.setColor(HIGHLIGHT_LIGHT_TILE);
                    } else {
                        tile.setColor(HIGHLIGHT_DARK_TILE);
                    }
                } else {
                    if (isIndexEven(cellToHighlight.getColumn())) {
                        tile.setColor(HIGHLIGHT_DARK_TILE);
                    } else {
                        tile.setColor(HIGHLIGHT_LIGHT_TILE);
                    }
                }
            }
        }

        private void resetCurrentlyHighlightedCells() {
            for (int i = 0; i < boardLayout.getCells().size; i++) {
                Cell<Actor> cell = boardLayout.getCells().get(i);
                Stack tileAndPiece = (Stack) cell.getActor();
                Actor tile = tileAndPiece.getChild(0);
                if (tile.getColor().equals(HIGHLIGHT_DARK_TILE)) {
                    tile.setColor(DARK_TILE);
                } else if (tile.getColor().equals(HIGHLIGHT_LIGHT_TILE)) {
                    tile.setColor(LIGHT_TILE);
                }
            }
        }
    }
}
