import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTests {
    private static @NotNull Stream<Arguments> providePositionPairsForArePositionsAdjacent() {
        return Stream.of(
                Arguments.of(new Position(3, 3), new Position(3, 3), false),
                Arguments.of(new Position(3, 3), new Position(3, 4), true),
                Arguments.of(new Position(3, 3), new Position(3, 2), true),
                Arguments.of(new Position(3, 3), new Position(2, 3), true),
                Arguments.of(new Position(3, 3), new Position(4, 3), true),
                Arguments.of(new Position(3, 3), new Position(3, 1), false),
                Arguments.of(new Position(3, 3), new Position(3, 5), false),
                Arguments.of(new Position(3, 3), new Position(1, 3), false),
                Arguments.of(new Position(3, 3), new Position(5, 3), false)
        );
    }

    private static @NotNull Stream<Arguments> providePositionCoordinates() {
        return Stream.of(
                Arguments.of(0, 0, InvalidPositionException.class),
                Arguments.of(-1, -1, InvalidPositionException.class),
                Arguments.of(5, 8, null)
        );
    }

    private static @NotNull Stream<Arguments> providePositionPairsForComparison() {
        return Stream.of(
                Arguments.of(new Position(1, 1), new Position(1, 1), 0),
                Arguments.of(new Position(2, 1), new Position(3, 1), 1),
                Arguments.of(new Position(1, 1), new Position(1, 2), 1),
                Arguments.of(new Position(3, 1), new Position(2, 2), 1),
                Arguments.of(new Position(3, 1), new Position(2, 5), 1),
                Arguments.of(new Position(1, 10), new Position(1, 9), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionCoordinates")
    void testNewPosition(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Position position = assertDoesNotThrow(() -> new Position(row, column));
            assertEquals(row, position.getRow());
            assertEquals(column, position.getColumn());
        } else {
            assertThrows(InvalidPositionException.class, () -> new Position(row, column));
        }
    }

    @ParameterizedTest
    @MethodSource("providePositionPairsForArePositionsAdjacent")
    void arePositionsAdjacent(@NotNull Position firstPosition,@NotNull Position secondPosition, boolean expectedAdjacent) {
        assertEquals(firstPosition.isPositionAdjacentTo(secondPosition), expectedAdjacent);
    }

    @ParameterizedTest
    @MethodSource("providePositionPairsForComparison")
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
}
