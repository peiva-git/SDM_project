package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MoveTests {

    private final Player whitePlayer = new Player(Color.WHITE, "white");
    private final Player blackPlayer = new Player(Color.BLACK, "black");
    private final Position firstPosition = Position.fromCoordinates(0, 0);
    private final Position secondPosition = Position.fromCoordinates(1, 1);
    private final Move whiteMove = new Move(whitePlayer, firstPosition);
    private final Move blackMove = new Move(blackPlayer, secondPosition);

    @Test
    void testPlayerGetter() {
        assertEquals(whitePlayer, whiteMove.getPlayer());
        assertEquals(blackPlayer, blackMove.getPlayer());
    }

    @Test
    void testPositionGetter() {
        assertEquals(firstPosition, whiteMove.getPosition());
        assertEquals(secondPosition, blackMove.getPosition());
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(whitePlayer, firstPosition), whiteMove.hashCode());
        assertEquals(Objects.hash(blackPlayer, secondPosition), blackMove.hashCode());
    }

    @Test
    void testEqualsBetweenTwoMovesByTwoDifferentPlayersWithDifferentPlayedPositions() {
        assertNotEquals(whiteMove, blackMove);
        assertNotEquals(blackMove, whiteMove);
    }

    @Test
    void testEqualsBetweenTwoMovesOfTheSamePlayerWithDifferentPlayedPositions() {
        Move secondWhiteMove = new Move(whitePlayer, secondPosition);
        assertNotEquals(whiteMove, secondWhiteMove);
        assertNotEquals(secondWhiteMove, whiteMove);
    }

    @Test
    void testEqualsWithNullValue() {
        assertNotEquals(null, whiteMove);
    }

    @Test
    void testEqualsBetweenTwoMovesOfTheSamePlayerWithTheSamePlayedPositions() {
        Move secondWhiteMove = new Move(whitePlayer, firstPosition);
        assertEquals(whiteMove, secondWhiteMove);
        assertEquals(secondWhiteMove, whiteMove);
    }

    @Test
    void testEqualsBetweenTwoMovesByTwoDifferentPlayerWithTheSamePlayedPosition() {
        Move secondWhiteMove = new Move(whitePlayer, secondPosition);
        assertNotEquals(secondWhiteMove, blackMove);
        assertNotEquals(blackMove, secondWhiteMove);
    }

}
