package board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class PositionTests {

    @ParameterizedTest
    @MethodSource("board.providers.PositionProviders#providePositionCoordinatesWithExceptionsForInvalidCoordinate")
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
    @MethodSource("board.providers.PositionProviders#providePositionPairsForOrderingComparisonWithExpectedOutcome")
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

    @ParameterizedTest
    @MethodSource("board.providers.PositionProviders#providePositionWithObjectAndWhetherEqual")
    void testEqualsByComparingPositionWithCandidateObject(@NotNull Position position, Object candidate, boolean shouldBeEqual) {
        assertEquals(shouldBeEqual, position.equals(candidate));
    }
}
