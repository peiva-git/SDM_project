import exceptions.InvalidBoardSizeException;
import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board implements Iterable<Map.Entry<Position, Cell>> {

    private final Map<Position, Cell> cells = new HashMap<>();

    public Board(int numberOfRows, int numberOfColumns) throws InvalidBoardSizeException {
        if (!isSizeOfBoardValid(numberOfRows, numberOfColumns))
            throw new InvalidBoardSizeException("The size of the board must be 8x8 or 10x10.");
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                cells.put(new Position(i, j), new Cell());
            }
        }
    }

    private boolean isSizeOfBoardValid(int numberOfRows, int numberOfColumns) {
        return (numberOfRows == numberOfColumns) && ((numberOfRows == 8) || (numberOfRows == 10));
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<Position, Cell>> iterator() {
        return this.cells.entrySet().iterator();
    }

    @NotNull
    public Cell getCell(@NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        return cell;
    }

    @Nullable
    public Stone getStone(@NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) {
            throw new InvalidPositionException("Invalid board position");
        } else {
            return cell.getStone();
        }
    }

    public void putStone(@NotNull Stone stone,@NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) {
            throw new InvalidPositionException("Invalid board position");
        } else {
            if (cell.isOccupied()) throw new InvalidPositionException("The cell is already occupied");
            cell.putStone(stone);
        }
    }

    public void clearBoard() {
        for (Cell cell : cells.values()) {
            cell.clear();
        }
    }

}
