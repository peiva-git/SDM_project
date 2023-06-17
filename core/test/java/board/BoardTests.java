package board;

import it.units.sdm.project.board.*;
import utility.BoardUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#provideAdjacentBoardPositions")
    void testGetAdjacentPositions(Position position, Set<Position> adjacentPositions, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> board.getAdjacentPositions(position));
        } else {
            assertEquals(adjacentPositions, board.getAdjacentPositions(position));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#provideBoardPositions")
    void testAreAdjacentCellsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            BoardUtils.fillBoardWithWhiteStones(board);
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            board.clearCell(Position.fromCoordinates(row, column));
            Assertions.assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            Assertions.assertFalse(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
        }
    }

}
