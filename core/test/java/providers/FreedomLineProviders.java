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
                        Position.fromCoordinates(8, 1),
                        null
                ),
                Arguments.of(
                        Position.fromCoordinates(9, 1),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        Position.fromCoordinates(0, 0),
                        InvalidPositionException.class
                )
        );
    }

    public static @NotNull Stream<Arguments> provideSetOfPositions() {
        return Stream.of(
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(8, 1),
                                Position.fromCoordinates(7, 1),
                                Position.fromCoordinates(6, 1),
                                Position.fromCoordinates(5,1),
                                Position.fromCoordinates(4,1)
                        )),
                        null
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(8, 1),
                                Position.fromCoordinates(7, 1),
                                Position.fromCoordinates(6, 1),
                                Position.fromCoordinates(5,1),
                                Position.fromCoordinates(4,1),
                                Position.fromCoordinates(3,1)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(8, 1),
                                Position.fromCoordinates(7, 2)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(7, 2),
                                Position.fromCoordinates(6, 3)
                        )),
                        null
                ),
                // diagonal right
                Arguments.of(
                        new TreeSet<>(Set.of(
                                Position.fromCoordinates(2, 5),
                                Position.fromCoordinates(3, 6)
                        )),
                        null
                )
        );
    }


}
