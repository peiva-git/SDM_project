package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

    public GuiStone(@NotNull Color color, @NotNull Texture texture) {
        super(color);
        this.image = new Image(texture);
    }

    @NotNull
    public Image getImage() {
        return image;
    }
}
