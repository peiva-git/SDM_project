import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTests {

    @Test
    void testPlayerEquals() {
        Player blackPlayer = new Player(Color.BLACK, "Name", "Surname");
        Player whitePlayer = new Player(Color.WHITE, "Name", "Surname");
        Assertions.assertNotEquals(blackPlayer, whitePlayer);
        Assertions.assertEquals(blackPlayer, blackPlayer);
    }

}
