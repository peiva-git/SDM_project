import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @ParameterizedTest
    @MethodSource("provideWrongBoardInSize")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new Board(numberOfRows, numberOfColumns));
        } else {
            Assertions.assertDoesNotThrow(() -> new Board(numberOfRows, numberOfColumns));
        }
    }

    private static @NotNull Stream<Arguments> provideWrongBoardInSize() {
        return Stream.of(
                Arguments.of(11, 11, InvalidBoardSizeException.class),
                Arguments.of(8, 3, InvalidBoardSizeException.class),
                Arguments.of(7, 7, InvalidBoardSizeException.class),
                Arguments.of(9, 9, InvalidBoardSizeException.class),
                Arguments.of(10, 10, null),
                Arguments.of(8, 8, null)
        );
    }

    @Test
    void testGetCellMethodOnAFreePosition() {
        Board board = new Board(8, 8);
        Position position = new Position(4, 5);
        assertDoesNotThrow(() -> board.getCell(position));
    }

    @Test
    void testPutStoneMethodOnAFreePosition() {
        Board board = new Board(8, 8);
        Stone stone = new Stone(Color.BLACK);
        Position position = new Position(2, 3);
        board.putStone(stone, position);
        assertEquals(stone, board.getStone(position));
    }

    @Test
    void testPutStoneMethodOnAWrongPosition() {
        Board board = new Board(8, 8);
        Stone stone = new Stone(Color.BLACK);
        Position position = new Position(13, 3);
        assertThrows(InvalidPositionException.class, () -> board.putStone(stone, position));
    }

    @Test
    void testPutStoneMethodOnAnOccupiedPosition() {
        Board board = new Board(8, 8);
        Stone stone = new Stone(Color.BLACK);
        Position position = new Position(2, 3);
        board.putStone(stone, position);
        assertThrows(InvalidPositionException.class, () -> board.putStone(stone, position));
    }
    @Test
    void testClearBoardByRemovingAllTheStones() {
        Board board = new Board(8, 8);
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
            cellWithPosition.getValue().putStone(new Stone(Color.WHITE));
        }
    }

}
