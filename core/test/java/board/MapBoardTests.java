package board;

import it.units.sdm.project.board.*;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static board.providers.BoardProviders.fillBoardWithWhiteStones;

class MapBoardTests {

    private final int numberOfRowsAndColumns = 8;
    private final Board<Piece> board = new MapBoard<>(numberOfRowsAndColumns);

    @BeforeEach
    void clearBoard() {
        board.clearBoard();
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provideBoardSizesWithExceptionsForInvalidBoardSizes")
    void testBoardSizeValidity(int boardSize, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard<>(boardSize));
        } else {
            assertDoesNotThrow(() -> new MapBoard<>(boardSize));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionsForInvalidPositions")
    void putPieceThenGetPieceAndCheckIfEquals(int row, int column, Class<Exception> expectedException) {
        Piece expectedStone = new Stone(Color.WHITE);
        if (expectedException == null) {
            board.putPiece(expectedStone, Position.fromCoordinates(row, column));
            assertEquals(expectedStone, board.getPiece(Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> board.putPiece(expectedStone, Position.fromCoordinates(row, column)));
            assertThrows(expectedException, () -> board.getPiece(Position.fromCoordinates(row, column)));
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
    @MethodSource("board.providers.BoardProviders#provideBoardSizeForEmptyBoardStringRepresentation")
    void testPrintingOfEmptyBoard(int boardSize, String printedBoard) {
        Board<Piece> board = new MapBoard<>(boardSize);
        assertEquals(printedBoard, board.toString());
    }
}
