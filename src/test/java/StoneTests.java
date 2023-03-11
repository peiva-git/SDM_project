import org.junit.jupiter.api.Test;

public class StoneTests {

    Stone stone = new Stone(Color.BLACK);

    @Test
    void testColorGetter() {
        assert(stone.getColor() == Color.BLACK);
    }

    @Test
    void testIsLiveAfterStoneCreation() {
        assert(!stone.isLive());
    }

    @Test
    void testIsLiveAfterLiveSetter() {
        stone.setLive(true);
        assert(stone.isLive());
    }

}
