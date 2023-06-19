package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import static it.units.sdm.project.board.Position.MAXIMUM_COLUMN_INDEX;

/**
 * This class represents an implementation of the {@link Board} interface using a {@link TreeMap} to hold
 * information about the {@link Piece}s that are on this {@link Board}. The {@link Position}s on this {@link Board}
 * are ordered based on the ordering defined in the {@link Position} class.
 */
public class MapBoard implements Board {

    /**
     * Minimum allowed {@link Board} size for this implementation.
     */
    public static final int MIN_BOARD_SIZE = 2;

    /**
     * Maximum allowed {@link Board} size for this implementation.
     */
    public static final int MAX_BOARD_SIZE = MAXIMUM_COLUMN_INDEX + 1;
    private final Map<Position, Cell> cells = new TreeMap<>();
    private final int numberOfRows;
    private final int numberOfColumns;

    /**
     * Creates a new {@link Board} instance with a {@link TreeMap} implementation
     * @param numberOfRows The number of rows on the {@link Board}
     * @param numberOfColumns The number of columns on the {@link Board}
     * @throws InvalidBoardSizeException In case the {@link Board} sizes aren't matching,
     * or they're outside the allowed range of [2, 26]
     */
    public MapBoard(int numberOfRows, int numberOfColumns) throws InvalidBoardSizeException {
        if (!isBoardSizeValid(numberOfRows, numberOfColumns)) {
            throw new InvalidBoardSizeException(
                    "The size of the board must be at least " + MIN_BOARD_SIZE + "x" + MIN_BOARD_SIZE
                            + " and at most " + MAX_BOARD_SIZE + "x" + MAX_BOARD_SIZE);
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initBoardWithEmptyCells();
    }

    private void initBoardWithEmptyCells() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells.put(Position.fromCoordinates(i, j), new Cell());
            }
        }
    }

    private boolean isBoardSizeValid(int numberOfRows, int numberOfColumns) {
        return numberOfRows >= MIN_BOARD_SIZE && numberOfRows <= MAX_BOARD_SIZE && (numberOfRows == numberOfColumns);
    }

    @Override
    public void clearCell(@NotNull Position position) {
        Cell cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        cell.clear();
    }

    @Override
    public void putPiece(@NotNull Piece piece, @NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        cell.putPiece(piece);
    }

    @Override
    @Nullable
    public Piece getPiece(@NotNull Position position) throws InvalidPositionException {
        Cell cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        return cell.getPiece();
    }

    /**
     * Generates a {@link String} representation of the {@link Board}. The {@link Board} is displayed here as a chess board,
     * with letters indicating columns and numbers indicating rows. Free cells are marked with a - character,
     * while cells occupied by the white {@link it.units.sdm.project.game.Player} are marked with a W
     * and cells occupied by the black {@link it.units.sdm.project.game.Player} are marked with a B.
     * @return The {@link Board}'s {@link String} representation
     */
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
                if (isCellOccupied(Position.fromCoordinates(i - 1, j - 1))) {
                    if (Objects.requireNonNull(getPiece(Position.fromCoordinates(i - 1, j - 1))).getColor() == Color.WHITE) {
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
    public Set<Position> getPositions() {
        return cells.keySet();
    }

    private static class Cell {

        @Nullable
        private Piece piece;

        public void putPiece(Piece piece) {
            this.piece = piece;
        }

        @Nullable
        public Piece getPiece() {
            return piece;
        }

        public void clear() {
            this.piece = null;
        }
    }
}
