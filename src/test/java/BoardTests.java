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
                Arguments.of(1, 1, null),
                Arguments.of(5, 3, null),
                Arguments.of(9, 8, InvalidPositionException.class)
        );
    }

    private static @NotNull Stream<Arguments> provideAdjacentBoardPositions() {
        return Stream.of(
                // corner positions
                Arguments.of(new Position(1, 1), Set.of(
                        new Position(2, 1),
                        new Position(1, 2),
                        new Position(2, 2)
                ), null),
                Arguments.of(new Position(8, 1), Set.of(
                        new Position(8, 2),
                        new Position(7, 1),
                        new Position(7, 2)
                ), null),
                Arguments.of(new Position(8, 8), Set.of(
                        new Position(8, 7),
                        new Position(7, 8),
                        new Position(7, 7)
                ), null),
                Arguments.of(new Position(1, 8), Set.of(
                        new Position(1, 7),
                        new Position(2, 8),
                        new Position(2, 7)
                ), null),
                // position on the board sides
                Arguments.of(new Position(3, 1), Set.of(
                        new Position(2, 1),
                        new Position(4, 1),
                        new Position(3, 2),
                        new Position(2, 2),
                        new Position(4, 2)
                ), null),
                Arguments.of(new Position(8, 2), Set.of(
                        new Position(8, 1),
                        new Position(8, 3),
                        new Position(7, 2),
                        new Position(7, 1),
                        new Position(7, 3)
                ), null),
                Arguments.of(new Position(3, 8), Set.of(
                        new Position(2, 8),
                        new Position(4, 8),
                        new Position(3, 7),
                        new Position(4, 7),
                        new Position(2, 7)
                ), null),
                Arguments.of(new Position(1, 4), Set.of(
                        new Position(1, 3),
                        new Position(1, 5),
                        new Position(2, 4),
                        new Position(2, 3),
                        new Position(2, 5)
                ), null),
                // position in the board center
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
}
