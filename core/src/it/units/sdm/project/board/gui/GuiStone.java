package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

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
