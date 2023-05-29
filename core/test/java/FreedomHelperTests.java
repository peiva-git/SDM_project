import it.units.sdm.project.board.*;
import utility.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FreedomHelperTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideAdjacentBoardPositions")
    void testGetAdjacentPositions(Position position, Set<Position> adjacentPositions, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> FreedomBoardHelper.getAdjacentPositions(board, position));
        } else {
            assertEquals(adjacentPositions, FreedomBoardHelper.getAdjacentPositions(board, position));
        }
    }

    @ParameterizedTest
    @MethodSource("providers.MapBoardProviders#provideBoardPositions")
    void testAreAdjacentCellsOccupied(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            TestUtilities.fillBoardWithWhiteStones(board);
            Assertions.assertTrue(FreedomBoardHelper.areAdjacentCellsOccupied(board, Position.fromCoordinates(row, column)));
            board.clearCell(Position.fromCoordinates(row, column));
            Assertions.assertTrue(FreedomBoardHelper.areAdjacentCellsOccupied(board, Position.fromCoordinates(row, column)));
            Assertions.assertFalse(FreedomBoardHelper.areAdjacentCellsOccupied(board, Position.fromCoordinates(row, column + 1)));
        } else {
            assertThrows(expectedException, () -> FreedomBoardHelper.areAdjacentCellsOccupied(board, Position.fromCoordinates(row, column)));
        }
    }

}
