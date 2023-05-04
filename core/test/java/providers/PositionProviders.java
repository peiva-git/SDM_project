package providers;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PositionProviders {
    public static @NotNull Stream<Arguments> providePositionCoordinates() {
        return Stream.of(
                Arguments.of(0, 0, null),
                Arguments.of(-1, -1, InvalidPositionException.class),
                Arguments.of(5, 8, null)
        );
    }

    public static @NotNull Stream<Arguments> providePositionPairsForComparison() {
        return Stream.of(
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 1), 0),
                Arguments.of(Position.fromCoordinates(2, 1), Position.fromCoordinates(3, 1), -1),
                Arguments.of(Position.fromCoordinates(1, 1), Position.fromCoordinates(1, 2), -1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 2), 1),
                Arguments.of(Position.fromCoordinates(3, 1), Position.fromCoordinates(2, 5), 1),
                Arguments.of(Position.fromCoordinates(1, 10), Position.fromCoordinates(1, 9), 1)
        );
    }
}
