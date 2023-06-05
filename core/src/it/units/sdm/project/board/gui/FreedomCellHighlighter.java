package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static it.units.sdm.project.board.gui.GuiBoard.fromTileCoordinatesToBoardPosition;

/**
 * This class provides helper methods to highlight valid moves in a {@link it.units.sdm.project.game.gui.FreedomGame}.
 */
public class FreedomCellHighlighter {
    /**
     * {@link GuiBoard#DARK_TILE}'s highlighted default {@link Color}
     */
    private static final Color HIGHLIGHT_DARK_TILE = new Color(105 / 255f, 105 / 255f, 105 / 255f, 255 / 255f);
    /**
     * {@link GuiBoard#LIGHT_TILE}'s highlighted default {@link Color}
     */
    private static final Color HIGHLIGHT_LIGHT_TILE = new Color(169 / 255f, 169 / 255f, 169 / 255f, 255 / 255f);
    @NotNull
    private final GuiBoard board;

    /**
     * Creates a new highlighter instance, to be used on the provided {@link GuiBoard}.
     * @param board The board on which cells should be highlighted
     */
    public FreedomCellHighlighter(@NotNull GuiBoard board) {
        this.board = board;
    }

    /**
     * Resets the currently highlighted cells on the provided {@link GuiBoard}
     * If there aren't any, this method does nothing
     */
    @SuppressWarnings("unchecked")
    public void resetCurrentlyHighlightedCellsIfAny() {
        for (int i = 0; i < board.getCells().size; i++) {
            Cell<Actor> cell = board.getCells().get(i);
            Group tileAndPiece = (Group) cell.getActor();
            Actor tile = tileAndPiece.getChild(0);
            if (tile.getColor().equals(HIGHLIGHT_DARK_TILE)) {
                tile.setColor(GuiBoard.DARK_TILE);
            } else if (tile.getColor().equals(HIGHLIGHT_LIGHT_TILE)) {
                tile.setColor(GuiBoard.LIGHT_TILE);
            }
        }
    }

    /**
     * Highlights the provided {@link Position}s on the current {@link GuiBoard}.
     * @param positionsToHighlight The {@link Position}s that need to be highlighted
     */
    public void highlightPositions(@NotNull Set<Position> positionsToHighlight) {
        List<Cell<Actor>> cellsToHighlight = getCellsToHighlight(positionsToHighlight);
        for (Cell<Actor> cellToHighlight : cellsToHighlight) {
            highlightCell(cellToHighlight);
        }
    }

    @SuppressWarnings("unchecked")
    private @NotNull List<Cell<Actor>> getCellsToHighlight(@NotNull Set<Position> positionsToHighlight){
        List<Cell<Actor>> cellsToHighlight = new ArrayList<>();
        for (int i = 0; i < board.getCells().size; i++) {
            Cell<Actor> cell = board.getCells().get(i);
            if (positionsToHighlight.contains(fromTileCoordinatesToBoardPosition(cell.getRow(), cell.getColumn()))) {
                cellsToHighlight.add(cell);
            }
        }
        return cellsToHighlight;
    }

    private void highlightCell(@NotNull Cell<Actor> cellToHighlight) {
        Stack tileAndPiece = (Stack) cellToHighlight.getActor();
        Actor tile = tileAndPiece.getChild(0);
        if (isIndexEven(cellToHighlight.getRow())) {
            if (isIndexEven(cellToHighlight.getColumn())) {
                tile.setColor(HIGHLIGHT_LIGHT_TILE);
            } else {
                tile.setColor(HIGHLIGHT_DARK_TILE);
            }
        } else {
            if (isIndexEven(cellToHighlight.getColumn())) {
                tile.setColor(HIGHLIGHT_DARK_TILE);
            } else {
                tile.setColor(HIGHLIGHT_LIGHT_TILE);
            }
        }
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }
}
