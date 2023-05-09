package it.units.sdm.project.game;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Move {

    @NotNull
    private final Player player;
    @NotNull
    private final Position position;

    public Move(@NotNull Player player, @NotNull Position position) {
        this.player = player;
        this.position = position;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return player.equals(move.player) && position.equals(move.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, position);
    }
}
