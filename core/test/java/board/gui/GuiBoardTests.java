package board.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class GuiBoardTests {

    private static HeadlessApplication application;

    private static final int numberOfRows = 8;
    private static final int numberOfColumns = 8;
    private final GuiBoard<GuiStone> board = new GuiBoard<>(numberOfRows, numberOfColumns);

    @BeforeAll
    static void initApplication() {
        application = initHeadlessApplication("freedom.atlas");
    }

    @BeforeEach
    void clearBoard() {
        board.clearBoard();
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

    @ParameterizedTest
    @MethodSource("board.gui.providers.GuiBoardProviders#provideTileCoordinatesAndExpectedPositionFor8x8Board")
    void testFromTileCoordinatesToPositionConversion(int tileRow, int tileColumn, Position expectedPosition) {
        assertEquals(expectedPosition, board.fromTileCoordinatesToBoardPosition(tileRow, tileColumn));
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
