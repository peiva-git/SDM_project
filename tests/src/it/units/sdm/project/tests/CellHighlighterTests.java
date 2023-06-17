package it.units.sdm.project.tests;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CellHighlighterTests {

    private HeadlessApplication application;

    @Before
    public void init() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(mock(ApplicationListener.class), config);
        Gdx.gl = mock(GL20.class);
        VisUI.load();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freedom.atlas"));
        VisUI.getSkin().addRegions(atlas);
    }

    @Test
    public void testCellHighlighting() {
        assertTrue(Gdx.files.internal("freedom.png").exists());
        GuiBoard<GuiStone> board = new GuiBoard<>(8, 8);
        FreedomCellHighlighter cellHighlighter = new FreedomCellHighlighter(board);
        boolean areCellsNotHighlighted = Arrays.stream(board.getCells().toArray(Cell.class))
                .allMatch(cell -> {
                    Group tileAndPiece = (Group) cell.getActor();
                    Actor tile = tileAndPiece.getChild(0);
                    return tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE && tile.getColor() != FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
                });
        assertTrue(areCellsNotHighlighted);
    }

    @After
    public void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
