package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a {@link Stone} which can be placed on a {@link Board}.
 */
public class Stone {
    @NotNull
    private final Color color;

    /**
     * Creates a new {@link Stone} instance to put on a {@link Board}
     * @param color The {@link Stone}'s {@link Color}.
     * In a game of checkers, this would be {@link Color#BLACK} or {@link Color#WHITE}
     */
    public Stone(@NotNull Color color) {
        this.color = color;
    }

    /**
     * Returns this {@link Stone}'s {@link Color}
     * @return This {@link Stone}'s {@link Color}
     */
    @NotNull
    public Color getColor() {
        return this.color;
    }

    /**
     * Two {@link Stone}s are equal if they are of the same {@link Color}.
     * @param obj {@link Object} to be compared with this {@link Stone}
     * @return {@code true} if the two {@link Stone}s are equal, {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Stone) obj).getColor() == color;
    }

    /**
     * {@link String} representation of the {@link Stone}.
     * @return {@link String} representation of the <a href="https://javadoc.io/static/com.badlogicgames.gdx/gdx/1.9.10/com/badlogic/gdx/graphics/Color.html">{@link Color}</a> class.
     */
    @Override
    public String toString() {
        return color.toString();
    }
}
