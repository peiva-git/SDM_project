package board;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.gui.GuiStone;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.FreedomHeadlessApplicationUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GuiStoneTests {

    private static final String WHITE_STONE_IMAGE_NAME = "white_checker";
    private static HeadlessApplication application;

    @BeforeAll
    static void initHeadlessApplication() {
        application = FreedomHeadlessApplicationUtils.initHeadlessApplication("freedom.atlas");
    }

    @ParameterizedTest
    @MethodSource("board.providers.PieceProviders#provideStoneColorsWithExceptionsForInvalidColors")
    void testStoneColorValidity(Color stoneColor, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new GuiStone(stoneColor, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class)));
        } else {
            assertThrows(expectedException, () -> new GuiStone(stoneColor, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.PieceProviders#provideImagePathWithColorPairsAndWhetherResultingGuiStonesShouldBeEqual")
    void testEqualsByComparingImageColorPairWithCandidatePair(String imageName, Color stoneColor, String candidateImageName, Color candidateStoneColor, boolean shouldBeEqual) {
        GuiStone stone = new GuiStone(stoneColor, VisUI.getSkin().get(imageName, TextureRegion.class));
        GuiStone comparisonStoneCandidate = new GuiStone(candidateStoneColor, VisUI.getSkin().get(candidateImageName, TextureRegion.class));
        assertEquals(shouldBeEqual, stone.equals(comparisonStoneCandidate));
    }

    @Test
    void testEqualsWithNullOrSameStoneOrOtherTypeCandidates() {
        GuiStone stone = new GuiStone(Color.WHITE, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class));
        assertNotEquals(null, stone);
        assertEquals(stone, stone);
        assertNotEquals(new Object(), stone);
    }

    @ParameterizedTest
    @MethodSource("board.providers.PieceProviders#provideImagePathWithColorPairsAndWhetherResultingGuiStonesShouldBeEqual")
    void testHashValue(String ignoredImageName, Color ignoredStoneColor, String candidateImageName, Color candidateStoneColor, boolean ignoredShouldBeEqual) {
        GuiStone stone = new GuiStone(candidateStoneColor, VisUI.getSkin().get(candidateImageName, TextureRegion.class));
        assertEquals(Objects.hash(candidateStoneColor, VisUI.getSkin().get(candidateImageName, TextureRegion.class)), stone.hashCode());
    }

    @Test
    void testColorGetter() {
        Piece blackStone = new GuiStone(Color.BLACK, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class));
        assertEquals(Color.BLACK, blackStone.getPieceColor());
        assertNotEquals(Color.WHITE, blackStone.getPieceColor());
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
