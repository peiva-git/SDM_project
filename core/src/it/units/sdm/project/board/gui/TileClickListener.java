package it.units.sdm.project.board.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.BoardGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

/**
 * This class represents a click listener to be used on each tile of a {@link GuiBoard}.
 */
public class TileClickListener extends ClickListener {
    @NotNull
    private final BoardGame game;

    /**
     * Create a new listener to be used with a {@link it.units.sdm.project.board.Board}. By default,
     * the listener will simply execute the nextMove() method from the BoardGame interface using the clicked tile position.
     * If a different behaviour is expected, the clicked() method should be overridden.
     * @param game The game instance on which to listen for tile click events
     */
    public TileClickListener(@NotNull BoardGame game) {
        this.game = game;
    }

    /**
     * Handles click events on actors
     * @param event Event for actor clicked
     * @param x Horizontal coordinate of the click
     * @param y Vertical coordinate of the click
     */
    @Override
    public void clicked(@NotNull InputEvent event, float x, float y) {
        Cell<Actor> cell = ((GuiBoard) game.getBoard()).getCell(event.getListenerActor());
        game.nextMove(fromTileCoordinatesToBoardPosition(cell.getRow(), cell.getColumn()));
        super.clicked(event, x, y);
    }

    @NotNull
    private Position fromTileCoordinatesToBoardPosition(int tileRow, int tileColumn) {
        return Position.fromCoordinates(NUMBER_OF_ROWS - tileRow - 1, tileColumn);
    }
}
