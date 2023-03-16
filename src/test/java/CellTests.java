import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTests {

    Stone stone = new Stone(Stone.Color.BLACK);
    Cell cell = new Cell(stone);

    @Test
    void testGettersAfterDefaultConstructor() {
        Cell cell = new Cell();
        Assertions.assertFalse(cell.isOccupied());
        Assertions.assertNull(cell.getStone());
    }

    @Test
    void testGettersAfterCustomConstructor() {
        Assertions.assertTrue(cell.isOccupied());
        Assertions.assertEquals(cell.getStone(), stone);
    }

    @Test
    void testPutStone() {
        Stone newStone = new Stone(Stone.Color.WHITE);
        cell.putStone(newStone);
        Assertions.assertEquals(cell.getStone(), newStone);
    }

    @Test
    void testClearCell() {
        cell.clear();
        Assertions.assertFalse(cell.isOccupied());
        Assertions.assertNull(cell.getStone());
    }

}
