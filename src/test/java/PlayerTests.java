import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTests {

    @Test
    void testPlayerEquals() {
        Player player1 = new Player(Stone.Color.BLACK);
        Player player2 = new Player(Stone.Color.WHITE);
        Assertions.assertNotEquals(player1, player2);
        Assertions.assertEquals(player1, player1);
    }

}
