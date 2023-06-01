package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Game {

    @NotNull Board<?> getBoard();
    @NotNull Player getFirstPlayer();
    @NotNull Player getSecondPlayer();
    @Nullable Move getLastMove();
    void nextMove(@NotNull Position position);
    void reset();

}
