package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a stone which can be placed on a {@link Board}.
 */
public class Stone {
    @NotNull
    private final Color color;

    /**
     * Creates a new Stone instance to put on a Board
     * @param color The stone's color. In a game of checkers, this would be black or white
     */
    public Stone(@NotNull Color color) {
        this.color = color;
    }

    /**
     * Returns this stone's color
     * @return This stone's color
     */
    @NotNull
    public Color getColor() {
        return this.color;
    }

    /**
     * Two stones are equal if they are of the same color.
     * @param obj Stone to be compared with this stone
     * @return True if the two stones are equals, otherwise it returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Stone) obj).getColor() == color;
    }

    /**
     * String representation of the stone.
     * @return String representation of the <a href="https://javadoc.io/static/com.badlogicgames.gdx/gdx/1.9.10/com/badlogic/gdx/graphics/Color.html">Color</a> class.
     */
    @Override
    public String toString() {
        return color.toString();
    }
}
