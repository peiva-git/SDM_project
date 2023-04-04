import it.units.sdm.project.core.MapBoard;
import it.units.sdm.project.core.FreedomPointsCounter;
import it.units.sdm.project.Position;
import it.units.sdm.project.Stone;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FreedomPointsCounterTests {

    private static @NotNull Board<Stone> parseBoardFromString(@NotNull String printedBoard, int numberOfRows, int numberOfColumns) {
        Scanner scanner = new Scanner(printedBoard);
        Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);
        while (scanner.hasNextLine()) {
            if (scanner.hasNextInt()) {
                int currentRow = scanner.nextInt();
                for (int currentColumn = 1; currentColumn <= numberOfColumns; currentColumn++) {
                    String placeholder = scanner.next("[WB-]");
                    if (placeholder.equals("W")) {
                        board.putPiece(new Stone(Stone.Color.WHITE), Position.fromCoordinates(currentRow, currentColumn));
                    } else if (placeholder.equals("B")) {
                        board.putPiece(new Stone(Stone.Color.BLACK), Position.fromCoordinates(currentRow, currentColumn));
                    }
                }
                scanner.nextLine();
            } else {
                scanner.close();
                break;
            }
        }
        return board;
    }

    @Test
    void testBoardParsing() {
        String printedBoard =
                " 8 -  -  -  -  -  -  -  -\n"
                        + " 7 -  -  -  -  -  -  -  -\n"
                        + " 6 -  -  -  -  -  -  -  -\n"
                        + " 5 -  -  -  -  -  -  -  -\n"
                        + " 4 -  -  -  -  -  -  -  -\n"
                        + " 3 -  -  -  -  -  -  -  -\n"
                        + " 2 -  -  -  -  -  -  -  -\n"
                        + " 1 -  -  -  -  -  -  -  -\n"
                        + "   A  B  C  D  E  F  G  H";
        Board<Stone> board = parseBoardFromString(printedBoard, 8, 8);
        assertEquals(printedBoard, board.toString());
    }

    private static @NotNull Stream<Arguments> printedBoardsProvider() {
        return Stream.of(
                Arguments.of(" 4 W  B  W  B\n"
                                + " 3 W  B  W  B\n"
                                + " 2 W  B  W  B\n"
                                + " 1 W  B  W  B\n"
                                + "   A  B  C  D",
                        4, 4, 2, 2
                ),
                Arguments.of(" 4 W  W  W  W\n"
                                + " 3 B  B  B  B\n"
                                + " 2 W  W  W  W\n"
                                + " 1 B  B  B  B\n"
                                + "   A  B  C  D",
                        4, 4, 2, 2
                ),
                Arguments.of(" 4 W  -  -  B\n"
                                + " 3 -  W  B  -\n"
                                + " 2 -  B  W  -\n"
                                + " 1 B  -  -  W\n"
                                + "   A  B  C  D",
                        4, 4, 1, 1
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  W  B  W  B  W\n"
                                + " 3 B  W  W  W  B  W  B  W\n"
                                + " 2 B  W  W  W  W  W  W  W\n"
                                + " 1 B  W  W  B  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 8, 2, 2
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  W  B  W  B  W\n"
                                + " 3 B  W  W  B  B  W  B  W\n"
                                + " 2 B  W  W  W  B  W  W  W\n"
                                + " 1 B  W  W  W  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 8, 2, 2
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  B  B  W  B  W\n"
                                + " 3 B  -  W  B  B  W  B  W\n"
                                + " 2 B  W  W  W  B  W  W  W\n"
                                + " 1 B  W  W  W  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 8, 3, 1
                ),
                Arguments.of(
                        " 8 -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  W  -  -  -\n"
                                + " 5 -  -  -  W  B  B  -  -\n"
                                + " 4 -  -  W  B  W  B  -  -\n"
                                + " 3 -  -  -  -  -  W  B  -\n"
                                + " 2 -  -  -  -  -  -  W  B\n"
                                + " 1 -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 8, 1, 1)
        );
    }


    @ParameterizedTest
    @MethodSource("printedBoardsProvider")
    void testGetWinner(String printedBoard, int numberOfRows, int numberOfColumns, int blackScore, int whiteScore) {
        Board<Stone> board = parseBoardFromString(printedBoard, numberOfRows, numberOfColumns);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        Assertions.assertEquals(freedomPointsCounter.getBlackPlayerScore(), blackScore);
        Assertions.assertEquals(freedomPointsCounter.getWhitePlayerScore(), whiteScore);
    }

}
