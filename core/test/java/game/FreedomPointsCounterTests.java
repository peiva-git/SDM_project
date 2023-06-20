package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.BoardUtils;

import static org.junit.jupiter.api.Assertions.*;

class FreedomPointsCounterTests {

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
        Board<Piece> board = BoardUtils.parseBoardFromString(printedBoard, 8, 8);
        assertEquals(printedBoard, board.toString());
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomPointsCounterProviders#printedBoardsProvider")
    void testGetPlayerScore(String printedBoard, int boardSize, int blackScore, int whiteScore) {
        Player whitePlayer = new Player(Color.WHITE, "white");
        Player blackPlayer = new Player(Color.BLACK, "black");
        Board<Piece> board = BoardUtils.parseBoardFromString(printedBoard, boardSize, boardSize);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        assertEquals(blackScore, freedomPointsCounter.getPlayerScore(blackPlayer));
        assertEquals(whiteScore, freedomPointsCounter.getPlayerScore(whitePlayer));
    }

    @Test
    void testGetPlayerScoreWhetherIsProvidedTheWrongColor() {
        Board<Piece> board = new MapBoard<>(8, 8);
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        assertThrows(IllegalArgumentException.class, () ->
                freedomPointsCounter.getPlayerScore(Color.BLUE)
        );
    }

}
