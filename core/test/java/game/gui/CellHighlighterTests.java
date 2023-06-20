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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Set;

import static it.units.sdm.project.board.gui.GuiBoard.DARK_TILE;
import static it.units.sdm.project.board.gui.GuiBoard.LIGHT_TILE;
import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class CellHighlighterTests {

    private static final int numberOfRows = 8;
    private static final int numberOfColumns = 8;
    private static HeadlessApplication application;
    private static GuiBoard<GuiStone> board;
    private static FreedomCellHighlighter cellHighlighter;

    @BeforeAll
    static void init() {
        application = initHeadlessApplication("freedom.atlas");
        board = new GuiBoard<>(numberOfRows, numberOfColumns);
        cellHighlighter = new FreedomCellHighlighter(board);
    }

    @Test
    void testCellHighlightingOnEmptyBoard() {
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray(Cell.class))
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE && tile.getColor() != HIGHLIGHT_DARK_TILE;
                });
        assertTrue(areCellsNotHighlighted);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @SuppressWarnings("unchecked")
    void testCellHighlightingOnFirstLightAndFirstDarkCellOfTheLastRow(int cellIndex) {
        Cell<Actor> cell = board.getCells().get(cellIndex);
        Group tileAndPiece = (Group) cell.getActor();
        assertEquals(cellIndex == 0 ? LIGHT_TILE : DARK_TILE, tileAndPiece.getChild(0).getColor());
        cellHighlighter.highlightPositions(Set.of(Position.fromCoordinates(numberOfRows - 1, cellIndex)));
        assertEquals(cellIndex == 0 ? HIGHLIGHT_LIGHT_TILE : HIGHLIGHT_DARK_TILE, tileAndPiece.getChild(0).getColor());
    }

    @ParameterizedTest
    @ValueSource(ints = {numberOfColumns, numberOfColumns + 1})
    @SuppressWarnings("unchecked")
    void testCellHighlightingOnLightAndDarkCellOfTheSecondLastRow(int cellIndex) {
        Cell<Actor> cell = board.getCells().get(cellIndex);
        Group tileAndPiece = (Group) cell.getActor();
        assertEquals(cellIndex != numberOfColumns ? LIGHT_TILE : DARK_TILE, tileAndPiece.getChild(0).getColor());
        cellHighlighter.highlightPositions(Set.of(Position.fromCoordinates(numberOfRows - 2, cellIndex - numberOfColumns)));
        assertEquals(cellIndex != numberOfColumns ? HIGHLIGHT_LIGHT_TILE : HIGHLIGHT_DARK_TILE, tileAndPiece.getChild(0).getColor());
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
