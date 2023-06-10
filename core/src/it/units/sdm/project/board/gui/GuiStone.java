package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a {@link Stone} that may be placed on a {@link GuiBoard}.
 * Aside from the {@link Color}, an instance of this object also holds information about the
 * {@link com.badlogic.gdx.scenes.scene2d.Actor} that represents this {@link Stone} on the
 * {@link GuiBoard}.
 */
public class GuiStone extends Stone {

    @NotNull
    private final Image image;

    /**
     * Creates a new {@link GuiStone} instance
     * @param color The {@link Color} of the {@link Stone}
     * @param image The stone's {@link Image} to be used in a libgdx scene2d GUI
     */
    public GuiStone(@NotNull Color color, @NotNull Image image) {
        super(color);
        this.image = image;
    }

    /**
     * Gets the stone {@link Image}
     * @return The {@link com.badlogic.gdx.scenes.scene2d.Actor} related to this {@link GuiStone}
     */
    @NotNull
    public Image getActor() {
        return image;
    }
}
