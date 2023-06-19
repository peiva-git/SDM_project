package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a {@link it.units.sdm.project.game.BoardGame} {@link Piece} which can be placed on a {@link Board}.
 */
public interface Piece {

    /**
     * Returns {@code this} {@link Piece}'s {@link Color}
     * @return {@code this} {@link Piece}'s {@link Color}
     */
    @NotNull
    Color getPieceColor();
}
