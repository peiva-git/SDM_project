package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {

    @Test
    void testColorGetter() {
        Player whitePlayer = new Player(Color.WHITE, "white");
        assertEquals(Color.WHITE, whitePlayer.getColor());
    }
    @Test
    void testUsernameGetter() {
        Player whitePlayer = new Player(Color.WHITE, "white");
        assertEquals("white", whitePlayer.getUsername());
    }
    @Test
    void testToString() {
        Player whitePlayer = new Player(Color.WHITE, "white");
        assertEquals("white", whitePlayer.toString());
    }
    @Test
    void testHashCode() {
        Player whitePlayer = new Player(Color.WHITE, "white");
        assertEquals(Objects.hash(Color.WHITE), whitePlayer.hashCode());
    }

    @Test
    void testEqualsForTwoPlayersWithDifferentColorsAndDifferentUsername() {
        Player blackPlayer = new Player(Color.BLACK, "black");
        Player whitePlayer = new Player(Color.WHITE, "white");
        assertNotEquals(whitePlayer, blackPlayer);
        assertNotEquals(blackPlayer, whitePlayer);
    }

    @Test
    void testEqualsForTwoPlayersWithDifferentColorsAndSameUsername() {
        Player blackPlayer = new Player(Color.BLACK, "username");
        Player whitePlayer = new Player(Color.WHITE, "username");
        assertNotEquals(whitePlayer, blackPlayer);
        assertNotEquals(blackPlayer, whitePlayer);
    }

    @Test
    void testEqualsForTwoPlayersWithSameColorAndSameUsername() {
        Player firstBlackPlayer = new Player(Color.BLACK, "username");
        Player secondBlackPlayer = new Player(Color.BLACK, "username");
        assertEquals(secondBlackPlayer, firstBlackPlayer);
        assertEquals(firstBlackPlayer, secondBlackPlayer);
    }

    @Test
    void testEqualsWithOnePlayerAndNullValue() {
        Player blackPlayer = new Player(Color.BLACK, "black");
        assertNotEquals(null, blackPlayer);
    }

    @Test
    void testEqualsForTwoPlayersWithSameColorAndDifferentUsername() {
        Player firstBlackPlayer = new Player(Color.BLACK, "first_player");
        Player secondBlackPlayer = new Player(Color.BLACK, "second_player");
        assertEquals(secondBlackPlayer, firstBlackPlayer);
        assertEquals(firstBlackPlayer, secondBlackPlayer);
    }

    @ParameterizedTest
    @MethodSource("game.providers.ColorProviders#provideColors")
    void testColorValidity(@NotNull Color color, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new Player(color, "username"));
        } else {
            assertThrows(expectedException, () -> new Player(color, "username"));
        }
    }

}
