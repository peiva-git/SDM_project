package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {

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
        assertNotEquals(blackPlayer, null);
    }
    @Test
    void testEqualsForTwoPlayersWithSameColorAndDifferentUsername() {
        Player firstBlackPlayer = new Player(Color.BLACK, "first_player");
        Player secondBlackPlayer = new Player(Color.BLACK, "second_player");
        assertEquals(secondBlackPlayer, firstBlackPlayer);
        assertEquals(firstBlackPlayer, secondBlackPlayer);
    }

    @Test
    void testColorValidity() {
        assertThrows(RuntimeException.class, () -> new Player(Color.BLUE, "username"));
        assertDoesNotThrow(() -> new Player(Color.WHITE, "username"));
        assertDoesNotThrow(() -> new Player(Color.BLACK, "username"));
    }

}
