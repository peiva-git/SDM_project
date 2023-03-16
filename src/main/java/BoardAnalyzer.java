

import java.util.Map;

public class BoardAnalyzer {

    private final Board board;

    public BoardAnalyzer(Board board) {
        this.board = board;
    }

    public boolean hasBoardMoreThanOneFreeCell() {
        int numberOfFreeCellsOnBoard = 0;
        for (Map.Entry<Position, Cell> cellOnBoard : board) {
            if (!cellOnBoard.getValue().isOccupied()) numberOfFreeCellsOnBoard++;
            if (numberOfFreeCellsOnBoard > 1) return true;
        }
        return false;
    }

}
