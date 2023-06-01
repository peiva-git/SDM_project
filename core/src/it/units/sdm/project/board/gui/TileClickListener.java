package it.units.sdm.project.board.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.BoardGame;
import org.jetbrains.annotations.NotNull;

public class TileClickListener extends ClickListener {
    @NotNull
    private final BoardGame game;

    public TileClickListener(@NotNull BoardGame game) {
        this.game = game;
    }

    @Override
    public void clicked(@NotNull InputEvent event, float x, float y) {
        Cell<Actor> cell = ((GuiBoard) game.getBoard()).getCell(event.getListenerActor());
        game.nextMove(Position.fromCoordinates(cell.getRow(), cell.getColumn()));
        super.clicked(event, x, y);
    }

}
