import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.FreedomLine;
import it.units.sdm.project.board.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreedomLineTests {

    @Test
    void testEquals() {
        FreedomLine line1 = new FreedomLine(Color.WHITE);
        FreedomLine line2 = new FreedomLine(Color.WHITE);
        line1.addPosition(Position.fromCoordinates(2,3));
        line1.addPosition(Position.fromCoordinates(3,4));
        line1.addPosition(Position.fromCoordinates(4,4));
        line1.addPosition(Position.fromCoordinates(5,4));
        line2.addPosition(Position.fromCoordinates(2,3));
        line2.addPosition(Position.fromCoordinates(3,4));
        line2.addPosition(Position.fromCoordinates(4,4));
        line2.addPosition(Position.fromCoordinates(5,4));
        Assertions.assertEquals(line1,line2);
        line2.addPosition(Position.fromCoordinates(6,4));
        Assertions.assertNotEquals(line1,line2);
    }

}
