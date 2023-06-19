package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.board.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.BoardUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FreedomPointsCounterTests {

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
        Board board = BoardUtils.parseBoardFromString(printedBoard, 8, 8);
        assertEquals(printedBoard, board.toString());
    }


    @ParameterizedTest
    @MethodSource("game.providers.FreedomPointsCounterProviders#printedBoardsProvider")
    void testGetWinner(String printedBoard, int numberOfRows, int numberOfColumns, int blackScore, int whiteScore) {
        Board board = BoardUtils.parseBoardFromString(printedBoard, numberOfRows, numberOfColumns);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        Assertions.assertEquals(freedomPointsCounter.getPlayerScore(Color.BLACK), blackScore);
        Assertions.assertEquals(freedomPointsCounter.getPlayerScore(Color.WHITE), whiteScore);
    }

}
