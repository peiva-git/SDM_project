package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a {@link Piece} which can be placed on a {@link Board}.
 */
public interface Piece {

    /**
     * Returns this {@link Piece}'s {@link Color}
     * @return This {@link Piece}'s {@link Color}
     */
    @NotNull
    Color getColor();
}
