package libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.game.gui.FreedomCellHighlighter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

public class CellHighlighterTests {

    private static HeadlessApplication application;

    @BeforeAll
    public static void init() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(mock(ApplicationListener.class), config);
        Gdx.gl = mock(GL20.class);
        VisUI.load();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freedom.atlas"));
        VisUI.getSkin().addRegions(atlas);
    }

    @Test
    public void testCellHighlighting() {
        Assertions.assertTrue(Gdx.files.internal("freedom.png").exists());
        GuiBoard<GuiStone> board = new GuiBoard<>(8, 8);
        FreedomCellHighlighter cellHighlighter = new FreedomCellHighlighter(board);
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray(Cell.class))
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE && tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
                });
        Assertions.assertTrue(areCellsNotHighlighted);
    }

    @AfterAll
    public static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
