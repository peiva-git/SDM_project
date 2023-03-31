import it.units.sdm.project.core.MapBoard;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.Position;
import it.units.sdm.project.Stone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MapBoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final MapBoard<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);

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
                // corner positions
                Arguments.of(Position.fromCoordinates(1, 1), Set.of(
                        Position.fromCoordinates(2, 1),
                        Position.fromCoordinates(1, 2),
                        Position.fromCoordinates(2, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 1), Set.of(
                        Position.fromCoordinates(8, 2),
                        Position.fromCoordinates(7, 1),
                        Position.fromCoordinates(7, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 8), Set.of(
                        Position.fromCoordinates(8, 7),
                        Position.fromCoordinates(7, 8),
                        Position.fromCoordinates(7, 7)
                ), null),
                Arguments.of(Position.fromCoordinates(1, 8), Set.of(
                        Position.fromCoordinates(1, 7),
                        Position.fromCoordinates(2, 8),
                        Position.fromCoordinates(2, 7)
                ), null),
                // position on the board sides
                Arguments.of(Position.fromCoordinates(3, 1), Set.of(
                        Position.fromCoordinates(2, 1),
                        Position.fromCoordinates(4, 1),
                        Position.fromCoordinates(3, 2),
                        Position.fromCoordinates(2, 2),
                        Position.fromCoordinates(4, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 2), Set.of(
                        Position.fromCoordinates(8, 1),
                        Position.fromCoordinates(8, 3),
                        Position.fromCoordinates(7, 2),
                        Position.fromCoordinates(7, 1),
                        Position.fromCoordinates(7, 3)
                ), null),
                Arguments.of(Position.fromCoordinates(3, 8), Set.of(
                        Position.fromCoordinates(2, 8),
                        Position.fromCoordinates(4, 8),
                        Position.fromCoordinates(3, 7),
                        Position.fromCoordinates(4, 7),
                        Position.fromCoordinates(2, 7)
                ), null),
                Arguments.of(Position.fromCoordinates(1, 4), Set.of(
                        Position.fromCoordinates(1, 3),
                        Position.fromCoordinates(1, 5),
                        Position.fromCoordinates(2, 4),
                        Position.fromCoordinates(2, 3),
                        Position.fromCoordinates(2, 5)
                ), null),
                // position in the board center
                Arguments.of(Position.fromCoordinates(5, 3), Set.of(
                        Position.fromCoordinates(5, 2),
                        Position.fromCoordinates(5, 4),
                        Position.fromCoordinates(4, 3),
                        Position.fromCoordinates(6, 3),
                        Position.fromCoordinates(6, 4),
                        Position.fromCoordinates(6, 2),
                        Position.fromCoordinates(4, 2),
                        Position.fromCoordinates(4, 4)
                ), null),
                Arguments.of(Position.fromCoordinates(9, 8), null, InvalidPositionException.class)
        );
    }

    private static @NotNull Stream<Arguments> provideEmptyPrintedBoards() {
        return Stream.of(
                Arguments.of(8, 8,
                        " 8 -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  -  -  -  -\n"
                                + " 5 -  -  -  -  -  -  -  -\n"
                                + " 4 -  -  -  -  -  -  -  -\n"
                                + " 3 -  -  -  -  -  -  -  -\n"
                                + " 2 -  -  -  -  -  -  -  -\n"
                                + " 1 -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H"),
                Arguments.of(10, 10,
                        "10 -  -  -  -  -  -  -  -  -  -\n"
                                + " 9 -  -  -  -  -  -  -  -  -  -\n"
                                + " 8 -  -  -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  -  -  -  -  -  -\n"
                                + " 5 -  -  -  -  -  -  -  -  -  -\n"
                                + " 4 -  -  -  -  -  -  -  -  -  -\n"
                                + " 3 -  -  -  -  -  -  -  -  -  -\n"
                                + " 2 -  -  -  -  -  -  -  -  -  -\n"
                                + " 1 -  -  -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H  I  J")
        );
    }

    @ParameterizedTest
    @MethodSource("provideBoardSizes")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> new MapBoard<>(numberOfRows, numberOfColumns));
        } else {
            assertDoesNotThrow(() -> new MapBoard<>(numberOfRows, numberOfColumns));
        }
    }

    private void fillBoardWithWhiteStones() {
        for (Position position : board) {
            board.putPiece(new Stone(Stone.Color.WHITE), position);
        }
    }

    @Test
    void testClearBoardByRemovingAllTheStones() {
        fillBoardWithWhiteStones();
        for (Position position : board) {
            assertTrue(board.isCellOccupied(position));
        }
        board.clearBoard();
        for (Position position : board) {
            assertFalse(board.isCellOccupied(position));
        }
    }

    @Test
    void testHasBoardMoreThanOneFreeCell() {
        fillBoardWithWhiteStones();
        Assertions.assertFalse(board.hasBoardMoreThanOneFreeCell());
        board.clearCell(Position.fromCoordinates(1, 1));
        Assertions.assertFalse(board.hasBoardMoreThanOneFreeCell());
        board.clearCell(Position.fromCoordinates(1, 2));
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
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            board.clearCell(Position.fromCoordinates(row, column));
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            Assertions.assertFalse(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideBoardPositions")
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
            board.putPiece(new Stone(Stone.Color.WHITE), Position.fromCoordinates(row, column));
            board.putPiece(new Stone(Stone.Color.BLACK), Position.fromCoordinates(row, column + 1));
            StringBuilder printedBoard = new StringBuilder(printedEmptyBoard);
            printedBoard.setCharAt(column * 3, 'W');
            printedBoard.setCharAt((column + 1) * 3, 'B');
            assertEquals(printedBoard.toString(), board.toString());
        } else {
            assertThrows(expectedException, () -> board.putPiece(new Stone(Stone.Color.WHITE), Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideEmptyPrintedBoards")
    void printEmptyBoard(int numberOfRows, int numberOfColumns, String printedBoard) {
        MapBoard<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);
        assertEquals(printedBoard, board.toString());
    }
}
