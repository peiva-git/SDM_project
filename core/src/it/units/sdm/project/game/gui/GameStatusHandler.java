package it.units.sdm.project.game.gui;

import it.units.sdm.project.board.BoardUtils;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiFreedomBoard;
import org.jetbrains.annotations.NotNull;

public class GameStatusHandler {

    @NotNull
    private final FreedomGame game;
    @NotNull
    private final GuiFreedomBoard board;
    @NotNull
    private GameStatus status = GameStatus.FREEDOM;

    public GameStatusHandler(@NotNull FreedomGame game, @NotNull GuiFreedomBoard board) {
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
                Position lastChosenPosition = game.getPlayersMovesHistory().getLast().getPosition();
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
