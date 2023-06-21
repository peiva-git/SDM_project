package board.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Color;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

import static board.gui.providers.GuiBoardProviders.fillBoardWithWhiteGuiStones;
import static org.junit.jupiter.api.Assertions.*;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class GuiBoardTests {

    private static HeadlessApplication application;

    private static final int boardSize = 8;
    private final GuiBoard<GuiStone> board = new GuiBoard<>(boardSize);

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
    void testBoardSizeValidity(int boardSize, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new GuiBoard<>(boardSize));
        } else {
            assertDoesNotThrow(() -> new GuiBoard<>(boardSize));
        }
    }

    @ParameterizedTest
    @MethodSource("board.gui.providers.GuiBoardProviders#provideTileCoordinatesAndExpectedPositionFor8x8Board")
    void testFromTileCoordinatesToPositionConversion(int tileRow, int tileColumn, Position expectedPosition) {
        assertEquals(expectedPosition, board.fromTileCoordinatesToBoardPosition(tileRow, tileColumn));
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionsForInvalidPositions")
    void testClearCellByFillingBoardAndThenClearingOneCell(int row, int column, Class<Exception> expectedException) {
        fillBoardWithWhiteGuiStones(board, VisUI.getSkin().getRegion("white_checker"));
        if (expectedException == null) {
            board.clearCell(Position.fromCoordinates(row, column));
            assertFalse(board.isCellOccupied(Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> board.clearCell(Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionsForInvalidPositions")
    void putPieceThenGetPieceAndCheckIfEquals(int row, int column, Class<Exception> expectedException) {
        GuiStone expectedStone = new GuiStone(Color.WHITE, VisUI.getSkin().getRegion("white_checker"));
        if (expectedException == null) {
            board.putPiece(expectedStone, Position.fromCoordinates(row, column));
            assertEquals(expectedStone, board.getPiece(Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> board.putPiece(expectedStone, Position.fromCoordinates(row, column)));
            assertThrows(expectedException, () -> board.getPiece(Position.fromCoordinates(row, column)));
        }
    }

    @Test
    void testGetPositionsByCheckingPositionCountAndMinAndMaxPositions() {
        Optional<Position> optionalMax = board.getPositions().stream().max(Position::compareTo);
        assertTrue(optionalMax.isPresent());
        assertEquals(Position.fromCoordinates(boardSize - 1, boardSize - 1), optionalMax.get());
        Optional<Position> optionalMin = board.getPositions().stream().min(Position::compareTo);
        assertTrue(optionalMin.isPresent());
        assertEquals(Position.fromCoordinates(0, 0), optionalMin.get());
        assertEquals(boardSize * boardSize, board.getPositions().size());
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
