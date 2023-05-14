package providers;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class FreedomLineProviders {

    public static @NotNull Stream<Arguments> provideInitialPosition() {
        return Stream.of(
                Arguments.of(
                        Position.fromCoordinates(7, 0),
                        null
                ),
                Arguments.of(
                        Position.fromCoordinates(8, 0),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        Position.fromCoordinates(-1, -1),
                        InvalidPositionException.class
                )
        );
    }

    public static @NotNull Stream<Arguments> provideSetOfPositions() {
        return Stream.of(
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 0),
                                Position.fromCoordinates(5, 0),
                                Position.fromCoordinates(4,0),
                                Position.fromCoordinates(3,0)
                        )),
                        null
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 0),
                                Position.fromCoordinates(5, 0),
                                Position.fromCoordinates(4,0),
                                Position.fromCoordinates(3,0),
                                Position.fromCoordinates(2,0)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 1)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(6, 1),
                                Position.fromCoordinates(5, 2)
                        )),
                        null
                ),
                // diagonal right
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(1, 4),
                                Position.fromCoordinates(2, 5)
                        )),
                        null
                )
        );
    }


}
