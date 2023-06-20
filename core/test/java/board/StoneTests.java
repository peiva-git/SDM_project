package board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StoneTests {

    @ParameterizedTest
    @MethodSource("board.providers.PieceProviders#provideStoneColorsWithExceptionsForInvalidColors")
    void testStoneColorValidity(Color stoneColor, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new Stone(stoneColor));
        } else {
            assertThrows(expectedException, () -> new Stone(stoneColor));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.PieceProviders#provideStoneAndObjectAndWhetherEqual")
    void testEqualsByComparingStoneWithCandidateObject(@NotNull Stone stone, Object candidate, boolean shouldBeEqual) {
        assertEquals(shouldBeEqual, stone.equals(candidate));
    }

    @Test
    void testColorGetter() {
        Piece blackStone = new Stone(Color.BLACK);
        assertEquals(Color.BLACK, blackStone.getPieceColor());
        assertNotEquals(Color.WHITE, blackStone.getPieceColor());
    }

    @Test
    void testHashCodeForBothColors() {
        Stone whiteStone = new Stone(Color.WHITE);
        assertEquals(Objects.hash(whiteStone.getPieceColor()), whiteStone.hashCode());
        Stone blackStone = new Stone(Color.BLACK);
        assertEquals(Objects.hash(blackStone.getPieceColor()), blackStone.hashCode());
    }

}
