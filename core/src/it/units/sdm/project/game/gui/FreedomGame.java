package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.CellHighlighter;
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
import it.units.sdm.project.game.gui.screens.MainMenuScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus.*;

/**
 * Represents the Freedom game, which is a {@link BoardGame} played by two
 * {@link Player}s (white and black) on a {@link GuiBoard}.
 */
public class FreedomGame extends Game implements BoardGame {
    /**
     * The number of rows that the {@link GuiBoard} used in this game has.
     */
    public static final int NUMBER_OF_ROWS = 8;
    /**
     * The number of columns that the {@link GuiBoard} used in this game has.
     */
    public static final int NUMBER_OF_COLUMNS = 8;
    private static final String GAME_TAG = "FREEDOM_GAME";
    private GuiBoard board;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private final Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
    private final Player blackPlayer = new Player(Color.BLACK, "Lollo", "Bianchi");
    private FreedomBoardStatusObserver statusObserver;
    private GameStatus gameStatus = FREEDOM;
    private Skin skin;
    private CellHighlighter cellHighlighter;

    @Override
    public void create() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freedom.atlas"));
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        skin.addRegions(atlas);
        board = new GuiBoard(skin, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        board.setClickListener(new TileClickListener(this));
        statusObserver = new FreedomBoardStatusObserver(board);
        cellHighlighter = new CellHighlighter(board);
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    @Override
    public void render() {
        super.render();
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
            currentScreen.getLogArea().appendText(textToAppend);
        } else {
            Gdx.app.error(GAME_TAG, "Unable to print to game screen text area");
        }
    }

    @Override
    public @NotNull Board<?> getBoard() {
        return board;
    }

    @Override
    public @NotNull Player getFirstPlayer() {
        return whitePlayer;
    }

    @Override
    public @NotNull Player getSecondPlayer() {
        return blackPlayer;
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
            GameOverDialog gameOverDialog = new GameOverDialog(this, board.getSkin());
            gameOverDialog.show(board.getStage());
        }
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
        if (gameStatus == LAST_MOVE) {
            LastMoveDialog lastMoveDialog = new LastMoveDialog(this, board.getSkin());
            lastMoveDialog.show(board.getStage());
            gameStatus = GAME_OVER;
        }
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
        board.putPiece(new GuiStone(currentPlayer.getColor(), getPlayerStoneImage(currentPlayer.getColor())), currentPosition);
        updateLogArea(move);
        playersMovesHistory.add(move);
    }

    private void updateLogArea(@NotNull Move move) {
        if (move.getPlayer().getColor() == Color.WHITE) {
            printFormattedMoveOnTheLogAreaForFirstPlayer(move.getPosition());
        } else {
            appendTextToLogArea("     " + move.getPosition() + "\n");
        }
    }

    @NotNull
    private Image getPlayerStoneImage(@NotNull Color color) {
        if(color == Color.WHITE) return new Image(skin.get("white_checker", TextureRegion.class));
        return new Image(skin.get("black_checker", TextureRegion.class));
    }


    private void printFormattedMoveOnTheLogAreaForFirstPlayer(@NotNull Position inputPosition) {
        long currentStep = playersMovesHistory.stream()
                .filter(move -> move.getPlayer().getColor() == Color.WHITE)
                .count();
        if (currentStep < 10) {
            appendTextToLogArea("  " + currentStep + ". " + inputPosition);
        } else if (currentStep < 100) {
            appendTextToLogArea(" " + currentStep + ". " + inputPosition);
        } else {
            appendTextToLogArea(currentStep + ". " + inputPosition);
        }
    }
}
