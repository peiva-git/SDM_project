package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing a {@link Stone} that may be placed on a {@link GuiBoard}.
 * Aside from the {@link Color}, an instance of this object also holds information about the
 * {@link com.badlogic.gdx.scenes.scene2d.Actor} that represents this {@link Stone} on the
 * {@link GuiBoard}.
 * @author Manuel Kosovel, Ivan Pelizon
 */
public class GuiStone extends Stone {

    @NotNull
    private final Image image;

    public GuiStone(@NotNull Color color, @NotNull Image image) {
        super(color);
        this.image = image;
    }

    @NotNull
    public Image getActor() {
        return image;
    }
}
