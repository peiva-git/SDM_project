package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Stone line of the same color.
 * The line {@link Position}s are stored in a
 * {@link TreeSet} by considering the custom ordering defined in the {@link Position} class.
 * Every instance of {@link FreedomLine} is described by its {@link Direction}, {@link Color} and stone {@link Position}s.
 */
public class FreedomLine {
    @NotNull
    private final Board<? extends Stone> board;
    @Nullable
    private Color color;
    @Nullable
    private Direction direction;
    @NotNull
    private final SortedSet<Position> cellPositions = new TreeSet<>();

    /**
     * Creates a {@link FreedomLine} instance
     * @param board Board on which is placed the stone line
     */
    public FreedomLine(@NotNull Board<? extends Stone> board) {
        this.color = null;
        this.direction = null;
        this.board = board;
    }

    /**
     * Creates a {@link FreedomLine} instance from a starting position
     * @param board Board on which is placed the stone line
     * @param initialPosition Initial position of the line
     */
    public FreedomLine(@NotNull Board<? extends Stone> board, @NotNull Position initialPosition) {
        this.board = board;
        addPosition(initialPosition);
    }

    /**
     * Adds a stone to this {@link FreedomLine}. This method checks if the
     * position to add is valid according to the line direction, to the last line
     * stone position and to the stone color on position to add.
     * @param position The position to add
     * @throws InvalidPositionException If the position isn't valid according to the last stone position,
     * to the line direction and to the stone color on the position to add
     */
    public void addPosition(@NotNull Position position) throws InvalidPositionException {
        Stone stone = board.getPiece(position);
        if (cellPositions.isEmpty()) setColor(stone);
        if (checkFreedomLineDirection(position)) {
            if (checkStoneColor(stone)) {
                cellPositions.add(position);
            } else {
                throw new InvalidPositionException("Stone on the current position has a different color");
            }
        } else {
            throw new InvalidPositionException("Invalid position");
        }
    }

    private void setColor(@Nullable Stone stone) {
        if (stone == null) {
            color = null;
        } else {
            color = stone.getColor();
        }
    }

    private boolean checkFreedomLineDirection(@NotNull Position nextPosition) {
        if (cellPositions.isEmpty()) return true;
        if (direction == null) setDirection(nextPosition);
        switch (direction) {
            case VERTICAL:
                if (!isVertical(cellPositions.last(), nextPosition)) return false;
                break;
            case HORIZONTAL:
                if (!isHorizontal(cellPositions.last(), nextPosition)) return false;
                break;
            case DIAGONAL_LEFT:
                if (!isDiagonalLeft(cellPositions.last(), nextPosition)) return false;
                break;
            case DIAGONAL_RIGHT:
                if (!isDiagonalRight(cellPositions.last(), nextPosition)) return false;
                break;
        }
        return true;
    }

    private boolean checkStoneColor(@Nullable Stone stone) throws InvalidPositionException {
        if (stone == null)
            throw new InvalidPositionException("There is no piece on the current position");
        return stone.getColor() == color;
    }

    private void setDirection(@NotNull Position nextPosition) {
        if (isHorizontal(cellPositions.last(), nextPosition)) {
            direction = Direction.HORIZONTAL;
            return;
        }
        if (isVertical(cellPositions.last(), nextPosition)) {
            direction = Direction.VERTICAL;
            return;
        }
        if (isDiagonalLeft(cellPositions.last(), nextPosition)) {
            direction = Direction.DIAGONAL_LEFT;
            return;
        }
        if (isDiagonalRight(cellPositions.last(), nextPosition)) {
            direction = Direction.DIAGONAL_RIGHT;
        }
    }

    private boolean isHorizontal(@NotNull Position firstPosition, @NotNull Position secondPosition) {
        return firstPosition.getRow() == secondPosition.getRow() && ((firstPosition.getColumn() == secondPosition.getColumn() + 1) || firstPosition.getColumn() == secondPosition.getColumn() - 1);
    }

    private boolean isVertical(@NotNull Position firstPosition, @NotNull Position secondPosition) {
        return firstPosition.getColumn() == secondPosition.getColumn() && ((firstPosition.getRow() == secondPosition.getRow() + 1) || firstPosition.getRow() == secondPosition.getRow() - 1);
    }

    private boolean isDiagonalLeft(@NotNull Position firstPosition, @NotNull Position secondPosition) {
        if (((firstPosition.getRow() == secondPosition.getRow() + 1) || (firstPosition.getRow() == secondPosition.getRow() - 1))) {
            return (firstPosition.getColumn() == secondPosition.getColumn() + 1) || firstPosition.getColumn() == secondPosition.getColumn() - 1;
        }
        return false;
    }

    private boolean isDiagonalRight(@NotNull Position firstPosition, @NotNull Position secondPosition) {
        return firstPosition.getRow() == secondPosition.getRow() && ((firstPosition.getColumn() == secondPosition.getColumn() + 1) || firstPosition.getColumn() == secondPosition.getColumn() - 1);
    }

    /**
     * Gets the line color
     * @return The line color
     */
    public @Nullable Color getColor() {
        return color;
    }

    /**
     * @return This method returns all the positions that are in this {@link FreedomLine},
     * ordered by the custom ordering defined in the {@link Position} class
     */
    @NotNull
    public SortedSet<Position> getCellPositions() {
        return cellPositions;
    }

    /**
     * Gets the first position of the line according to the {@link Position} ordering.
     * @return The first position of the line
     */
    @NotNull
    public Position first() {
        return cellPositions.first();
    }

    /**
     * Gets the last position of the line according to the {@link Position} ordering.
     * @return The last position of the line
     */
    @NotNull
    public Position last() {
        return cellPositions.last();
    }

    /**
     * Gets the size of the line.
     * @return The size of the line
     */
    public int size() {
        return cellPositions.size();
    }

    /**
     * Two lines are equal if they have the same color, the same stone positions and the same direction.
     * @param o The object to compare with
     * @return True if the lines are equal, otherwise it returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreedomLine)) return false;
        FreedomLine that = (FreedomLine) o;
        return Objects.equals(color, that.color) && direction == that.direction && Objects.equals(cellPositions, that.cellPositions);
    }

    /**
     * {@link String} representation of the {@link FreedomLine}
     * @return String composed by line color, line direction and stone positions
     */
    @Override
    public String toString() {
        return "FreedomLine{" +
                "color=" + color +
                ", direction=" + direction +
                ", cellPositions=" + cellPositions +
                '}';
    }

    /**
     * Describes all the possible directions that can have a {@link FreedomLine} on a {@link Board}
     */
    public enum Direction {
        /**
         * A line has a horizontal direction if all the
         * line stones are placed on the same row
         */
        HORIZONTAL,
        /**
         * A line has a vertical direction if all the line stones
         * are placed on the same column
         */
        VERTICAL,
        /**
         * A line has a diagonal left direction if all the line stones are placed
         * diagonally left with respect to the starting position
         */
        DIAGONAL_LEFT,
        /**
         * A line has a diagonal right direction if all the line stones are placed
         * diagonally right with respect to the starting position
         */
        DIAGONAL_RIGHT
    }
}
