package board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.board.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTests {

    @ParameterizedTest
    @MethodSource("board.providers.PositionProviders#providePositionCoordinates")
    void testInstancingNewPositionFromCoordinates(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Position position = assertDoesNotThrow(() -> Position.fromCoordinates(row, column));
            assertEquals(row, position.getRow());
            assertEquals(column, position.getColumn());
        } else {
            assertThrows(InvalidPositionException.class, () -> Position.fromCoordinates(row, column));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.PositionProviders#providePositionPairsForComparison")
    void testPositionOrdering(Position first, Position second, int expectedResult) {
        if (expectedResult == 1) {
            assertTrue(first.compareTo(second) > 0);
            assertNotEquals(first, second);
        } else if (expectedResult == -1) {
            assertTrue(first.compareTo(second) < 0);
            assertNotEquals(first, second);
        } else {
            assertEquals(0, first.compareTo(second));
            assertEquals(first, second);
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.PositionProviders#providePositionStringRepresentations")
    void testPositionStringRepresentation(int row, int column, String positionRepresentation) {
        assertEquals(Position.fromCoordinates(row, column).toString(), positionRepresentation);
    }
}
