package game.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Color;
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
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class CellHighlighterTests {

    private static final int boardSize = 8;
    private static HeadlessApplication application;
    private static GuiBoard<GuiStone> board;
    private static FreedomCellHighlighter cellHighlighter;

    @BeforeAll
    static void init() {
        application = initHeadlessApplication("freedom.atlas");
        board = new GuiBoard<>(boardSize);
        cellHighlighter = new FreedomCellHighlighter(board);
    }

    @Test
    void testCellHighlightingOnEmptyBoard() {
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray(Cell.class))
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != HIGHLIGHT_LIGHT_TILE && tile.getColor() != HIGHLIGHT_DARK_TILE;
                });
        assertTrue(areCellsNotHighlighted);
    }

    @ParameterizedTest
    @MethodSource("game.providers.CellHighlighterProviders#provideALightAndADarkDefaultAndHighlightedTilePair")
    void testCellHighlighting(Color defaultTileColor, Color highlightedTileColor) {
        Set<Position> positionsToHighlight = new HashSet<>();
        List<Actor> lightColoredTiles = Arrays.stream(board.getCells().toArray(Cell.class))
                .filter(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    return tileAndPiece.getChild(0).getColor().equals(defaultTileColor);
                })
                .map(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    positionsToHighlight.add(board.fromTileCoordinatesToBoardPosition(cell.getRow(), cell.getColumn()));
                    return tileAndPiece.getChild(0);
                })
                .collect(Collectors.toList());
        cellHighlighter.highlightPositions(positionsToHighlight);
        lightColoredTiles.forEach(tile -> assertEquals(highlightedTileColor, tile.getColor()));
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
