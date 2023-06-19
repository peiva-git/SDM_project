package board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Piece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PieceTests {
    Piece blackPiece = new Piece(Color.BLACK);

    @Test
    void testColorGetter() {
        assertEquals(blackPiece.getColor(), Color.BLACK);
    }

    @Test
    void testEquals() {
        Piece newWhitePiece = new Piece(Color.WHITE);
        Piece newBlackPiece = new Piece(Color.BLACK);
        assertEquals(blackPiece, newBlackPiece);
        assertNotEquals(blackPiece, newWhitePiece);
    }

}
