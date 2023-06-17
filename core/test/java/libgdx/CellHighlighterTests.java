package libgdx;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.game.gui.FreedomCellHighlighter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

public class CellHighlighterTests {

    private static HeadlessApplication application;
    private static GuiBoard<GuiStone> board;
    private static FreedomCellHighlighter cellHighlighter;

    @BeforeAll
    static void init() {
        application = initHeadlessApplication();
        board = new GuiBoard<>(8, 8);
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

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
