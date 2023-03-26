import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTests {

    @Test
    void testIncrementNumberOfFreedomLines() {
        Score score = new Score(new Player(Stone.Color.WHITE, "Mario", "Rossi"));
        assertEquals(score.getNumberOfFreedomLines(), 0);
        score.incrementNumberOfFreedomLines();
        assertEquals(score.getNumberOfFreedomLines(), 1);
        score.incrementNumberOfFreedomLines();
        assertEquals(score.getNumberOfFreedomLines(), 2);
    }

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



}
