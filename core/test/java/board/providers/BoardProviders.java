package board.providers;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public class BoardProviders {
    public static @NotNull Stream<Arguments> provideBoardSizesWithExceptionsForInvalidMapBoardSizes() {
        return Stream.of(
                Arguments.of(0, 1, InvalidBoardSizeException.class),
                Arguments.of(1, 0, InvalidBoardSizeException.class),
                Arguments.of(0, 0, InvalidBoardSizeException.class),
                Arguments.of(-1, -1, InvalidBoardSizeException.class),
                Arguments.of(2, 3, InvalidBoardSizeException.class),
                Arguments.of(1, 1, InvalidBoardSizeException.class),
                Arguments.of(27, 27, InvalidBoardSizeException.class),
                Arguments.of(2, 2, null)
        );
    }

    public static @NotNull Stream<Arguments> providePositionsFor8x8BoardWithExceptionForInvalidPositions() {
        return Stream.of(
                Arguments.of(7, 0, null),
                Arguments.of(7, 1, null),
                Arguments.of(8, 7, InvalidPositionException.class)
        );
    }

    /**
     * Each item in the returned {@link Stream} provides a starting {@link Position},
     * a {@link Set} of all the {@link Position}s that should be adjacent to the starting {@link Position}
     * and an {@link InvalidPositionException} argument.
     * If the starting {@link Position} is invalid, the {@link Set} of adjacent {@link Position}s is empty and the
     * final argument is set to {@link InvalidPositionException}. Otherwise, the final argument is set to {@code null}
     * @return A {@link Stream} of {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provideStartingPositionAndAdjacent8x8BoardPositionsWithExceptionForInvalidStartingPosition() {
        return Stream.of(
                // corner positions
                Arguments.of(Position.fromCoordinates(0, 0), Set.of(
                        Position.fromCoordinates(1, 0),
                        Position.fromCoordinates(0, 1),
                        Position.fromCoordinates(1, 1)
                ), null),
                Arguments.of(Position.fromCoordinates(7, 0), Set.of(
                        Position.fromCoordinates(7, 1),
                        Position.fromCoordinates(6, 0),
                        Position.fromCoordinates(6, 1)
                ), null),
                Arguments.of(Position.fromCoordinates(7, 7), Set.of(
                        Position.fromCoordinates(7, 6),
                        Position.fromCoordinates(6, 7),
                        Position.fromCoordinates(6, 6)
                ), null),
                Arguments.of(Position.fromCoordinates(0, 7), Set.of(
                        Position.fromCoordinates(0, 6),
                        Position.fromCoordinates(1, 7),
                        Position.fromCoordinates(1, 6)
                ), null),
                // position on the board sides
                Arguments.of(Position.fromCoordinates(2, 0), Set.of(
                        Position.fromCoordinates(1, 0),
                        Position.fromCoordinates(3, 0),
                        Position.fromCoordinates(2, 1),
                        Position.fromCoordinates(1, 1),
                        Position.fromCoordinates(3, 1)
                ), null),
                Arguments.of(Position.fromCoordinates(7, 1), Set.of(
                        Position.fromCoordinates(7, 0),
                        Position.fromCoordinates(7, 2),
                        Position.fromCoordinates(6, 1),
                        Position.fromCoordinates(6, 0),
                        Position.fromCoordinates(6, 2)
                ), null),
                Arguments.of(Position.fromCoordinates(2, 7), Set.of(
                        Position.fromCoordinates(1, 7),
                        Position.fromCoordinates(3, 7),
                        Position.fromCoordinates(2, 6),
                        Position.fromCoordinates(3, 6),
                        Position.fromCoordinates(1, 6)
                ), null),
                Arguments.of(Position.fromCoordinates(0, 3), Set.of(
                        Position.fromCoordinates(0, 2),
                        Position.fromCoordinates(0, 4),
                        Position.fromCoordinates(1, 3),
                        Position.fromCoordinates(1, 2),
                        Position.fromCoordinates(1, 4)
                ), null),
                // position in the board center
                Arguments.of(Position.fromCoordinates(4, 2), Set.of(
                        Position.fromCoordinates(4, 1),
                        Position.fromCoordinates(4, 3),
                        Position.fromCoordinates(3, 2),
                        Position.fromCoordinates(5, 2),
                        Position.fromCoordinates(5, 3),
                        Position.fromCoordinates(5, 1),
                        Position.fromCoordinates(3, 1),
                        Position.fromCoordinates(3, 3)
                ), null),
                Arguments.of(Position.fromCoordinates(8, 7), Collections.emptySet(), InvalidPositionException.class)
        );
    }

    public static @NotNull Stream<Arguments> provideNumberOfRowsAndNumberOfColumnsForEmptyBoardStringRepresentation() {
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

    /**
     * Each item in the returned {@link Stream} provides a non-empty {@link it.units.sdm.project.board.Board} {@link String}
     * representation, followed by the {@link Color#WHITE} {@link it.units.sdm.project.board.Piece} {@link Position}
     * and then the {@link Color#BLACK} {@link it.units.sdm.project.board.Piece} {@link Position}
     * @return A {@link Stream} of {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provide8x8NotEmptyBoardStringRepresentationWithPositionsToOccupyWithPieces() {
        return Stream.of(
                Arguments.of(" 8 -  -  -  -  -  -  -  -\n"
                        + " 7 -  -  -  -  -  -  -  -\n"
                        + " 6 -  W  -  -  -  -  -  -\n"
                        + " 5 -  -  -  -  -  -  -  -\n"
                        + " 4 -  -  -  B  -  -  -  -\n"
                        + " 3 -  -  -  -  -  -  -  -\n"
                        + " 2 -  -  -  -  -  -  -  -\n"
                        + " 1 -  -  -  -  -  -  -  -\n"
                        + "   A  B  C  D  E  F  G  H",
                        Position.fromCoordinates(5, 1), Position.fromCoordinates(3, 3))
        );
    }

}
