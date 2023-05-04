package providers;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Set;
import java.util.stream.Stream;

public class MapBoardProviders {
    public static @NotNull Stream<Arguments> provideBoardSizes() {
        return Stream.of(
                Arguments.of(0, 1, InvalidBoardSizeException.class),
                Arguments.of(1, 0, InvalidBoardSizeException.class),
                Arguments.of(0, 0, InvalidBoardSizeException.class),
                Arguments.of(-1, -1, InvalidBoardSizeException.class),
                Arguments.of(2, 3, InvalidBoardSizeException.class),
                Arguments.of(1, 1, null)
        );
    }

    public static @NotNull Stream<Arguments> provideBoardPositions() {
        return Stream.of(
                Arguments.of(8, 1, null),
                Arguments.of(8, 2, null),
                Arguments.of(9, 8, InvalidPositionException.class)
        );
    }

    public static @NotNull Stream<Arguments> provideAdjacentBoardPositions() {
        return Stream.of(
                // corner positions
                Arguments.of(Position.fromCoordinates(1, 1), Set.of(
                        Position.fromCoordinates(2, 1),
                        Position.fromCoordinates(1, 2),
                        Position.fromCoordinates(2, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 1), Set.of(
                        Position.fromCoordinates(8, 2),
                        Position.fromCoordinates(7, 1),
                        Position.fromCoordinates(7, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 8), Set.of(
                        Position.fromCoordinates(8, 7),
                        Position.fromCoordinates(7, 8),
                        Position.fromCoordinates(7, 7)
                ), null),
                Arguments.of(Position.fromCoordinates(1, 8), Set.of(
                        Position.fromCoordinates(1, 7),
                        Position.fromCoordinates(2, 8),
                        Position.fromCoordinates(2, 7)
                ), null),
                // position on the board sides
                Arguments.of(Position.fromCoordinates(3, 1), Set.of(
                        Position.fromCoordinates(2, 1),
                        Position.fromCoordinates(4, 1),
                        Position.fromCoordinates(3, 2),
                        Position.fromCoordinates(2, 2),
                        Position.fromCoordinates(4, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 2), Set.of(
                        Position.fromCoordinates(8, 1),
                        Position.fromCoordinates(8, 3),
                        Position.fromCoordinates(7, 2),
                        Position.fromCoordinates(7, 1),
                        Position.fromCoordinates(7, 3)
                ), null),
                Arguments.of(Position.fromCoordinates(3, 8), Set.of(
                        Position.fromCoordinates(2, 8),
                        Position.fromCoordinates(4, 8),
                        Position.fromCoordinates(3, 7),
                        Position.fromCoordinates(4, 7),
                        Position.fromCoordinates(2, 7)
                ), null),
                Arguments.of(Position.fromCoordinates(1, 4), Set.of(
                        Position.fromCoordinates(1, 3),
                        Position.fromCoordinates(1, 5),
                        Position.fromCoordinates(2, 4),
                        Position.fromCoordinates(2, 3),
                        Position.fromCoordinates(2, 5)
                ), null),
                // position in the board center
                Arguments.of(Position.fromCoordinates(5, 3), Set.of(
                        Position.fromCoordinates(5, 2),
                        Position.fromCoordinates(5, 4),
                        Position.fromCoordinates(4, 3),
                        Position.fromCoordinates(6, 3),
                        Position.fromCoordinates(6, 4),
                        Position.fromCoordinates(6, 2),
                        Position.fromCoordinates(4, 2),
                        Position.fromCoordinates(4, 4)
                ), null),
                Arguments.of(Position.fromCoordinates(9, 8), null, InvalidPositionException.class)
        );
    }

    public static @NotNull Stream<Arguments> provideEmptyPrintedBoards() {
        return Stream.of(
                Arguments.of(8, 8,
                        " 8 -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  -  -  -  -\n"
                                + " 5 -  -  -  -  -  -  -  -\n"
                                + " 4 -  -  -  -  -  -  -  -\n"
                                + " 3 -  -  -  -  -  -  -  -\n"
                                + " 2 -  -  -  -  -  -  -  -\n"
                                + " 1 -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H"),
                Arguments.of(10, 10,
                        "10 -  -  -  -  -  -  -  -  -  -\n"
                                + " 9 -  -  -  -  -  -  -  -  -  -\n"
                                + " 8 -  -  -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  -  -  -  -  -  -\n"
                                + " 5 -  -  -  -  -  -  -  -  -  -\n"
                                + " 4 -  -  -  -  -  -  -  -  -  -\n"
                                + " 3 -  -  -  -  -  -  -  -  -  -\n"
                                + " 2 -  -  -  -  -  -  -  -  -  -\n"
                                + " 1 -  -  -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H  I  J")
        );
    }
}
