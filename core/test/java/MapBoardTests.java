import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.board.Board;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MapBoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);

    @BeforeEach
    void initBoard() {
        board.clearBoard();
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard<>(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new MapBoard<>(numberOfRows, numberOfColumns));
        }
    }

    private void fillBoardWithWhiteStones() {
        for (Position position : board.getPositions()) {
            board.putPiece(new Stone(Color.WHITE), position);
        }
    }

    @Test
    void testClearBoardByRemovingAllTheStones() {
        fillBoardWithWhiteStones();
        board.getPositions().forEach(position -> assertTrue(board.isCellOccupied(position)));
        board.clearBoard();
        board.getPositions().forEach(position -> assertFalse(board.isCellOccupied(position)));
    }

    @Test
    void testHasBoardMoreThanOneFreeCell() {
        fillBoardWithWhiteStones();
        Assertions.assertEquals(board.getNumberOfFreeCells(),0);
        board.clearCell(Position.fromCoordinates(0, 0));
        Assertions.assertEquals(board.getNumberOfFreeCells(),1);
        board.clearCell(Position.fromCoordinates(0, 1));
        Assertions.assertEquals(board.getNumberOfFreeCells(),2);
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideAdjacentBoardPositions")
    void testGetAdjacentPositions(Position position, Set<Position> adjacentPositions, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> board.getAdjacentPositions(position));
        } else {
            assertEquals(adjacentPositions, board.getAdjacentPositions(position));
        }
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideBoardPositions")
    void testAreAdjacentCellsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            fillBoardWithWhiteStones();
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            board.clearCell(Position.fromCoordinates(row, column));
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            Assertions.assertFalse(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideBoardPositions")
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
            printedBoard.setCharAt((column + 1)* 3, 'W');
            printedBoard.setCharAt((column + 2) * 3, 'B');
            assertEquals(printedBoard.toString(), board.toString());
        } else {
            assertThrows(expectedException, () -> board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideEmptyPrintedBoards")
    void printEmptyBoard(int numberOfRows, int numberOfColumns, String printedBoard) {
        Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);
        assertEquals(printedBoard, board.toString());
    }
}
