import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LineTests {

    @Test
    void testEquals() {
        Line line1 = new Line(Stone.Color.WHITE);
        Line line2 = new Line(Stone.Color.WHITE);
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
