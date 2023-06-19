package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents a {@link it.units.sdm.project.game.gui.FreedomGame} {@link Piece} that may be placed on a {@link it.units.sdm.project.board.Board}.
 */
public class Stone implements Piece {

    @NotNull
    private final Color color;

    /**
     * Creates a new {@link Piece} instance to put on a {@link Board}
     * @param color The {@link Piece}'s {@link Color}.
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
     * Two {@link Piece}s are equal if they are of the same {@link Color}.
     * @param obj {@link Object} to be compared with this {@link Piece}
     * @return {@code true} if the two {@link Piece}s are equal, {@code false}
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
     * {@link String} representation of the {@link Piece}.
     * @return {@link String} representation of the <a href="https://javadoc.io/static/com.badlogicgames.gdx/gdx/1.9.10/com/badlogic/gdx/graphics/Color.html">{@link Color}</a> class.
     */
    @Override
    public String toString() {
        return color.toString();
    }
}
