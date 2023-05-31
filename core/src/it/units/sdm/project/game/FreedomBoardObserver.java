package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.BoardUtils;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreedomBoardObserver {

    @NotNull
    Board<? extends Stone> board;

    public FreedomBoardObserver(@NotNull Board<? extends Stone> board) {
        this.board = board;
    }

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

    private int getCurrentScore(@NotNull Player player) {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        return freedomPointsCounter.getPlayerScore(player);
    }

    @NotNull
    public GameStatus getCurrentGameStatus(@Nullable Move lastMove) {
        long numberOfFreeCells = BoardUtils.getNumberOfFreeCells(board);
        if (numberOfFreeCells > 1) {
            if (lastMove == null || BoardUtils.areAdjacentCellsOccupied(board, lastMove.getPosition())) {
                return GameStatus.FREEDOM;
            } else {
                return GameStatus.NO_FREEDOM;
            }
        } else if (numberOfFreeCells == 1) {
            return GameStatus.LAST_MOVE;
        }
        return GameStatus.GAME_OVER;
    }

}
