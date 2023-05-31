package it.units.sdm.project.board.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;

public class TileClickListener extends ClickListener {

    @NotNull
    private final Stack tileAndPiece;

    public TileClickListener(@NotNull Stack tileAndPiece) {
        this.tileAndPiece = tileAndPiece;
    }

}
