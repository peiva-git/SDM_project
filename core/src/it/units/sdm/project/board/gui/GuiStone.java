package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents a {@link it.units.sdm.project.game.gui.FreedomGame} {@link Piece} that may be placed on a {@link it.units.sdm.project.board.Board}.
 * Aside from the {@link Color}, an instance of this object also holds information about the
 * {@link com.badlogic.gdx.scenes.scene2d.Actor} that represents this {@link Piece} on the
 * {@link GuiBoard}.
 */
public class GuiStone extends Image implements Piece {

    @NotNull
    private final Color stoneColor;
    @NotNull
    private final TextureRegion image;

    /**
     * Creates a new {@link GuiStone} instance
     *
     * @param stoneColor The {@link Piece}'s {@link Color}.
     *                   Can be either {@link Color#BLACK} or {@link Color#WHITE}
     * @param image      The {@link Piece}'s {@link TextureRegion} to be drawn in a libgdx scene2d GUI
     */
    public GuiStone(@NotNull Color stoneColor, @NotNull TextureRegion image) {
        super(image);
        if (!isColorValid(stoneColor)) {
            throw new IllegalArgumentException("Invalid stone color, can be either black or white");
        }
        this.stoneColor = stoneColor;
        this.image = image;
    }

    private static boolean isColorValid(@NotNull Color stoneColor) {
        return stoneColor == Color.BLACK || stoneColor == Color.WHITE;
    }

    @Override
    public @NotNull Color getPieceColor() {
        return stoneColor;
    }

    /**
     * Two {@link GuiStone}s are equal if they are of the same {@link Color} and have the same image.
     * {@code this} {@link Image} is specified by the {@link TextureRegion} that was supplied in the constructor
     *
     * @param o {@link Object} to be compared with {@code this} {@link Piece}
     * @return {@code true} if the two {@link Piece}s are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiStone guiStone = (GuiStone) o;
        return stoneColor.equals(guiStone.stoneColor) && image.equals(guiStone.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoneColor, image);
    }
}
