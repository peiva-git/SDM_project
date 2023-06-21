package it.units.sdm.project.board.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.BoardGame;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a {@link ClickListener} to be used on each tile of a {@link GuiBoard}.
 */
public class TileClickListener extends ClickListener {
    @NotNull
    private final BoardGame<?> boardGame;

    /**
     * Creates a new listener to be used with a {@link GuiBoard}. By default,
     * the listener will simply execute the {@link BoardGame#nextMove(Position)} method from the {@link BoardGame}
     * interface using the clicked tile's {@link Position}.
     * If a different behaviour is expected, the {@link ClickListener#clicked(InputEvent, float, float)} method should be overridden.
     * @param boardGame The {@link BoardGame} instance on which to listen for tile click events
     */
    public TileClickListener(@NotNull BoardGame<?> boardGame) {
        this.boardGame = boardGame;
    }

    /**
     * Handles click events on {@link Actor}s
     * @param event Dispatched event on clicked {@link Actor}
     * @param x Horizontal coordinate of the click event
     * @param y Vertical coordinate of the click event
     */
    @Override
    public void clicked(@NotNull InputEvent event, float x, float y) {
        GuiBoard<?> board = (GuiBoard<?>) boardGame.getBoard();
        Cell<Actor> cell = board.getCell(event.getListenerActor());
        boardGame.nextMove(board.fromTileCoordinatesToBoardPosition(cell.getRow(), cell.getColumn()));
        super.clicked(event, x, y);
    }
}
