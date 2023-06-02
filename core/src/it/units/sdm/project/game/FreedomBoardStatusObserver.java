package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreedomBoardStatusObserver {

    @NotNull
    Board<? extends Stone> board;

    /**
     * Creates a new game status observer. Given the situation on the board, the game could be in 4
     * different states.
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
     * Returns the current state of the game, given the last move and the situation on the board.
     * The 4 possible states are FREEDOM, in case the player can place a new stone anywhere on the board;
     * NO_FREEDOM, in case the player must place the new stone adjacent to the previous one;
     * LAST_MOVE, in case there's only one position left on the board;
     * and finally GAME_OVER, in case the board is full
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

    public enum GameStatus {
        FREEDOM,
        NO_FREEDOM,
        LAST_MOVE,
        GAME_OVER
    }
}

