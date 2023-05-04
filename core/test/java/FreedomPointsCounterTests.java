import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.terminal.MapBoard;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Scanner;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FreedomPointsCounterTests {

    private static @NotNull Board<Stone> parseBoardFromString(@NotNull String printedBoard, int numberOfRows, int numberOfColumns) {
        Scanner scanner = new Scanner(printedBoard);
        Board<Stone> board = new MapBoard<>(numberOfRows, numberOfColumns);
        while (scanner.hasNextLine()) {
            if (scanner.hasNextInt()) {
                int currentRow = scanner.nextInt();
                IntStream.rangeClosed(1, numberOfColumns)
                        .forEach(currentColumn -> {
                            String placeholder = scanner.next("[WB-]");
                            if (placeholder.equals("W")) {
                                board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(currentRow, currentColumn));
                            } else if (placeholder.equals("B")) {
                                board.putPiece(new Stone(Color.BLACK), Position.fromCoordinates(currentRow, currentColumn));
                            }
                        });
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


    @ParameterizedTest
    @MethodSource("providers.FreedomPointsCounterProviders#printedBoardsProvider")
    void testGetWinner(String printedBoard, int numberOfRows, int numberOfColumns, int blackScore, int whiteScore) {
        Board<Stone> board = parseBoardFromString(printedBoard, numberOfRows, numberOfColumns);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        Assertions.assertEquals(freedomPointsCounter.getBlackPlayerScore(), blackScore);
        Assertions.assertEquals(freedomPointsCounter.getWhitePlayerScore(), whiteScore);
    }

}
