package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BoardGame {

    @NotNull Board<?> getBoard();
    @NotNull Player getFirstPlayer();
    @NotNull Player getSecondPlayer();
    @Nullable Move getLastMove();
    void nextMove(@NotNull Position position);
    default @NotNull Player getNextPlayer() {
        Player firstPlayer = getFirstPlayer();
        Player secondPlayer = getSecondPlayer();
        if(getLastMove() == null || getLastMove().getPlayer().equals(secondPlayer)) return firstPlayer;
        return secondPlayer;
    }
    void reset();

}
