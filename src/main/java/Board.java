import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Board implements Iterable<Map.Entry<Position, Cell>> {

    private final Map<Position, Cell> cells = new HashMap<>();
    private final int numberOfRows;
    private final int numberOfColumns;

    public Board(int numberOfRows, int numberOfColumns) throws InvalidBoardSizeException {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        if (!isBoardSizeValid(this.numberOfRows, this.numberOfColumns))
            throw new InvalidBoardSizeException("The size of the board must be at least 1x1");
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                cells.put(new Position(i, j), new Cell());
            }
        }
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    private boolean isBoardSizeValid(int numberOfRows, int numberOfColumns) {
        return numberOfRows > 0 && (numberOfRows == numberOfColumns);
    }

    @NotNull
    public Cell getCell(@NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        return cell;
    }

    public void clearBoard() {
        for (Cell cell : cells.values()) {
            cell.clear();
        }
    }

    /**
     * Cells are considered adjacent in all directions, even diagonally
     * @param cellPosition find cells adjacent to the given cellPosition
     * @return a Set containing all the cells adjacent to the given position
     * @throws InvalidPositionException when the given position is invalid
     */
    @NotNull
    public Set<Cell> getAdjacentCells(@NotNull Position cellPosition) throws InvalidPositionException {
        if (cellPosition.getRow() > numberOfRows || cellPosition.getColumn() > numberOfColumns) {
            throw new InvalidPositionException("The specified position is outside the board");
        }
        Set<Cell> adjacentCells = new HashSet<>(4);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (j == 0 && i == 0) continue;
                    Position adjacentCellPosition = new Position(cellPosition.getRow() + i, cellPosition.getColumn() + j);
                    adjacentCells.add(cells.get(adjacentCellPosition));
                } catch (InvalidPositionException ignored) {
                    // if there are no adjacent cells in one direction, simply don't add them to the set
                }
            }
        }
        return adjacentCells;
    }

    public boolean areAdjacentCellsOccupied(@NotNull Position cellPosition) throws InvalidPositionException {
        Set<Cell> adjacentCells = getAdjacentCells(cellPosition);
        for(Cell cell : adjacentCells) {
            if(!cell.isOccupied()) return false;
        }
        return true;
    }

    public boolean hasBoardMoreThanOneFreeCell() {
        int numberOfFreeCellsOnBoard = 0;
        for (Map.Entry<Position, Cell> cellOnBoard : cells.entrySet()) {
            if (!cellOnBoard.getValue().isOccupied()) numberOfFreeCellsOnBoard++;
            if (numberOfFreeCellsOnBoard > 1) return true;
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<Position, Cell>> iterator() {
        return this.cells.entrySet().iterator();
    }

}
