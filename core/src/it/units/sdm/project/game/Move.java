package it.units.sdm.project.game;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a {@link Player}'s move in a {@link BoardGame}.
 */
public class Move {

    @NotNull
    private final Player player;
    @NotNull
    private final Position position;

    /**
     * Creates a wrapper to save a {@link Player}'s {@link Move} in a {@link BoardGame}
     * @param player The {@link Player} making the {@link Move}
     * @param position The chosen {@link Position}
     */
    public Move(@NotNull Player player, @NotNull Position position) {
        this.player = player;
        this.position = position;
    }

    /**
     * Returns the {@link Move}'s {@link Player}
     * @return The {@link Move}'s {@link Player}
     */
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * Returns the {@link Move}'s {@link Position}
     * @return The {@link Move}'s {@link Position}
     */
    public @NotNull Position getPosition() {
        return position;
    }

    /**
     * Two {@link Move}s are equal if the {@link Player}s and the {@link Position}s are equal
     * @param o The {@link Object} to compare {@code this} with
     * @return {@code true} if the {@link Move}s are equal, {@code false} otherwise
     */
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
