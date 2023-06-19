package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {

    @Test
    void testPlayerEquals() {
        Player blackPlayer = new Player(Color.BLACK, "Name", "Surname");
        Player whitePlayer = new Player(Color.WHITE, "Name", "Surname");
        assertNotEquals(blackPlayer, whitePlayer);
        assertEquals(blackPlayer, blackPlayer);
    }

    @Test
    void testValidColor() {
        assertThrows(RuntimeException.class, () -> new Player(Color.BLUE, "Name", "Surname"));
        assertDoesNotThrow(() -> new Player(Color.WHITE, "Name", "Surname"));
        assertDoesNotThrow(() -> new Player(Color.BLACK, "Name", "Surname"));
    }

}
