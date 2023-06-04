package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class obtains the current {@link GameStatus} and the current winner {@link Player}
 * of a FreedomGame given the situation on the board. The game could be in 4 different {@link GameStatus}:
 * FREEDOM, NO_FREEDOM, LAST_MOVE, GAME_OVER
 */
public class FreedomBoardStatusObserver {

    @NotNull
    Board<? extends Stone> board;

    /**
     * Creates a new game status observer.
     * @param board The board on which to observe the state changes
     */
    public FreedomBoardStatusObserver(@NotNull Board<? extends Stone> board) {
        this.board = board;
    }

    /**
     * Returns the player who's currently winning among the two players
     * @param whitePlayer The first player
     * @param blackPlayer The second player
     * @return The player who's currently winning, or null i it's a tie
     */
    @Nullable
    public Player getCurrentWinner(@NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        int whiteScore = getCurrentScore(whitePlayer);
        int blackScore = getCurrentScore(blackPlayer);
        if (whiteScore > blackScore) {
            return whitePlayer;
        } else if (blackScore > whiteScore) {
            return blackPlayer;
        }
        return null;
    }

    /**
     * Returns the player's current score
     * @param player The chosen player
     * @return The player's current score
     */
    private int getCurrentScore(@NotNull Player player) {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        return freedomPointsCounter.getPlayerScore(player);
    }

    /**
     * Returns the current state of the game, given the last {@link Move} and the situation on the board.
     * @param lastMove The last played move
     * @return The current state of the game
     */
    @NotNull
    public GameStatus getCurrentGameStatus(@Nullable Move lastMove) {
        long numberOfFreeCells = board.getNumberOfFreeCells();
        if (numberOfFreeCells > 1) {
            if (lastMove == null || board.areAdjacentCellsOccupied(lastMove.getPosition())) {
                return GameStatus.FREEDOM;
            } else {
                return GameStatus.NO_FREEDOM;
            }
        } else if (numberOfFreeCells == 1) {
            return GameStatus.LAST_MOVE;
        }
        return GameStatus.GAME_OVER;
    }

    /**
     * Describes the 4 different {@link GameStatus} of a Freedom game.
     */
    public enum GameStatus {
        /**
         * In case the player can place a new stone anywhere on the board. This happens on the very first
         * play of the game and when there are no adjacent positions to the previous played one.
         */
        FREEDOM,
        /**
         * In case the player must place the new stone adjacent to the previous one. This happens when there
         * are adjacent positions to the previous played one available.
         */
        NO_FREEDOM,
        /**
         * In case there's only one position left on the board. The {@link Player} can either put the last stone
         * or end the game by skipping the last move.
         */
        LAST_MOVE,
        /**
         * In case the board is full or in case the LAST_MOVE was skipped.
         */
        GAME_OVER
    }
}

