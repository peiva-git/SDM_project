import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FreedomPointsCounterTests {

    private static @NotNull Board parseBoardFromString(@NotNull String printedBoard, int numberOfRows, int numberOfColumns) {
        Scanner scanner = new Scanner(printedBoard);
        Board board = new Board(numberOfRows, numberOfColumns);
        while (scanner.hasNextLine()) {
            if (scanner.hasNextInt()) {
                int currentRow = scanner.nextInt();
                for (int currentColumn = 1; currentColumn <= numberOfColumns; currentColumn++) {
                    String placeholder = scanner.next("[WB-]");
                    if (placeholder.equals("W")) {
                        board.putStone(new Position(currentRow, currentColumn), Stone.Color.WHITE);
                    } else if (placeholder.equals("B")) {
                        board.putStone(new Position(currentRow, currentColumn), Stone.Color.BLACK);
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
                "8 -  -  -  -  -  -  -  -\n"
                        + "7 -  -  -  -  -  -  -  -\n"
                        + "6 -  -  -  -  -  -  -  -\n"
                        + "5 -  -  -  -  -  -  -  -\n"
                        + "4 -  -  -  -  -  -  -  -\n"
                        + "3 -  -  -  -  -  -  -  -\n"
                        + "2 -  -  -  -  -  -  -  -\n"
                        + "1 -  -  -  -  -  -  -  -\n"
                        + "  A  B  C  D  E  F  G  H";
        Board board = parseBoardFromString(printedBoard, 8, 8);
        assertEquals(printedBoard, board.toString());
    }


    @Test
    void testGetWinner() {
        String printedBoard =
                "8 -  -  -  -  -  -  -  -\n"
                        + "7 W  B  W  W  W  W  W  W\n"
                        + "6 W  B  B  W  B  W  B  B\n"
                        + "5 W  B  B  W  B  W  B  W\n"
                        + "4 W  W  B  W  B  W  B  W\n"
                        + "3 B  W  W  W  B  W  B  W\n"
                        + "2 B  W  W  W  W  W  W  W\n"
                        + "1 B  W  W  B  W  W  W  W\n"
                        + "  A  B  C  D  E  F  G  H";

        Board board = parseBoardFromString(printedBoard, 8, 8);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        Assertions.assertEquals(freedomPointsCounter.getBlackPlayerScore(),2);
        Assertions.assertEquals(freedomPointsCounter.getWhitePlayerScore(), 3);
    }

}
