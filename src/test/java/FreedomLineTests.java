import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreedomLineTests {

    @Test
    void testEquals() {
        FreedomLine line1 = new FreedomLine(Stone.Color.WHITE);
        FreedomLine line2 = new FreedomLine(Stone.Color.WHITE);
        line1.addPosition(new Position(2,3));
        line1.addPosition(new Position(3,4));
        line1.addPosition(new Position(4,4));
        line1.addPosition(new Position(5,4));
        line2.addPosition(new Position(2,3));
        line2.addPosition(new Position(3,4));
        line2.addPosition(new Position(4,4));
        line2.addPosition(new Position(5,4));
        Assertions.assertEquals(line1,line2);
        line2.addPosition(new Position(6,4));
        Assertions.assertNotEquals(line1,line2);
    }

}
