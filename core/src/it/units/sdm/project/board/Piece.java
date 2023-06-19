package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a {@link it.units.sdm.project.game.BoardGame} {@link Piece} which can be placed on a {@link Board}.
 */
public interface Piece {

    /**
     * Returns this {@link Piece}'s {@link it.units.sdm.project.game.Player}'s {@link Color}
     * @return This {@link Piece}'s {@link it.units.sdm.project.game.Player}'s {@link Color}
     */
    @NotNull
    Color getPieceColor();
}
