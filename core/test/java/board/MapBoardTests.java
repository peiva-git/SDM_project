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
    @MethodSource("board.providers.BoardProviders#provideBoardSizesWithExceptionsForInvalidMapBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard<>(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new MapBoard<>(numberOfRows, numberOfColumns));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionForInvalidPositions")
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
    @MethodSource("board.providers.BoardProviders#providePositionsFor8x8BoardWithExceptionForInvalidPositions")
    void printSizeEightBoardWithStones(int row, int column, Class<Exception> expectedException) {
        String printedEmptyBoard =
                " 8 -  -  -  -  -  -  -  -\n"
                        + " 7 -  -  -  -  -  -  -  -\n"
                        + " 6 -  -  -  -  -  -  -  -\n"
                        + " 5 -  -  -  -  -  -  -  -\n"
                        + " 4 -  -  -  -  -  -  -  -\n"
                        + " 3 -  -  -  -  -  -  -  -\n"
                        + " 2 -  -  -  -  -  -  -  -\n"
                        + " 1 -  -  -  -  -  -  -  -\n"
                        + "   A  B  C  D  E  F  G  H";
        assertEquals(printedEmptyBoard, board.toString());
        if (expectedException == null) {
            board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(row, column));
            board.putPiece(new Stone(Color.BLACK), Position.fromCoordinates(row, column + 1));
            StringBuilder printedBoard = new StringBuilder(printedEmptyBoard);
            printedBoard.setCharAt((column + 1) * 3, 'W');
            printedBoard.setCharAt((column + 2) * 3, 'B');
            assertEquals(printedBoard.toString(), board.toString());
        } else {
            assertThrows(expectedException, () -> board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.BoardProviders#provideNumberOfRowsAndNumberOfColumnsForEmptyBoardStringRepresentation")
    void printEmptyBoard(int numberOfRows, int numberOfColumns, String printedBoard) {
        Board<Piece> board = new MapBoard<>(numberOfRows, numberOfColumns);
        assertEquals(printedBoard, board.toString());
    }
}
