package game;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.game.FreedomLine;
import it.units.sdm.project.board.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.BoardUtils;

import java.util.SortedSet;

public class FreedomLineTests {

    private final String printedBoard = " 8 W  W  W  W  W  W  W  W\n"
            + " 7 W  B  W  W  W  W  W  W\n"
            + " 6 W  B  B  W  B  W  B  B\n"
            + " 5 W  B  B  W  B  W  B  W\n"
            + " 4 W  W  B  W  B  W  B  W\n"
            + " 3 B  W  W  W  B  W  B  W\n"
            + " 2 B  W  W  W  W  W  W  W\n"
            + " 1 B  W  W  B  W  W  W  W\n"
            + "   A  B  C  D  E  F  G  H";
    private final Board<Stone> board = BoardUtils.parseBoardFromString(printedBoard, 8, 8);

    @ParameterizedTest
    @MethodSource("providers.FreedomLineProviders#provideInitialPosition")
    void testFreedomLineCustomConstructor(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Assertions.assertDoesNotThrow(() -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        } else {
            Assertions.assertThrows(expectedException, () -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("providers.FreedomLineProviders#provideSetOfPositions")
    void testAddMethod(SortedSet<Position> positions, Class<Exception> expectedException) {
        if (expectedException == null) {
            Assertions.assertDoesNotThrow(() -> {
                FreedomLine freedomLine = new FreedomLine(board);
                for (Position position : positions) {
                    freedomLine.addPosition(position);
                }
            });
        } else {
            Assertions.assertThrows(expectedException, () -> {
                FreedomLine freedomLine = new FreedomLine(board);
                for (Position position : positions) {
                    freedomLine.addPosition(position);
                }
            });
        }
    }

}
