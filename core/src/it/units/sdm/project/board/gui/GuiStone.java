package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

/**
 * Representation of a checker to be used as a part of a <a href="https://libgdx.com/wiki/">libGDX</a>
 * scene2d graphical user interface.
 */
public class GuiStone extends Stone {

    @NotNull
    private final Image image;

    /**
     * Create a new {@link GuiStone} instance
     * @param color Color of the stone
     * @param image Stone image to be used in a libgdx scene2d GUI
     */
    public GuiStone(@NotNull Color color, @NotNull Image image) {
        super(color);
        this.image = image;
    }

    /**
     * Gets the stone image
     * @return The stone image
     */
    @NotNull
    public Image getActor() {
        return image;
    }
}
