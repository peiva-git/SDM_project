package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Representation of a {@link Piece} which can be placed on a {@link Board}.
 */
public class Piece {
    @NotNull
    private final Color color;
    @Nullable
    private final Image image;

    /**
     * Creates a new {@link Piece} instance to put on a {@link Board}
     * @param color The {@link Piece}'s {@link Color}.
     * In a game of checkers, this would be {@link Color#BLACK} or {@link Color#WHITE}
     */
    public Piece(@NotNull Color color) {
        this.color = color;
        this.image = null;
    }

    public Piece(@NotNull Color color, @Nullable Image image) {
        this.color = color;
        this.image = image;
    }

    /**
     * Returns this {@link Piece}'s {@link Color}
     * @return This {@link Piece}'s {@link Color}
     */
    @NotNull
    public Color getColor() {
        return this.color;
    }
    @Nullable
    public Image getActor() {
        return image;
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
        return ((Piece) obj).getColor() == color;
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
