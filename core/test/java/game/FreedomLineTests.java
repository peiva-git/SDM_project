package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.game.FreedomLine;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static board.providers.BoardProviders.parseBoardFromString;
import static org.junit.jupiter.api.Assertions.*;

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

    private final Board<Piece> board = parseBoardFromString(printedBoard, 8, 8);

    @Test
    void testColorGetter() {
        FreedomLine blackFreedomLine = new FreedomLine(board, Position.fromCoordinates(0, 0));
        FreedomLine whiteFreedomLine = new FreedomLine(board, Position.fromCoordinates(0, 1));
        assertEquals(Color.BLACK, blackFreedomLine.getColor());
        assertEquals(Color.WHITE, whiteFreedomLine.getColor());
    }

    @Test
    void testPositionsGetter() {
        FreedomLine blackFreedomLine = new FreedomLine(board, Position.fromCoordinates(0, 0));
        assertEquals(Set.of(Position.fromCoordinates(0,0)), blackFreedomLine.getCellPositions());
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomLineProviders#provideFreedomLineWithCandidateObjectAndWhetherEqual")
    void testEquals(@NotNull FreedomLine freedomLine, @Nullable Object object, boolean shouldBeEqual) {
        assertEquals(shouldBeEqual, freedomLine.equals(object));
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomLineProviders#provideInitialLinePositionFor8x8BoardWithExceptionsForInvalidPosition")
    void testFreedomLineCustomConstructor(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        } else {
            assertThrows(expectedException, () -> new FreedomLine(board, Position.fromCoordinates(row, column)));
        }
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomLineProviders#provideSetOfPositions")
    void testAddMethod(Set<Position> positions, Class<Exception> expectedException) {
        FreedomLine freedomLine = new FreedomLine(board);
        if (expectedException == null) {
            assertDoesNotThrow(() ->
                    freedomLine.addPositions(positions)
            );
        } else {
            assertThrows(expectedException, () ->
                    freedomLine.addPositions(positions)
            );
        }
    }

}
