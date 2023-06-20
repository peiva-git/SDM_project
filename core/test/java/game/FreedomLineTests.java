package game;

import board.providers.BoardProviders;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.game.FreedomLine;
import it.units.sdm.project.board.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.SortedSet;

class FreedomLineTests {

    private final String printedBoard = " 8 W  W  W  W  W  W  W  W\n"
            + " 7 W  B  W  W  W  W  W  W\n"
            + " 6 W  B  B  W  B  W  B  B\n"
            + " 5 W  B  B  W  B  W  B  W\n"
            + " 4 W  W  B  W  B  W  B  W\n"
            + " 3 B  W  W  W  B  W  B  W\n"
            + " 2 B  W  W  W  W  W  W  W\n"
            + " 1 B  W  W  B  W  W  W  W\n"
            + "   A  B  C  D  E  F  G  H";
    private final Board<Piece> board = BoardProviders.parseBoardFromString(printedBoard, 8, 8);

    @ParameterizedTest
    @MethodSource("game.providers.FreedomLineProviders#provideInitialLinePositionFor8x8BoardWithException")
    void testFreedomLineCustomConstructor(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            Assertions.assertDoesNotThrow(() -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        } else {
            Assertions.assertThrows(expectedException, () -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomLineProviders#provideSetOfPositions")
    void testAddMethod(SortedSet<Position> positions, Class<Exception> expectedException) {
        FreedomLine freedomLine = new FreedomLine(board);
        if (expectedException == null) {
            Assertions.assertDoesNotThrow(() ->
                    freedomLine.addPositions(positions)
            );
        } else {
            Assertions.assertThrows(expectedException, () ->
                    freedomLine.addPositions(positions)
            );
        }
    }

}
