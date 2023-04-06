import it.units.sdm.project.core.game.Player;
import it.units.sdm.project.core.board.Stone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTests {

    @Test
    void testPlayerEquals() {
        Player blackPlayer = new Player(Stone.Color.BLACK, "Name", "Surname");
        Player whitePlayer = new Player(Stone.Color.WHITE, "Name", "Surname");
        Assertions.assertNotEquals(blackPlayer, whitePlayer);
        Assertions.assertEquals(blackPlayer, blackPlayer);
    }

}
