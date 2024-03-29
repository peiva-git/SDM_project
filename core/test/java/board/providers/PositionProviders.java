package board.providers;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PositionProviders {
    public static @NotNull Stream<Arguments> providePositionCoordinatesWithExceptionsForInvalidCoordinate() {
        return Stream.of(
                Arguments.of(0, 0, null),
                Arguments.of(-1, -1, InvalidPositionException.class),
                Arguments.of(5, 8, null),
                Arguments.of(5, 26, InvalidPositionException.class),
                Arguments.of(Integer.MAX_VALUE, 25, null)
        );
    }

    public static @NotNull Stream<Arguments> providePositionPairsForOrderingComparisonWithExpectedOutcome() {
        return Stream.of(
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 1), 0),
                Arguments.of(Position.fromCoordinates(2, 1), Position.fromCoordinates(3, 1), -1),
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 2), -1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 2), 1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 5), 1),
                Arguments.of(Position.fromCoordinates(1, 10), Position.fromCoordinates(1, 9), 1)
        );
    }

    public static @NotNull Stream<Arguments> providePositionStringRepresentations() {
        return Stream.of(
                Arguments.of(0, 0, "A1"),
                Arguments.of(9, 9, "J10"),
                Arguments.of(0, 9, "J1"),
                Arguments.of(9, 0, "A10"),
                Arguments.of(3, 7, "H4")
        );
    }

    public static @NotNull Stream<Arguments> providePositionWithObjectAndWhetherEqual() {
        Position position = Position.fromCoordinates(4, 6);
        return Stream.of(
                Arguments.of(position, Position.fromCoordinates(4, 6), true),
                Arguments.of(position, Position.fromCoordinates(5, 6), false),
                Arguments.of(position, Position.fromCoordinates(4, 7), false),
                Arguments.of(position, null, false),
                Arguments.of(position, position, true),
                Arguments.of(position, new Object(), false)
        );
    }
}
