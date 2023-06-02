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

public class MapBoard<P extends Stone> implements Board<P> {

    public static final int MIN_BOARD_SIZE = 2;
    public final static int MAX_BOARD_SIZE = 26;
    private final Map<Position, Cell<P>> cells = new TreeMap<>();
    private final int numberOfRows;
    private final int numberOfColumns;

    /**
     * Creates a new Board instance with a TreeMap implementation
     * @param numberOfRows The number of rows on the board
     * @param numberOfColumns The number of columns on the board
     * @throws InvalidBoardSizeException In case the board sizes aren't matching,
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
                cells.put(Position.fromCoordinates(i, j), new Cell<>());
            }
        }
    }

    private boolean isBoardSizeValid(int numberOfRows, int numberOfColumns) {
        return numberOfRows > MIN_BOARD_SIZE - 1 && numberOfRows <= MAX_BOARD_SIZE && (numberOfRows == numberOfColumns);
    }

    @Override
    public void clearCell(@NotNull Position position) {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException("Invalid board position");
        cell.clear();
    }

    @Override
    public void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException {
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

    /**
     * Generates a string representation of the board. The board is displayed here as a chess board,
     * with letters indicating columns and numbers indicating rows. Free cells are marked with a - character,
     * while cells occupied by the first player are marked with a W and cells occupied by the second player
     * are marked with a B.
     * @return The board's string representation
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

    private static class Cell<P> {

        @Nullable
        private P piece;

        public void putPiece(P piece) {
            this.piece = piece;
        }

        @Nullable
        public P getPiece() {
            return piece;
        }

        public void clear() {
            this.piece = null;
        }
    }
}
