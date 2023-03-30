import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTests {

    private static @NotNull Stream<Arguments> providePositionCoordinates() {
        return Stream.of(
                Arguments.of(0, 0, InvalidPositionException.class),
                Arguments.of(-1, -1, InvalidPositionException.class),
                Arguments.of(5, 8, null)
        );
    }

    private static @NotNull Stream<Arguments> providePositionPairsForComparison() {
        return Stream.of(
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 1), 0),
                Arguments.of(Position.fromCoordinates(2, 1), Position.fromCoordinates(3, 1), -1),
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 2), -1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 2), 1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 5), 1),
                Arguments.of(Position.fromCoordinates(1, 10), Position.fromCoordinates(1, 9), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionCoordinates")
    void testNewPosition(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Position position = assertDoesNotThrow(() -> Position.fromCoordinates(row, column));
            assertEquals(row, position.getRow());
            assertEquals(column, position.getColumn());
        } else {
            assertThrows(InvalidPositionException.class, () -> Position.fromCoordinates(row, column));
        }
    }

    @ParameterizedTest
    @MethodSource("providePositionCoordinates")
    void testValueOf(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Position position = assertDoesNotThrow(() -> Position.fromCoordinates(row, column));
            assertEquals(row, position.getRow());
            assertEquals(column, position.getColumn());
        } else {
            assertThrows(InvalidPositionException.class, () -> Position.fromCoordinates(row, column));
        }
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
