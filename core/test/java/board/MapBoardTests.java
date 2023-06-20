package board;

import it.units.sdm.project.board.*;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static utility.BoardUtils.fillBoardWithWhiteStones;

class MapBoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board<Piece> board = new MapBoard<>(numberOfRows, numberOfColumns);

    @BeforeEach
    void clearBoard() {
        board.clearBoard();
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provideBoardSizesWithExceptionsForInvalidBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard<>(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new MapBoard<>(numberOfRows, numberOfColumns));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionsForInvalidPositions")
    void testPutPieceByCheckingWhetherTheCellsGetsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(row, column));
            assertTrue(board.isCellOccupied(Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionsForInvalidPositions")
    void testClearCellByFillingBoardAndThenClearingOneCell(int row, int column, Class<Exception> expectedException) {
        fillBoardWithWhiteStones(board);
        if (expectedException == null) {
            board.clearCell(Position.fromCoordinates(row, column));
            assertFalse(board.isCellOccupied(Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> board.clearCell(Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provide8x8NotEmptyBoardStringRepresentationWithPositionsToOccupyWithPieces")
    void testPrintingOfBoardWithAWhiteAndABlackPiece(String printedBoard, Position whitePiecePosition, Position blackPiecePosition) {
        board.putPiece(new Stone(Color.WHITE), whitePiecePosition);
        board.putPiece(new Stone(Color.BLACK), blackPiecePosition);
        assertEquals(printedBoard, board.toString());
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provideNumberOfRowsAndNumberOfColumnsForEmptyBoardStringRepresentation")
    void testPrintingOfEmptyBoard(int numberOfRows, int numberOfColumns, String printedBoard) {
        Board<Piece> board = new MapBoard<>(numberOfRows, numberOfColumns);
        assertEquals(printedBoard, board.toString());
    }
}
