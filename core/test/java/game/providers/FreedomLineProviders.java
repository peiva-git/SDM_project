package game.providers;

import board.providers.BoardProviders;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.game.FreedomLine;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.Stream;

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

        Board<Piece> board = BoardProviders.parseBoardFromString(printedBoard, 8, 8);
        FreedomLine freedomLine = new FreedomLine(board, Position.fromCoordinates(0,0));
        return Stream.of(
                Arguments.of(freedomLine, new FreedomLine(board, Position.fromCoordinates(0,1)), false),
                Arguments.of(freedomLine, new FreedomLine(board, Position.fromCoordinates(0,3)), false),
                Arguments.of(freedomLine, freedomLine, true),
                Arguments.of(freedomLine, null, false),
                Arguments.of(freedomLine, new Object(), false)
        );
    }

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
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 0),
                                Position.fromCoordinates(5, 0),
                                Position.fromCoordinates(4, 0),
                                Position.fromCoordinates(3, 0)
                        )),
                        null
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(5, 0),
                                Position.fromCoordinates(6, 0),
                                Position.fromCoordinates(1, 0)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 1)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(6, 1),
                                Position.fromCoordinates(5, 2),
                                Position.fromCoordinates(4, 2)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(1, 2),
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(2, 3),
                                Position.fromCoordinates(5, 0)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(1, 2),
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(7, 3)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(7, 0),
                                Position.fromCoordinates(6, 2)
                        )),
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(0, 2),
                                Position.fromCoordinates(1, 2)
                        )),
                        InvalidPositionException.class
                ),
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
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(0, 2),
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(0, 5)))
                        ,
                        InvalidPositionException.class
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(
                                Position.fromCoordinates(2, 3),
                                Position.fromCoordinates(1, 2),
                                Position.fromCoordinates(0, 1),
                                Position.fromCoordinates(3, 4)))
                        ,
                        InvalidPositionException.class
                )
        );
    }


}
