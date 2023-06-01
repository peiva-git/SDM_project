package it.units.sdm.project.game;

import it.units.sdm.project.board.FreedomBoardHelper;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiFreedomBoard;
import it.units.sdm.project.game.gui.FreedomGame;
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
    public GameStatus getStatus() {
        return status;
    }

    public void proceedToNextState() {
        switch (status) {
            case LAST_MOVE:
                status = GameStatus.PLAY_LAST_MOVE;
                break;
            case PLAY_LAST_MOVE:
                status = GameStatus.GAME_OVER;
                break;
            case GAME_OVER:
                status = GameStatus.FREEDOM;
                break;
            case FREEDOM:
            case NO_FREEDOM:
                Position lastChosenPosition = game.getPlayersMovesHistory().getLast().getPosition();
                if (FreedomBoardHelper.getNumberOfFreeCells(board) == 1) {
                    status = GameStatus.FREEDOM;
                } else if (FreedomBoardHelper.areAdjacentCellsOccupied(board, lastChosenPosition)) {
                    status = GameStatus.LAST_MOVE;
                } else {
                    status = GameStatus.NO_FREEDOM;
                }
                break;
        }
    }

    public enum GameStatus {
        EXIT,
        GAME_OVER,
        DISPLAY_WINNER,
        FREEDOM,
        NO_FREEDOM,
        LAST_MOVE,
        PLAY_LAST_MOVE
    }
}
