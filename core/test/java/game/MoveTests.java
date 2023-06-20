package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTests {

    private final Player whitePlayer = new Player(Color.WHITE, "white");
    private final Player blackPlayer = new Player(Color.BLACK, "black");
    private final Move whiteMove = new Move(whitePlayer, Position.fromCoordinates(0, 0));
    private final Move blackMove = new Move(blackPlayer, Position.fromCoordinates(1, 1));

    @Test
    void testPlayerGetter() {
        assertEquals(whitePlayer, whiteMove.getPlayer());
        assertEquals(blackPlayer, blackMove.getPlayer());
    }
}
