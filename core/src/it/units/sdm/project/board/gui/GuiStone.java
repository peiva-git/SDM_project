package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents a {@link Piece} that may be placed on a {@link GuiBoard}.
 * Aside from the {@link Color}, an instance of this object also holds information about the
 * {@link com.badlogic.gdx.scenes.scene2d.Actor} that represents this {@link Piece} on the
 * {@link GuiBoard}.
 */
public class GuiStone extends Image implements Piece {

    @NotNull
    private final Color playerColor;
    @NotNull
    private final TextureRegion image;

    /**
     * Creates a new {@link GuiStone} instance
     * @param playerColor The {@link Color} of the {@link it.units.sdm.project.game.Player} using {@code this} {@link Piece}
     * @param image {@code this} stone's {@link TextureRegion} to be drawn in a libgdx scene2d GUI
     */
    public GuiStone(@NotNull Color playerColor, @NotNull TextureRegion image) {
        super(image);
        this.playerColor = playerColor;
        this.image = image;
    }

    @Override
    public @NotNull Color getPieceColor() {
        return playerColor;
    }

    /**
     * Two {@link GuiStone}s are equal if they are of the same {@link Color} and have the same image.
     * {@code this} {@link Image} is specified by the {@link TextureRegion} that was supplied in the constructor
     * @param o {@link Object} to be compared with {@code this} {@link Piece}
     * @return {@code true} if the two {@link Piece}s are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiStone guiStone = (GuiStone) o;
        return playerColor.equals(guiStone.playerColor) && image.equals(guiStone.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, image);
    }
}
