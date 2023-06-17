import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.game.gui.FreedomCellHighlighter;
import it.units.sdm.project.game.gui.FreedomGame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import runners.GdxTestRunner;

import java.util.Arrays;

@RunWith(GdxTestRunner.class)
public class CellHighlighterTests {

    @BeforeAll
    static void init() {
        VisUI.load();
    }

    @Test
    void testCellHighlighting() {
        GuiBoard<GuiStone> board = new GuiBoard<>(8, 8);
        FreedomCellHighlighter cellHighlighter = new FreedomCellHighlighter(board);
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray())
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE && tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
                });
        Assertions.assertTrue(areCellsNotHighlighted);
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
    }
}
