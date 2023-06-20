package board.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.gui.GuiBoard;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class GuiBoardTests {

    private static HeadlessApplication application;

    @BeforeAll
    static void initApplication() {
        application = initHeadlessApplication("freedom.atlas");
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provideBoardSizesWithExceptionsForInvalidBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new GuiBoard<>(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new GuiBoard<>(numberOfRows, numberOfColumns));
        }
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
