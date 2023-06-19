package board;

import it.units.sdm.project.board.*;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import utility.BoardUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class MapBoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board board = new MapBoard(numberOfRows, numberOfColumns);

    @BeforeEach
    void initBoard() {
        board.clearBoard();
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#provideBoardSizesWithExceptions")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new MapBoard(numberOfRows, numberOfColumns));
        }
    }

    @Test
    void testClearBoardByRemovingAllTheStones() {
        BoardUtils.fillBoardWithWhiteStones(board);
        board.getPositions().forEach(position -> assertTrue(board.isCellOccupied(position)));
        board.clearBoard();
        board.getPositions().forEach(position -> assertFalse(board.isCellOccupied(position)));
    }

    @Test
    void testHasBoardMoreThanOneFreeCell() {
        BoardUtils.fillBoardWithWhiteStones(board);
        Assertions.assertEquals(board.getNumberOfFreeCells(), 0);
        board.clearCell(Position.fromCoordinates(0, 0));
        Assertions.assertEquals(board.getNumberOfFreeCells(), 1);
        board.clearCell(Position.fromCoordinates(0, 1));
        Assertions.assertEquals(board.getNumberOfFreeCells(), 2);
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#providePositionsFor8x8BoardWithExceptions")
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
            board.putPiece(new Piece(Color.WHITE), Position.fromCoordinates(row, column));
            board.putPiece(new Piece(Color.BLACK), Position.fromCoordinates(row, column + 1));
            StringBuilder printedBoard = new StringBuilder(printedEmptyBoard);
            printedBoard.setCharAt((column + 1) * 3, 'W');
            printedBoard.setCharAt((column + 2) * 3, 'B');
            assertEquals(printedBoard.toString(), board.toString());
        } else {
            assertThrows(expectedException, () -> board.putPiece(new Piece(Color.WHITE), Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#provideEmptyBoardStringRepresentations")
    void printEmptyBoard(int numberOfRows, int numberOfColumns, String printedBoard) {
        Board board = new MapBoard(numberOfRows, numberOfColumns);
        assertEquals(printedBoard, board.toString());
    }
}
