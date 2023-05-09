package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.Direction;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * FreedomLine is a line of stones with the same color.
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

    public FreedomLine(@NotNull Board<? extends Stone> board) {
        this.color = null;
        this.direction = null;
        this.board = board;
    }

    public FreedomLine(@NotNull Board<? extends Stone> board, @NotNull Position initialPosition) {
        this.board = board;
        addPosition(initialPosition);
    }

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

    public @Nullable Color getColor() {
        return color;
    }

    /**
     * @return This method returns all the positions that are in this freedom line,
     * ordered by the custom ordering defined in the Position class
     */
    public Set<Position> getCellPositions() {
        return cellPositions;
    }

    @NotNull
    public Position first() {
        return cellPositions.first();
    }

    @NotNull
    public Position last() {
        return cellPositions.last();
    }

    public int size() {
        return cellPositions.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreedomLine)) return false;
        FreedomLine that = (FreedomLine) o;
        return Objects.equals(board, that.board) && Objects.equals(color, that.color) && direction == that.direction && Objects.equals(cellPositions, that.cellPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, color, direction, cellPositions);
    }

    @Override
    public String toString() {
        return "FreedomLine{" +
                "color=" + color +
                ", direction=" + direction +
                ", cellPositions=" + cellPositions +
                '}';
    }
}
