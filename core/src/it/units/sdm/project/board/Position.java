package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Representation of a {@link Board} position. The {@link Position} is composed by two coordinates:
 * a row index and a column index. Both of them must be a positive integer number.
 */
public class Position implements Comparable<Position> {

    /**
     * Maximum allowed {@link Position} column index for this implementation.
     * Since {@link Position}s are printed using chess-board-like coordinates, i.e. A5, G7 and so on,
     * this limit is set to the size of the English alphabet - 1 to prevent incorrect string conversion
     */
    static final int MAXIMUM_COLUMN_INDEX = 25;
    private final int row;
    private final int column;

    private Position(int row, int column) throws InvalidPositionException {
        if (!areCoordinatesWithinAllowedRange(row, column)) {
            throw new InvalidPositionException("Invalid parameter: position row and column must be >= 0");
        }
        this.row = row;
        this.column = column;
    }

    private static boolean isACoordinateNegative(int row, int column) {
        return row < 0 || column < 0;
    }

    private static boolean areCoordinatesWithinAllowedRange(int row, int column) {
        return !isACoordinateNegative(row, column) && (column <= MAXIMUM_COLUMN_INDEX);
    }

    /**
     * Returns this {@link Position}'s row index, zero-indexed
     * @return This {@link Position}'s row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns this {@link Position}'s column index, zero-indexed
     * @return This {@link Position}'s column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Two {@link Position}s are equal if they have the same coordinates
     * @param o The object to compare with
     * @return {@code true} if the {@link Position}s are equal, {@code false} otherwise
     */
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

    /**
     * Returns a {@link String} representation of this {@link Position}. The column is converted to a letter and is printed
     * before the row, for example G4
     * @return The {@link String} representation of this {@link Position}
     */
    @Override
    public String toString() {
        char printedColumn = (char) ('A' + column);
        return printedColumn + String.valueOf(row + 1);
    }

    /**
     * The assumed ordering is that of a chess board: assuming to be looking from the first player's side,
     * the columns are ordered from A to H starting on the left to the right,
     * while the rows are ordered from 1 to 8 from the bottom to the top.
     * We're therefore ordering all the {@link Board} {@link Position}s starting from letters first and then numbers,
     * eg. A1, B1, C1...
     *
     * @param position the {@link Position} to compare this {@link Position} with
     * @return 0 if the {@link Position}s are the same, < 0 if {@code position} comes before this {@link Position},
     * > 0 if {@code position} comes after this {@link Position}
     */
    /*
     * 8 -  -  -  -  -  -  -  -
     * 7 -  -  -  -  -  -  -  -
     * 6 -  -  -  -  -  -  -  -
     * 5 -  -  -  -  -  -  -  -
     * 4 -  -  -  -  -  -  -  -
     * 3 -  -  -  -  -  -  -  -
     * 2 -  -  -  -  -  -  -  -
     * 1 -  -  -  -  -  -  -  -
     *   A  B  C  D  E  F  G  H
     * **White player's side is here**
     */
    @Override
    public int compareTo(@NotNull Position position) {
        if (row == position.getRow()) {
            return Integer.compare(column, position.getColumn());
        }
        return Integer.compare(row, position.getRow());
    }

    /**
     * Creates a new {@link Board} {@link Position} instance from the given coordinates
     * @param row The row coordinate, starting from index 0
     * @param column The column coordinate, starting from index 0
     * @return The new {@link Position} instance
     * @throws InvalidPositionException In case the indexes are negative
     */
    @NotNull
    public static Position fromCoordinates(int row, int column) throws InvalidPositionException {
        return new Position(row, column);
    }

}
