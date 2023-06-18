package board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Stone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StoneTests {
    Stone blackStone = new Stone(Color.BLACK);

    @Test
    void testColorGetter() {
        assertEquals(blackStone.getColor(), Color.BLACK);
    }

    @Test
    void testEquals() {
        Stone newWhiteStone = new Stone(Color.WHITE);
        Stone newBlackStone = new Stone(Color.BLACK);
        assertEquals(blackStone, newBlackStone);
        assertNotEquals(blackStone, newWhiteStone);
    }

}
