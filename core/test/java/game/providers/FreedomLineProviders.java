package game.providers;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.Stream;

public class FreedomLineProviders {

    public static @NotNull Stream<Arguments> provideInitialLinePositionFor8x8BoardWithException() {
        return Stream.of(
                Arguments.of(
                        7, 0,
                        null
                ),
                Arguments.of(
                        8, 0,
                        InvalidPositionException.class
                ),
                Arguments.of(
                        -1, -1,
                        InvalidPositionException.class
                )
        );
    }

    public static @NotNull Stream<Arguments> provideSetOfPositions() {
        return Stream.of(
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(7, 0),
//                                Position.fromCoordinates(6, 0),
//                                Position.fromCoordinates(5, 0),
//                                Position.fromCoordinates(4, 0),
//                                Position.fromCoordinates(3, 0)
//                        )),
//                        null
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(7, 0),
//                                Position.fromCoordinates(6, 0),
//                                Position.fromCoordinates(5, 0),
//                                Position.fromCoordinates(4, 0),
//                                Position.fromCoordinates(3, 0),
//                                Position.fromCoordinates(2, 0)
//                        )),
//                        InvalidPositionException.class
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(7, 0),
//                                Position.fromCoordinates(6, 1)
//                        )),
//                        InvalidPositionException.class
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(6, 1),
//                                Position.fromCoordinates(5, 2),
//                                Position.fromCoordinates(4, 2)
//                        )),
//                        InvalidPositionException.class
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(0, 1),
//                                Position.fromCoordinates(1, 2),
//                                Position.fromCoordinates(2, 3),
//                                Position.fromCoordinates(3, 3)
//                        )),
//                        InvalidPositionException.class
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(7, 0),
//                                Position.fromCoordinates(6, 2)
//                        )),
//                        InvalidPositionException.class
//                ),
//                Arguments.of(
//                        new LinkedHashSet<>(List.of(
//                                Position.fromCoordinates(0, 1),
//                                Position.fromCoordinates(0, 2),
//                                Position.fromCoordinates(1, 2)
//                        )),
//                        InvalidPositionException.class
//                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(1, 2),
                                Position.fromCoordinates(2, 3)
                        )),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(0, 2),
                                Position.fromCoordinates(1, 1),
                                Position.fromCoordinates(2, 1)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(1, 1),
                                Position.fromCoordinates(2, 1)))
                        ,
                        null
                )
        );
    }


}
