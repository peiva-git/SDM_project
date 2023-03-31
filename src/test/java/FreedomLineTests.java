import it.units.sdm.project.core.FreedomLine;
import it.units.sdm.project.Position;
import it.units.sdm.project.Stone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreedomLineTests {

    @Test
    void testEquals() {
        FreedomLine line1 = new FreedomLine(Stone.Color.WHITE);
        FreedomLine line2 = new FreedomLine(Stone.Color.WHITE);
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
