import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
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

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testGetStoneMethodOnFreePosition(int row, int column, Class<Exception> expectedException) {
        Position position = new Position(row, column);
        if (expectedException == null) {
            assertDoesNotThrow(() -> board.getStone(position));
        } else {
            assertThrows(InvalidPositionException.class, () -> board.getStone(position));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testPutStoneMethodOnAFreePosition(int row, int column, Class<Exception> expectedException) {
        Stone blackStone = new Stone(Stone.Color.BLACK);
        Position position = new Position(row, column);
        if (expectedException == null) {
            assertDoesNotThrow(() -> board.putStone(blackStone, position));
            assertEquals(blackStone, board.getStone(position));
        } else {
            assertThrows(InvalidPositionException.class, () -> board.putStone(blackStone, position));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
    void testPutStoneMethodOnAnOccupiedPosition(int row, int column, Class<Exception> expectedException) {
        testPutStoneMethodOnAFreePosition(row, column, expectedException);
        Stone blackStone = new Stone(Stone.Color.BLACK);
        Position position = new Position(row, column);
        assertThrows(InvalidPositionException.class, () -> board.putStone(blackStone, position));
    }

    @Test
    void testClearBoardByRemovingAllTheStones() {
        fillTheEntireBoardWithWhiteStones(board);
        for (Map.Entry<Position, Cell> cellWithPosition : board) {
            assertTrue(cellWithPosition.getValue().isOccupied());
        }
        board.clearBoard();
        for (Map.Entry<Position, Cell> cellWithPosition : board) {
            assertFalse(cellWithPosition.getValue().isOccupied());
        }
    }

    private void fillTheEntireBoardWithWhiteStones(@NotNull Board board) {
        for (Map.Entry<Position, Cell> cellWithPosition : board) {
            cellWithPosition.getValue().putStone(new Stone(Stone.Color.WHITE));
        }
    }

}
