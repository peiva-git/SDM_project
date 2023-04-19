package it.units.sdm.project.core.board;

import com.badlogic.gdx.Gdx;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MapBoard<P extends Stone> implements Board<P> {

    private static final String MAP_BOARD_TAG = "MAP_BOARD";
    private final Map<Position, MapBoardCell<P>> cells = new TreeMap<>();
    private final static int MAX_NUMBER_OF_ROWS = 26;
    private final int numberOfRows;
    private final int numberOfColumns;

    public MapBoard(int numberOfRows, int numberOfColumns) throws InvalidBoardSizeException {
        if (!isBoardSizeValid(numberOfRows, numberOfColumns)) {
            Gdx.app.error(MAP_BOARD_TAG, "The size of the board must be at least 1x1 and at most 26x26");
            throw new InvalidBoardSizeException("The size of the board must be at least 1x1 and at most 26x26");
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initBoardWithEmptyCells();
    }

    private void initBoardWithEmptyCells() {
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                cells.put(Position.fromCoordinates(i, j), new MapBoardCell<>());
            }
        }
    }

    private boolean isBoardSizeValid(int numberOfRows, int numberOfColumns) {
        return numberOfRows > 0 && numberOfRows <= MAX_NUMBER_OF_ROWS && (numberOfRows == numberOfColumns);
    }

    @Override
    public int getNumberOfRows() {
        return numberOfRows;
    }

    @Override
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        return cell.isOccupied();
    }

    @Override
    public void clearCell(@NotNull Position position) {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        cell.clear();
    }

    @Override
    public void clearBoard() {
        for (Cell<P> cell : cells.values()) {
            cell.clear();
        }
    }

    @Override
    public void putPiece(@NotNull P piece,@NotNull  Position position) throws InvalidPositionException {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        cell.putPiece(piece);
    }

    @Override
    @Nullable
    public P getPiece(@NotNull Position position) throws InvalidPositionException {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        return cell.getPiece();
    }

    public Set<Position> getAdjacentPositions(Position position) throws InvalidPositionException {
        if (isPositionOutOfBoardBounds(position)) {
            throw new InvalidPositionException("The specified position is outside the board");
        }
        Set<Position> adjacentPositions = new HashSet<>(8);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (i == 0 && j == 0) continue;
                    if (position.getRow() + i <= numberOfRows && position.getColumn() + j <= numberOfColumns) {
                        Position adjacentPosition = Position.fromCoordinates(position.getRow() + i, position.getColumn() + j);
                        Gdx.app.log(MAP_BOARD_TAG, "Position " + adjacentPosition + " added to adjacent positions set");
                        adjacentPositions.add(adjacentPosition);
                    }
                } catch (InvalidPositionException ignored) {
                }
            }
        }
        return adjacentPositions;
    }

    private boolean isPositionOutOfBoardBounds(@NotNull Position position) {
        return position.getRow() > numberOfRows || position.getColumn() > numberOfColumns;
    }

    public boolean areAdjacentCellsOccupied(@NotNull Position cellPosition) throws InvalidPositionException {
        return getAdjacentPositions(cellPosition).stream()
                .allMatch(this::isCellOccupied);
    }

    public boolean hasBoardMoreThanOneFreeCell() {
        return cells.values().stream()
                .filter(boardCell -> !boardCell.isOccupied())
                .count() > 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = numberOfRows; i > 0; i--) {
            for (int j = 1; j <= numberOfColumns; j++) {
                if (j == 1) {
                    if (i < 10) {
                        sb.append(" ").append(i).append(" ");
                    } else {
                        sb.append(i).append(" ");
                    }
                }
                if (isCellOccupied(Position.fromCoordinates(i, j))) {
                    if (getPiece(Position.fromCoordinates(i, j)).getColor() == Stone.Color.WHITE) {
                        sb.append("W");
                    } else {
                        sb.append("B");
                    }
                } else {
                    sb.append("-");
                }
                if (j < numberOfColumns) {
                    sb.append("  ");
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append(" ");
        for (int j = 0; j < numberOfColumns; j++) {
            sb.append("  ").append((char) ('A' + j));
        }
        return sb.toString();
    }

    @NotNull
    @Override
    public Iterator<Position> iterator() {
        return this.cells.keySet().iterator();
    }

    @NotNull
    @Override
    public Set<Position> getPositions() {
        return cells.keySet();
    }

    private static class MapBoardCell<P> implements Cell<P> {

        @Nullable
        private P piece;

        public MapBoardCell() {
        }

        @Override
        public void putPiece(P piece) {
            this.piece = piece;
        }

        @Override
        @Nullable
        public P getPiece() {
            return piece;
        }

        @Override
        public boolean isOccupied() {
            return piece != null;
        }

        @Override
        public void clear() {
            this.piece = null;
        }

    }
}