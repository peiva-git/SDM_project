import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StoneTests {

    Stone blackStone = new Stone(Color.BLACK);

    @Test
    void testColorGetter() {
        assertEquals(blackStone.getColor(), Color.BLACK);
    }

    @Test
    void testIsLiveAfterStoneCreation() {
        assertFalse(blackStone.isLive());
    }

    @Test
    void testIsLiveAfterLiveSetter() {
        blackStone.setLive(true);
        assertTrue(blackStone.isLive());
    }

    @Test
    void testEquals() {
        Stone newWhiteStone = new Stone(Color.WHITE);
        Stone newBlackStone = new Stone(Color.BLACK);
        assertEquals(blackStone, newBlackStone);
        assertNotEquals(blackStone, newWhiteStone);
    }

}
