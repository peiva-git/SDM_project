package board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Stone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StoneTests {
    Piece blackStone = new Stone(Color.BLACK);

    @Test
    void testColorGetter() {
        assertEquals(Color.BLACK, blackStone.getPieceColor());
    }

    @Test
    void testEquals() {
        Piece newWhiteStone = new Stone(Color.WHITE);
        Piece newBlackStone = new Stone(Color.BLACK);
        assertEquals(blackStone, newBlackStone);
        assertNotEquals(blackStone, newWhiteStone);
    }

}
