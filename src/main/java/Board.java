import exceptions.InvalidBoardPositionException;
import exceptions.InvalidSizeOfBoardException;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Cell> cells = new HashMap<>();

    public Board(int numberOfRows, int numberOfColumns) throws InvalidSizeOfBoardException {
        if (!isSizeOfBoardValid(numberOfRows, numberOfColumns))
            throw new InvalidSizeOfBoardException("The size of the board must be 8x8 or 10x10.");
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                cells.put(new Position(i, j), new Cell());
            }
        }
    }

    private boolean isSizeOfBoardValid(int numberOfRows, int numberOfColumns) {
        return (numberOfRows == numberOfColumns) && ((numberOfRows == 8) || (numberOfRows == 10));
    }

    public Cell getCell(Position position) throws InvalidBoardPositionException{
        Cell cell = cells.get(position);
        if(cell == null) throw new InvalidBoardPositionException("Invalid board position");
        return cell;
    }

    public void putStone(Stone stone, Position position) throws InvalidBoardPositionException {
        Cell cell = cells.get(position);
        if (cell == null) {
            throw new InvalidBoardPositionException("Invalid board position");
        } else {
            if(cell.isOccupied()) throw new InvalidBoardPositionException("The cell is already occupied");
            cell.putStone(stone);
        }
    }

}
