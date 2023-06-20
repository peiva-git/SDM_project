package board;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.gui.GuiStone;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.FreedomHeadlessApplicationUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuiStoneTests {

    private static final String WHITE_STONE_IMAGE_NAME = "white_checker";
    private static HeadlessApplication application;

    @BeforeAll
    static void initHeadlessApplication() {
        application = FreedomHeadlessApplicationUtils.initHeadlessApplication("freedom.atlas");
    }

    @ParameterizedTest
    @MethodSource("board.providers.StoneProviders#provideStoneColorsWithExceptionsForInvalidColors")
    void testStoneColorValidity(Color stoneColor, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new GuiStone(stoneColor, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class)));
        } else {
            assertThrows(expectedException, () -> new GuiStone(stoneColor, VisUI.getSkin().get(WHITE_STONE_IMAGE_NAME, TextureRegion.class)));
        }
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
