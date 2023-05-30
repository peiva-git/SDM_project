package it.units.sdm.project.game;

import it.units.sdm.project.board.FreedomBoardHelper;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

public class GameStatusHandler {

    @NotNull
    private final FreedomGame game;
    @NotNull
    private GameStatus status = GameStatus.START;

    public GameStatusHandler(@NotNull FreedomGame game) {
        this.game = game;
    }

    @NotNull
    public GameStatus getStatus() {
        return status;
    }

    public void proceedToNextState() {
        switch (status) {
            case START:
                status = GameStatus.FREEDOM;
                break;
            case LAST_MOVE:
                status = GameStatus.GAME_OVER;
                break;
            case GAME_OVER:
                status = GameStatus.START;
                break;
            case FREEDOM:
            case NO_FREEDOM:
                Position lastChosenPosition = game.getPlayersMovesHistory().getLast().getPosition();
                if (FreedomBoardHelper.areAdjacentCellsOccupied(game.getBoard(), lastChosenPosition)) {
                    status = GameStatus.FREEDOM;
                } else if (FreedomBoardHelper.getNumberOfFreeCells(game.getBoard()) == 1) {
                    status = GameStatus.LAST_MOVE;
                } else {
                    status = GameStatus.NO_FREEDOM;
                }
                break;
            default:
                break;
        }
    }
}
