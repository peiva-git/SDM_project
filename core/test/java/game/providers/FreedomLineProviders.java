package game.providers;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.game.FreedomLine;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.Stream;

import static board.providers.BoardProviders.parseBoardFromString;

public class FreedomLineProviders {

    public static @NotNull Stream<Arguments> provideFreedomLineWithCandidateObjectAndWhetherEqual() {
        String printedBoard = " 8 W  W  W  W  W  W  W  W\n"
                + " 7 W  B  W  W  W  W  W  W\n"
                + " 6 W  B  B  W  B  W  B  B\n"
                + " 5 W  B  B  W  B  W  B  W\n"
                + " 4 W  W  B  W  B  W  B  W\n"
                + " 3 B  W  W  W  B  W  B  W\n"
                + " 2 B  W  W  W  W  W  W  W\n"
                + " 1 B  W  W  B  W  W  W  W\n"
                + "   A  B  C  D  E  F  G  H";

        Board<Piece> board = parseBoardFromString(printedBoard, 8);
        FreedomLine freedomLine = new FreedomLine(board, Position.fromCoordinates(0,0));
        return Stream.of(
                Arguments.of(freedomLine, new FreedomLine(board, Position.fromCoordinates(0,1)), false),
                Arguments.of(freedomLine, new FreedomLine(board, Position.fromCoordinates(0,3)), false),
                Arguments.of(freedomLine, freedomLine, true),
                Arguments.of(freedomLine, new FreedomLine(board, Position.fromCoordinates(0,0)), true),
                Arguments.of(freedomLine, null, false),
                Arguments.of(freedomLine, new Object(), false)
        );
    }

    public static @NotNull Stream<Arguments> provideInitialLinePositionFor8x8BoardWithExceptionsForInvalidPosition() {
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

    /**
     * This provides a {@link Set} of {@link Position}s, with at least two of them horizontally aligned.
     * If the expected {@link Exception} is {@code null}, the provided {@link Position}s are either adjacent
     * to the last {@link FreedomLine}'s {@link Position} or to the first {@link FreedomLine}'s {@link Position}.
     * Otherwise, there is at least one position which is either adjacent but in the wrong direction or not adjacent at all.
     * @return A {@link Stream} of the specified {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provideSetOfPositionsWhichAtLeastTwoOfThemAreHorizontallyAlignedWithException() {
        return Stream.of(
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 2),
                                        Position.fromCoordinates(1,3),
                                        Position.fromCoordinates(1,1)
                                )
                        ),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 2),
                                        Position.fromCoordinates(5,3)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 2),
                                        Position.fromCoordinates(1,3),
                                        Position.fromCoordinates(2,3)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 2),
                                        Position.fromCoordinates(1,3),
                                        Position.fromCoordinates(1,7)
                                )
                        ),
                        InvalidPositionException.class
                )
        );
    }

    /**
     * This provides a {@link Set} of {@link Position}s, with at least two of them vertically aligned.
     * If the expected {@link Exception} is {@code null}, the provided {@link Position}s are either adjacent
     * to the last {@link FreedomLine}'s {@link Position} or to the first {@link FreedomLine}'s {@link Position}.
     * Otherwise, there is at least one position which is either adjacent but in the wrong direction or not adjacent at all.
     * @return A {@link Stream} of the specified {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provideSetOfPositionsWhichAtLeastTwoOfThemAreVerticallyAlignedWithException() {
        return Stream.of(
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 1),
                                        Position.fromCoordinates(2,1),
                                        Position.fromCoordinates(0,1)
                                )
                        ),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(0, 1),
                                        Position.fromCoordinates(1,1),
                                        Position.fromCoordinates(3,2)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(0, 1),
                                        Position.fromCoordinates(1,1),
                                        Position.fromCoordinates(5,1)
                                )
                        ),
                        InvalidPositionException.class
                )
        );
    }

    /**
     * This provides a {@link Set} of {@link Position}s, with at least two of them diagonally right aligned.
     * If the expected {@link Exception} is {@code null}, the provided {@link Position}s are either adjacent
     * to the last {@link FreedomLine}'s {@link Position} or to the first {@link FreedomLine}'s {@link Position}.
     * Otherwise, there is at least one position which is either adjacent but in the wrong direction or not adjacent at all.
     * @return A {@link Stream} of the specified {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provideSetOfPositionsWhichAtLeastTwoOfThemAreDiagonallyRightAlignedWithException() {
        return Stream.of(
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 2),
                                        Position.fromCoordinates(0,1),
                                        Position.fromCoordinates(2,3)
                                )
                        ),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(0, 1),
                                        Position.fromCoordinates(1,2),
                                        Position.fromCoordinates(2,4)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(2, 3),
                                        Position.fromCoordinates(1,2),
                                        Position.fromCoordinates(4,6)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(2, 3),
                                        Position.fromCoordinates(1,2),
                                        Position.fromCoordinates(4,1)
                                )
                        ),
                        InvalidPositionException.class
                )
        );
    }

    /**
     * This provides a {@link Set} of {@link Position}s, with at least two of them diagonally left aligned.
     * If the expected {@link Exception} is {@code null}, the provided {@link Position}s are either adjacent
     * to the last {@link FreedomLine}'s {@link Position} or to the first {@link FreedomLine}'s {@link Position}.
     * Otherwise, there is at least one position which is either adjacent but in the wrong direction or not adjacent at all.
     * @return A {@link Stream} of the specified {@link Arguments}
     */
    public static @NotNull Stream<Arguments> provideSetOfPositionsWhichAtLeastTwoOfThemAreDiagonallyLeftAlignedWithException() {
        return Stream.of(
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(1, 3),
                                        Position.fromCoordinates(0,4),
                                        Position.fromCoordinates(2,2)
                                )
                        ),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(0, 4),
                                        Position.fromCoordinates(1,3),
                                        Position.fromCoordinates(4,2)
                                )
                        ),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(
                                List.of(
                                        Position.fromCoordinates(2, 2),
                                        Position.fromCoordinates(1,3),
                                        Position.fromCoordinates(4,7)
                                )
                        ),
                        InvalidPositionException.class
                )
        );
    }


}
