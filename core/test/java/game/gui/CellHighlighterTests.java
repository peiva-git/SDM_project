package game.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.game.gui.FreedomCellHighlighter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

public class CellHighlighterTests {

    private static final int numberOfRows = 8;
    private static final int numberOfColumns = 8;
    private static HeadlessApplication application;
    private static GuiBoard<GuiStone> board;
    private static FreedomCellHighlighter cellHighlighter;

    @BeforeAll
    static void init() {
        application = initHeadlessApplication();
        board = new GuiBoard<>(numberOfRows, numberOfColumns);
        cellHighlighter = new FreedomCellHighlighter(board);
    }

    @Test
    void testCellHighlightingOnEmptyBoard() {
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray(Cell.class))
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE && tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
                });
        assertTrue(areCellsNotHighlighted);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCellHighlightingOnLightCell() {
        Cell<Actor> lightCell = board.getCells().get(0);
        Group tileAndPiece = (Group) lightCell.getActor();
        assertEquals(GuiBoard.LIGHT_TILE, tileAndPiece.getChild(0).getColor());
        cellHighlighter.highlightPositions(Set.of(Position.fromCoordinates(numberOfRows - 1, 0)));
        assertEquals(FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE, tileAndPiece.getChild(0).getColor());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCellHighlightingOnDarkCell() {
        Cell<Actor> darkCell = board.getCells().get(1);
        Group tileAndPiece = (Group) darkCell.getActor();
        assertEquals(GuiBoard.DARK_TILE, tileAndPiece.getChild(0).getColor());
        cellHighlighter.highlightPositions(Set.of(Position.fromCoordinates(numberOfRows - 1, 1)));
        assertEquals(FreedomCellHighlighter.HIGHLIGHT_DARK_TILE, tileAndPiece.getChild(0).getColor());
    }

    @AfterEach
    void resetCellsHighlighting() {
        cellHighlighter.resetCurrentlyHighlightedCellsIfAny();
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
