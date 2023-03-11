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
}
