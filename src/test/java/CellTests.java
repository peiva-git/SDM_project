import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTests {

    Stone stone = new Stone(Color.BLACK);
    Cell cell = new Cell(stone);

    @Test
    void testGettersAfterDefaultConstructor() {
        Cell cell = new Cell();
        Assertions.assertEquals(cell.isOccupied(), Cell.Status.FREE);
        Assertions.assertNull(cell.getStone());
    }

    @Test
    void testGettersAfterCustomConstructor() {
        Assertions.assertEquals(cell.isOccupied(), Cell.Status.OCCUPIED);
        Assertions.assertEquals(cell.getStone(), stone);
    }

    @Test
    void testSetStone() {
        Stone newStone = new Stone(Color.WHITE);
        cell.setStone(newStone);
        Assertions.assertEquals(cell.getStone(), newStone);
    }

}
