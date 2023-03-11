import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) throws InvalidPositionException {
        if (arePositionCoordinatesInvalid(row, column)) {
            throw new InvalidPositionException("Invalid paramater: position row and column must be > 1");
        }
        this.row = row;
        this.column = column;
    }

    private static boolean arePositionCoordinatesInvalid(int row, int column) {
        return row < 1 || column < 1;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public boolean isPositionAdjacentTo(@NotNull Position otherPosition) {
        if (otherPosition.getRow() == row && otherPosition.getColumn() == column + 1) {
            return true;
        } else if (otherPosition.getRow() == row && otherPosition.getColumn() == column - 1) {
            return true;
        } else if (otherPosition.getColumn() == column && otherPosition.getRow() == row + 1) {
            return true;
        } else return otherPosition.getColumn() == column && otherPosition.getRow() == row - 1;
    }
}
