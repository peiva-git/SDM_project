import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BoardAnalyzerTests {

    private final Board board = new Board(8, 8);

    @Test
    void testHasBoardMoreThanOneFreeCell() {
        BoardAnalyzer boardAnalyzer = new BoardAnalyzer(board);
        fillTheBoardWithWhiteStones();
        Assertions.assertFalse(boardAnalyzer.hasBoardMoreThanOneFreeCell());
        board.getCell(new Position(1,1)).clear();
        Assertions.assertFalse(boardAnalyzer.hasBoardMoreThanOneFreeCell());
        board.getCell(new Position(1,2)).clear();
        Assertions.assertTrue(boardAnalyzer.hasBoardMoreThanOneFreeCell());
    }

    private void fillTheBoardWithWhiteStones() {
        for (Map.Entry<Position, Cell> boardCell : board) {
            boardCell.getValue().putStone(new Stone(Stone.Color.WHITE));
        }
    }
    @Test
    void testAreAdjacentCellsOccupied() {
        BoardAnalyzer boardAnalyzer = new BoardAnalyzer(board);
        fillTheBoardWithWhiteStones();
        Assertions.assertTrue(boardAnalyzer.areAdjacentCellsOccupied(new Position(1,1)));
        board.getCell(new Position(1,1)).clear();
        Assertions.assertTrue(boardAnalyzer.areAdjacentCellsOccupied(new Position(1,1)));
        Assertions.assertFalse(boardAnalyzer.areAdjacentCellsOccupied(new Position(1,2)));
    }

}
