package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

public class GuiStone extends Stone {

    @NotNull
    private final Image tile;

    public GuiStone(@NotNull Color color) {
        super(color);
        Texture stoneTexture;
        if(color == Color.WHITE) {
            stoneTexture =  new Texture(Gdx.files.internal("redCircle.png"));
        } else {
            stoneTexture =  new Texture(Gdx.files.internal("circle2.png"));
        }
        this.tile = new Image(stoneTexture);
    }

    @NotNull
    public Image getTile() {
        return tile;
    }
}
