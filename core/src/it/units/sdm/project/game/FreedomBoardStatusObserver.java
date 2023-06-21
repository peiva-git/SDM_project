package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class obtains the current {@link GameStatus} and the {@link Player} that's currently winning
 * at the Freedom game that's being played on the supplied {@link Board}. The game could be in 4 different states:
 * {@link GameStatus#FREEDOM}, {@link GameStatus#NO_FREEDOM}, {@link GameStatus#LAST_MOVE}, {@link GameStatus#GAME_OVER}
 */
public class FreedomBoardStatusObserver {

    @NotNull
    Board<? extends Piece> board;

    /**
     * Creates a new {@link GameStatus} observer.
     * @param board The {@link Board} on which to observe the state changes
     */
    public FreedomBoardStatusObserver(@NotNull Board<? extends Piece> board) {
        this.board = board;
    }

    /**
     * Returns the {@link Color} of the {@link Player} who's currently winning among the two {@link Player}s
     * @return The {@link Color} of the {@link Player} who's currently winning, or {@code null} if it's a tie
     */
    @Nullable
    public Color getCurrentWinner() {
        int whiteScore = getCurrentScore(Color.WHITE);
        int blackScore = getCurrentScore(Color.BLACK);
        if (whiteScore > blackScore) {
            return Color.WHITE;
        } else if (blackScore > whiteScore) {
            return Color.BLACK;
        }
        return null;
    }

    /**
     * Returns the {@link Player}'s current score
     * @param playerColor The chosen {@link Player}
     * @return The {@link Player}'s current score
     */
    private int getCurrentScore(@NotNull Color playerColor) {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        return freedomPointsCounter.getPlayerScore(playerColor);
    }

    /**
     * Returns the current state of the game, given the last {@link Move} and the situation on the {@link Board}.
     * @param lastMove The last played {@link Move}
     * @return The current state of the {@link BoardGame}
     */
    @NotNull
    public GameStatus getCurrentGameStatus(@Nullable Move lastMove) {
        long numberOfFreeCells = board.getNumberOfFreeCells();
        if (numberOfFreeCells > 1) {
            if (lastMove == null || board.areAdjacentCellsOccupied(lastMove.getPosition())) {
                return GameStatus.FREEDOM;
            }
            return GameStatus.NO_FREEDOM;
        } else if (numberOfFreeCells == 1) {
            return GameStatus.LAST_MOVE;
        }
        return GameStatus.GAME_OVER;
    }

    /**
     * Describes the 4 different {@link GameStatus} states of a Freedom game.
     */
    public enum GameStatus {
        /**
         * In case the {@link Player} can place a new {@link Piece} anywhere on the {@link Board}.
         * This happens on the very first
         * round of the game or when there are no {@link it.units.sdm.project.board.Position}s adjacent to the previously played one.
         */
        FREEDOM,
        /**
         * In case the {@link Player} must place the new {@link Piece} near the previously played one.
         * This happens when there
         * are free {@link it.units.sdm.project.board.Position}s adjacent to the previously played one.
         */
        NO_FREEDOM,
        /**
         * In case there's only one {@link it.units.sdm.project.board.Position} left on the board.
         * The {@link Player} can decide
         * to either play the last {@link Piece} or to end the game by skipping the last turn.
         */
        LAST_MOVE,
        /**
         * In case the {@link Board} is full or in case the {@link GameStatus#LAST_MOVE} was skipped.
         */
        GAME_OVER
    }
}

