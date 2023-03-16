

import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

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

    public boolean areAdjacentCellsOccupied(@NotNull Position cellPosition) {
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                try {
                    if (j == 0 && i == 0) continue;
                    Position adjacentCellPosition = new Position(cellPosition.getRow() + i, cellPosition.getColumn() + j);
                    Cell adjacentCell = board.getCell(adjacentCellPosition);
                    if (!adjacentCell.isOccupied()) return false;
                } catch (InvalidPositionException ignored) {
                }
            }
        }
        return true;
    }

}
