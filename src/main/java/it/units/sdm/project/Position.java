package it.units.sdm.project;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Position implements Comparable<Position> {
    private final int row;
    private final int column;

    private Position(int row, int column) throws InvalidPositionException {
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

    @Override
    public String toString() {
        char column = (char) ('A' + this.column - 1);
        return String.format("%c%d", column, row);
    }

    /**
     * The assumed ordering is that of a chess board: assuming to be looking from the white player's side,
     * the columns are ordered from A to H starting on the left to the right,
     * while the rows are ordered from 1 to 8 from the bottom to the top.
     * We're therefore ordering all the board positions starting from letters first and then numbers
     *
     * @param position the position to compare this instance with
     * @return 0 if the positions are the same, < 0 if position comes before this position,
     * > 0 if position comes after this position
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

    @NotNull
    public static Position fromCoordinates(int row, int column) throws InvalidPositionException {
        return new Position(row, column);
    }

}
