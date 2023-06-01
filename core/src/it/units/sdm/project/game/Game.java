package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.gui.GameStatusHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Game {

    @NotNull Board<?> getBoard();
    @NotNull Set<Player> getPlayers();
    @Nullable Move getLastMove();
    void nextMove(@NotNull Move move);

}
