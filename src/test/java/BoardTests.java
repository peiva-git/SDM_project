import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board board = new Board(numberOfRows, numberOfColumns);

    @BeforeEach
    void initBoard() {
        board.clearBoard();
    }

    private static @NotNull Stream<Arguments> provideBoardSizes() {
        return Stream.of(
                Arguments.of(0, 1, InvalidBoardSizeException.class),
                Arguments.of(1, 0, InvalidBoardSizeException.class),
                Arguments.of(0, 0, InvalidBoardSizeException.class),
                Arguments.of(-1, -1, InvalidBoardSizeException.class),
                Arguments.of(2, 3, InvalidBoardSizeException.class),
                Arguments.of(1, 1, null)
        );
    }

    private static @NotNull Stream<Arguments> provideBoardPositions() {
        return Stream.of(
                Arguments.of(8, 1, null),
                Arguments.of(8, 2, null),
                Arguments.of(9, 8, InvalidPositionException.class)
        );
    }

    private static @NotNull Stream<Arguments> provideAdjacentBoardPositions() {
        return Stream.of(
                Arguments.of(new Position(1, 1), Set.of(
                        new Position(2, 1),
                        new Position(1, 2),
                        new Position(2, 2)
                ), null),
                Arguments.of(new Position(3, 1), Set.of(
                        new Position(2, 1),
                        new Position(4, 1),
                        new Position(3, 2),
                        new Position(2, 2),
                        new Position(4, 2)
                ), null),
                Arguments.of(new Position(5, 3), Set.of(
                        new Position(5, 2),
                        new Position(5, 4),
                        new Position(4, 3),
                        new Position(6, 3),
                        new Position(6, 4),
                        new Position(6, 2),
                        new Position(4, 2),
                        new Position(4, 4)
                ), null),
                Arguments.of(new Position(9, 8), null, InvalidPositionException.class)
        );
    }

    private static @NotNull Stream<Arguments> provideEmptyPrintedBoards() {
        return Stream.of(
                Arguments.of(8, 8,
                         " 8 -  -  -  -  -  -  -  -\n"
                        +" 7 -  -  -  -  -  -  -  -\n"
                        +" 6 -  -  -  -  -  -  -  -\n"
                        +" 5 -  -  -  -  -  -  -  -\n"
                        +" 4 -  -  -  -  -  -  -  -\n"
                        +" 3 -  -  -  -  -  -  -  -\n"
                        +" 2 -  -  -  -  -  -  -  -\n"
                        +" 1 -  -  -  -  -  -  -  -\n"
                        +"   A  B  C  D  E  F  G  H"),
                Arguments.of(10, 10,
                         "10 -  -  -  -  -  -  -  -  -  -\n"
                        +" 9 -  -  -  -  -  -  -  -  -  -\n"
                        +" 8 -  -  -  -  -  -  -  -  -  -\n"
                        +" 7 -  -  -  -  -  -  -  -  -  -\n"
                        +" 6 -  -  -  -  -  -  -  -  -  -\n"
                        +" 5 -  -  -  -  -  -  -  -  -  -\n"
                        +" 4 -  -  -  -  -  -  -  -  -  -\n"
                        +" 3 -  -  -  -  -  -  -  -  -  -\n"
                        +" 2 -  -  -  -  -  -  -  -  -  -\n"
                        +" 1 -  -  -  -  -  -  -  -  -  -\n"
                        +"   A  B  C  D  E  F  G  H  I  J")
        );
    }

    @ParameterizedTest
    @MethodSource("provideBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new Board(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new Board(numberOfRows, numberOfColumns));
        }
    }

    private void fillBoardWithWhiteStones() {
        for (Map.Entry<Position, Cell> boardCell : board) {
            boardCell.getValue().putStone(new Stone(Stone.Color.WHITE));
        }
    }

    @Test
    void testClearBoardByRemovingAllTheStones() {
        fillBoardWithWhiteStones();
        for (Map.Entry<Position, Cell> cellWithPosition : board) {
            assertTrue(cellWithPosition.getValue().isOccupied());
        }
        board.clearBoard();
        for (Map.Entry<Position, Cell> cellWithPosition : board) {
            assertFalse(cellWithPosition.getValue().isOccupied());
        }
    }

    @Test
    void testHasBoardMoreThanOneFreeCell() {
        fillBoardWithWhiteStones();
        Assertions.assertFalse(board.hasBoardMoreThanOneFreeCell());
        board.clearCell(new Position(1,1));
        Assertions.assertFalse(board.hasBoardMoreThanOneFreeCell());
        board.clearCell(new Position(1,2));
        Assertions.assertTrue(board.hasBoardMoreThanOneFreeCell());
    }

    @ParameterizedTest
    @MethodSource("provideAdjacentBoardPositions")
    void testGetAdjacentPositions(Position position, Set<Position> adjacentPositions, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> board.getAdjacentPositions(position));
        } else {
            assertEquals(adjacentPositions, board.getAdjacentPositions(position));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testAreAdjacentCellsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            fillBoardWithWhiteStones();
            Assertions.assertTrue(board.areAdjacentCellsOccupied(new Position(row,column)));
            board.clearCell(new Position(row,column));
            Assertions.assertTrue(board.areAdjacentCellsOccupied(new Position(row,column)));
            Assertions.assertFalse(board.areAdjacentCellsOccupied(new Position(row,column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(new Position(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void printSizeEightBoardWithStones(int row, int column, Class<Exception> expectedException) {
        String expectedEmptyBoard =
                 " 8 -  -  -  -  -  -  -  -\n"
                +" 7 -  -  -  -  -  -  -  -\n"
                +" 6 -  -  -  -  -  -  -  -\n"
                +" 5 -  -  -  -  -  -  -  -\n"
                +" 4 -  -  -  -  -  -  -  -\n"
                +" 3 -  -  -  -  -  -  -  -\n"
                +" 2 -  -  -  -  -  -  -  -\n"
                +" 1 -  -  -  -  -  -  -  -\n"
                +"   A  B  C  D  E  F  G  H";
        assertEquals(expectedEmptyBoard, board.toString());
        if (expectedException == null) {
            board.putStone(new Position(row, column), Stone.Color.WHITE);
            board.putStone(new Position(row, column + 1), Stone.Color.BLACK);
            StringBuilder expectedBoard = new StringBuilder(expectedEmptyBoard);
            expectedBoard.setCharAt(column * 3, 'W');
            expectedBoard.setCharAt((column + 1) * 3, 'B');
            assertEquals(expectedBoard.toString(), board.toString());
        } else {
            assertThrows(expectedException, () -> board.putStone(new Position(row, column), Stone.Color.WHITE));
        }
    }

    @ParameterizedTest
    @MethodSource("provideEmptyPrintedBoards")
    void printEmptyBoard(int numberOfRows, int numberOfColumns, String expectedResult) {
        Board board = new Board(numberOfRows, numberOfColumns);
        assertEquals(expectedResult, board.toString());
    }
}
