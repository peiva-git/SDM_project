package it.units.sdm.project.game.gui;

import it.units.sdm.project.board.BoardUtils;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import org.jetbrains.annotations.NotNull;

public class GameStatusHandler {

    @NotNull
    private final FreedomGame game;
    @NotNull
    private final GuiBoard board;
    @NotNull
    private GameStatus status = GameStatus.FREEDOM;

    public GameStatusHandler(@NotNull FreedomGame game, @NotNull GuiBoard board) {
        this.game = game;
        this.board = board;
    }

    @NotNull
    public GameStatus getCurrentStatus() {
        return status;
    }

    public void proceedToNextState() {
        switch (status) {
            case LAST_MOVE:
                status = GameStatus.GAME_OVER;
                break;
            case GAME_OVER:
                status = GameStatus.FREEDOM;
                break;
            case FREEDOM:
            case NO_FREEDOM:
                Position lastChosenPosition = game.getLastMove().getPosition();
                if (BoardUtils.getNumberOfFreeCells(board) == 1) {
                    status = GameStatus.LAST_MOVE;
                } else if (BoardUtils.areAdjacentCellsOccupied(board, lastChosenPosition)) {
                    status = GameStatus.FREEDOM;
                } else {
                    status = GameStatus.NO_FREEDOM;
                }
                break;
        }
    }

    public enum GameStatus {
        FREEDOM,
        NO_FREEDOM,
        LAST_MOVE,
        GAME_OVER
    }
}
