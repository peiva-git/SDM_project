package board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class StoneTests {
    Piece blackStone = new Stone(Color.BLACK);

    @ParameterizedTest
    @MethodSource("board.providers.StoneProviders#provideStoneColorsWithExceptionsForInvalidColors")
    void testStoneColorValidity(Color stoneColor, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new Stone(stoneColor));
        } else {
            assertThrows(expectedException, () -> new Stone(stoneColor));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.StoneProviders#provideStoneAndObjectAndWhetherEqual")
    void testEqualsByComparingStoneWithCandidateObject(@NotNull Stone stone, Object candidate, boolean shouldBeEqual) {
        assertEquals(shouldBeEqual, stone.equals(candidate));
    }

    @Test
    void testColorGetter() {
        assertEquals(Color.BLACK, blackStone.getPieceColor());
    }

    @Test
    void testEquals() {
        Piece newWhiteStone = new Stone(Color.WHITE);
        Piece newBlackStone = new Stone(Color.BLACK);
        assertEquals(blackStone, newBlackStone);
        assertNotEquals(blackStone, newWhiteStone);
    }

}
