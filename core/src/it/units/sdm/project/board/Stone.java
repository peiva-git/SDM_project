package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents a {@link Piece} implementation
 * to be used in a text-based Freedom game implementation,
 * that may be placed on a {@link it.units.sdm.project.board.Board}.
 */
public class Stone implements Piece {

    @NotNull
    private final Color color;

    /**
     * Creates a new {@link Stone} instance to put on a {@link Board}
     * @param color {@code this} {@link Stone}'s {@link Color}.
     *              Can be either {@link Color#BLACK} or {@link Color#WHITE}
     */
    public Stone(@NotNull Color color) {
        if (!isColorValid(color)) {
            throw new IllegalArgumentException("Invalid stone color, can be either black or white");
        }
        this.color = color;
    }
    private static boolean isColorValid(@NotNull Color stoneColor) {
        return stoneColor == Color.BLACK || stoneColor == Color.WHITE;
    }

    @Override
    public @NotNull Color getPieceColor() {
        return color;
    }

    /**
     * Two {@link Stone}s are equal if they are of the same {@link Color}.
     * @param obj {@link Object} to be compared with {@code this} {@link Stone}
     * @return {@code true} if the two {@link Stone}s are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Piece) obj).getPieceColor() == color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    /**
     * A {@link String} representation of {@code this} {@link Stone}.
     * Can be either {@code W} for a {@link Color#WHITE} {@link Stone}, or {@code B} for a {@link Color#BLACK} {@link Stone}
     * @return A {@link String} representation of the {@link Stone} class, suitable for a text-based representation
     */
    @Override
    public String toString() {
        return color.equals(Color.WHITE) ? "W" : "B";
    }
}
