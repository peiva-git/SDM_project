package it.units.sdm.project.game;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a player's move of a {@link BoardGame}.
 */
public class Move {

    @NotNull
    private final Player player;
    @NotNull
    private final Position position;

    /**
     * Creates a wrapper to save a player's move in a board game
     * @param player The player making the move
     * @param position The chosen position
     */
    public Move(@NotNull Player player, @NotNull Position position) {
        this.player = player;
        this.position = position;
    }

    /**
     * Gets the move's player
     * @return The move's player
     */
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * Gets the move's position
     * @return The move's position
     */
    public @NotNull Position getPosition() {
        return position;
    }

    /**
     * Two moves are equal whether the moves players and the moves positions are
     * equal.
     * @param o Move to be compared with
     * @return True if the moves are equal, otherwise it returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return player.equals(move.player) && position.equals(move.position);
    }

}
