package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.board.gui.TileClickListener;
import it.units.sdm.project.game.BoardGame;
import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.dialogs.GameOverDialog;
import it.units.sdm.project.game.gui.dialogs.LastMoveDialog;
import it.units.sdm.project.game.gui.screens.GameScreen;
import it.units.sdm.project.game.gui.screens.SplashScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus.*;

/**
 * Represents the Freedom game, which is a {@link BoardGame} played by two
 * {@link Player}s ({@link Color#WHITE} and {@link Color#BLACK}) on a {@link GuiBoard}.
 */
public class FreedomGame extends Game implements BoardGame<GuiStone> {
    /**
     * Maximum number of columns and rows that a {@link Board} can have in a {@link FreedomGame}
     */
    public static final int MAX_BOARD_SIZE = 12;
    /**
     * Minimum number of columns and rows that a {@link Board} can have in a {@link FreedomGame}
     */
    public static final int MIN_BOARD_SIZE = 4;
    private static final String GAME_TAG = "FREEDOM_GAME";
    private int numberOfRowsAndColumns = 8;
    @NotNull
    private GuiBoard<GuiStone> board;
    @NotNull
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    @NotNull
    private Player whitePlayer = new Player(Color.WHITE, "player_one");
    @NotNull
    private Player blackPlayer = new Player(Color.BLACK, "player_two");
    @NotNull
    private FreedomBoardStatusObserver statusObserver;
    @NotNull
    private GameStatus gameStatus = FREEDOM;
    @NotNull
    private FreedomCellHighlighter cellHighlighter;
    @NotNull
    private TextureAtlas atlas;

    @Override
    public void create() {
        atlas = new TextureAtlas("freedom.atlas");
        VisUI.load(VisUI.SkinScale.X2);
        VisUI.getSkin().addRegions(atlas);
        reloadBoardSetup();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        atlas.dispose();
        VisUI.dispose();
    }

    @Override
    public void reset() {
        board.clearBoard();
        playersMovesHistory.clear();
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
        cellHighlighter.resetCurrentlyHighlightedCellsIfAny();
    }

    private void appendTextToLogArea(@NotNull String textToAppend) {
        if (getScreen().getClass() == GameScreen.class) {
            GameScreen currentScreen = (GameScreen) getScreen();
            currentScreen.appendTextToLogArea(textToAppend);
        } else {
            Gdx.app.error(GAME_TAG, "Unable to print to game screen text area");
        }
    }

    /**
     * Returns the {@link Board} used by this {@link BoardGame}.
     * If no {@link Board} was set, returns the default 8x8 sized {@link Board}
     * @return The {@link Board} used by this {@link BoardGame}
     */
    @Override
    public @NotNull Board<GuiStone> getBoard() {
        return board;
    }

    /**
     * Returns the {@link com.badlogic.gdx.graphics.Color#BLACK} {@link Player}.
     * If no {@link Player} was set, returns a default {@link Player}
     * @return The {@link Player} who's going first
     */
    @Override
    public @NotNull Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the {@link com.badlogic.gdx.graphics.Color#BLACK} {@link Player}.
     * If no {@link Player} was set, returns a default {@link Player}
     * @return The {@link Player} who's going second
     */
    @Override
    public @NotNull Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * Gets the number of rows or columns used by {@code this} {@link FreedomGame}'s {@link Board}.
     * If no value was set, returns the default value of 8
     * @return The number of rows or columns
     */
    public int getNumberOfRowsAndColumns() {
        return numberOfRowsAndColumns;
    }

    @Override
    public @Nullable Move getLastMove() {
        if (playersMovesHistory.isEmpty()) return null;
        return playersMovesHistory.getLast();
    }

    @Override
    public void nextMove(@NotNull Position inputPosition) {
        Move currentMove = new Move(getNextPlayer(), inputPosition);
        if (!isChosenPositionValid(currentMove.getPosition())) return;
        updateBoard(currentMove);
        if(gameStatus == GAME_OVER) {
            GameOverDialog gameOverDialog = new GameOverDialog(this);
            gameOverDialog.show(board.getStage());
        }
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
        if (gameStatus == LAST_MOVE) {
            LastMoveDialog lastMoveDialog = new LastMoveDialog(this);
            lastMoveDialog.show(board.getStage());
            gameStatus = GAME_OVER;
        }
    }

    /**
     * Sets the {@link Color#WHITE} {@link Player} for {@code this} {@link FreedomGame}
     * @param whitePlayer The {@link Player} to be set as the {@link Color#WHITE} {@link Player}
     */
    public void setWhitePlayer(@NotNull Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    /**
     * Sets the {@link Color#BLACK} {@link Player} for {@code this} {@link FreedomGame}
     * @param blackPlayer The {@link Player} to be set as the {@link Color#BLACK} {@link Player}
     */
    public void setBlackPlayer(@NotNull Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    /**
     * Sets the number of rows and columns for {@code this} {@link FreedomGame}
     * @param numberOfRowsAndColumns The numbers of rows and columns to be set
     */
    public void setNumberOfRowsAndColumns(int numberOfRowsAndColumns) {
        this.numberOfRowsAndColumns = numberOfRowsAndColumns;
        reloadBoardSetup();
    }

    private void reloadBoardSetup() {
        board = new GuiBoard<>(numberOfRowsAndColumns, numberOfRowsAndColumns);
        board.setTileClickListener(new TileClickListener(this));
        statusObserver = new FreedomBoardStatusObserver(board);
        cellHighlighter = new FreedomCellHighlighter(board);
    }

    private void updateBoard(Move currentMove) {
        cellHighlighter.resetCurrentlyHighlightedCellsIfAny();
        putStoneOnTheBoard(currentMove);
        cellHighlighter.highlightPositions(findFreePositionsNearLastPlayedPosition());
    }

    private boolean isChosenPositionValid(@NotNull Position inputPosition) {
        if (board.isCellOccupied(inputPosition)) return false;
        if (gameStatus == GameStatus.NO_FREEDOM)
            return findFreePositionsNearLastPlayedPosition().contains(inputPosition);
        return true;
    }

    @NotNull
    private Set<Position> findFreePositionsNearLastPlayedPosition() {
        return board.getAdjacentPositions(Objects.requireNonNull(getLastMove(),
                        "There should be at least one move played when looking for free adjacent cells").getPosition()).stream()
                .filter(position -> !board.isCellOccupied(position))
                .collect(Collectors.toSet());
    }

    private void putStoneOnTheBoard(@NotNull Move move) {
        Player currentPlayer = move.getPlayer();
        Position currentPosition = move.getPosition();
        board.putPiece(new GuiStone(currentPlayer.getColor(), findStoneTextureRegion(currentPlayer.getColor())), currentPosition);
        updateLogArea(move);
        playersMovesHistory.add(move);
    }

    private void updateLogArea(@NotNull Move move) {
        if (move.getPlayer().getColor() == Color.WHITE) {
            appendTextToLogArea(whitePlayer + ": " + move.getPosition() + "\n");
        } else {
            appendTextToLogArea(blackPlayer + ": " + move.getPosition() + "\n");
        }
    }

    @NotNull
    private TextureRegion findStoneTextureRegion(@NotNull Color color) {
        if(color == Color.WHITE) return atlas.findRegion("white_checker");
        return atlas.findRegion("black_checker");
    }
}
