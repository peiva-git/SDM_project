import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private Board board;
    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;

    @BeforeEach
    void initBoard() {
        board = new Board(numberOfRows, numberOfColumns);
    }

    @AfterEach
    void clearBoard() {
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

    @ParameterizedTest
    @MethodSource("provideBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new Board(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new Board(numberOfRows, numberOfColumns));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testGetCellMethodOnAFreePosition(int row, int column, Class<Exception> expectedException) {
        Position position = new Position(row, column);
        if (expectedException == null) {
            assertDoesNotThrow(() -> board.getCell(position));
        } else {
            assertThrows(InvalidPositionException.class, () -> board.getCell(position));
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
        board.getCell(new Position(1,1)).clear();
        Assertions.assertFalse(board.hasBoardMoreThanOneFreeCell());
        board.getCell(new Position(1,2)).clear();
        Assertions.assertTrue(board.hasBoardMoreThanOneFreeCell());
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testAreAdjacentCellsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            fillBoardWithWhiteStones();
            Assertions.assertTrue(board.areAdjacentCellsOccupied(new Position(row,column)));
            board.getCell(new Position(row,column)).clear();
            Assertions.assertTrue(board.areAdjacentCellsOccupied(new Position(row,column)));
            Assertions.assertFalse(board.areAdjacentCellsOccupied(new Position(row,column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(new Position(row, column)));
        }
    }
}
