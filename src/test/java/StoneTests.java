import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StoneTests {

    Stone stone = new Stone(Color.BLACK);

    @Test
    void testColorGetter() {
        assertEquals(stone.getColor(), Color.BLACK);
    }

    @Test
    void testIsLiveAfterStoneCreation() {
        assertFalse(stone.isLive());
    }

    @Test
    void testIsLiveAfterLiveSetter() {
        stone.setLive(true);
        assertTrue(stone.isLive());
    }

}
