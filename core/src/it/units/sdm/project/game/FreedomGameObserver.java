package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.FreedomBoardHelper;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreedomGameObserver {

    @Nullable
    public static Color getCurrentWinnerColor(@NotNull Board<? extends Stone> board) {
        int whiteScore = getCurrentScore(board, Color.WHITE);
        int blackScore = getCurrentScore(board, Color.BLACK);
        if (whiteScore > blackScore) {
            return Color.WHITE;
        } else if (blackScore > whiteScore) {
            return Color.BLACK;
        }
        return null;
    }

    private static int getCurrentScore(@NotNull Board<? extends Stone> board, @NotNull Color playerColor) {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        return freedomPointsCounter.getPlayerScore(playerColor);
    }

    @NotNull
    public static GameStatus getCurrentGameStatus(@NotNull Board<? extends Stone> board, @Nullable Move lastMove) {
        long numberOfFreeCells = FreedomBoardHelper.getNumberOfFreeCells(board);
        if (numberOfFreeCells > 1) {
            if (lastMove == null || FreedomBoardHelper.areAdjacentCellsOccupied(board, lastMove.getPosition())) {
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
