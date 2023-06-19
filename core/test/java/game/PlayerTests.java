package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {

    @Test
    void testPlayerEquals() {
        Player blackPlayer = new Player(Color.BLACK, "username");
        Player whitePlayer = new Player(Color.WHITE, "username");
        assertNotEquals(blackPlayer, whitePlayer);
        assertEquals(blackPlayer, blackPlayer);
    }

    @Test
    void testValidColor() {
        assertThrows(RuntimeException.class, () -> new Player(Color.BLUE, "username"));
        assertDoesNotThrow(() -> new Player(Color.WHITE, "username"));
        assertDoesNotThrow(() -> new Player(Color.BLACK, "username"));
    }

}
