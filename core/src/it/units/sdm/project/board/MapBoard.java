package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * This class represents an implementation of the {@link Board} interface using a {@link TreeMap} to hold
 * information about the pieces {@link P} that are on this {@link Board}. The {@link Position}s on this {@link Board}
 * are ordered based on the ordering defined in the {@link Position} class.
 *
 * @param <P> The type of piece to be put on this {@link Board}.
 */
public class MapBoard<P extends Piece> implements Board<P> {

    private static final String INVALID_BOARD_POSITION_MESSAGE = "Invalid board position";
    private final Map<Position, Cell<P>> cells = new TreeMap<>();
    private final int boardSize;

    /**
     * Creates a new {@link Board} instance with a {@link TreeMap} implementation.
     * This implementation allows only square {@link Board}s, with the maximum and minimum size limits specified by the
     * {@link Board#MIN_BOARD_SIZE} and {@link Board#MAX_BOARD_SIZE} fields.
     *
     * @param boardSize The number of rows and columns on the {@link Board}
     * @throws InvalidBoardSizeException In case the {@link Board} sizes aren't matching,
     *                                   or they're outside the allowed range of [2, 26]
     */
    public MapBoard(int boardSize) throws InvalidBoardSizeException {
        if (!isBoardSizeValid(boardSize)) {
            throw new InvalidBoardSizeException(
                    "The size of the board must be at least " + Board.MIN_BOARD_SIZE + "x" + Board.MIN_BOARD_SIZE
                            + " and at most " + Board.MAX_BOARD_SIZE + "x" + Board.MAX_BOARD_SIZE);
        }
        this.boardSize = boardSize;
        initBoardWithEmptyCells();
    }

    private void initBoardWithEmptyCells() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells.put(Position.fromCoordinates(i, j), new Cell<>());
            }
        }
    }

    @Override
    public void clearCell(@NotNull Position position) {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
        cell.clear();
    }

    @Override
    public void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
        cell.putPiece(piece);
    }

    @Override
    @Nullable
    public P getPiece(@NotNull Position position) throws InvalidPositionException {
        Cell<P> cell = cells.get(position);
        if (cell == null) throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
        return cell.getPiece();
    }

    /**
     * Generates a {@link String} representation of the {@link Board}. The {@link Board} is displayed here as a chess board,
     * with letters indicating columns and numbers indicating rows. Free cells are marked with a - character,
     * while cells occupied are displayed with {@link Object#toString()}. In order to properly visualize
     * the {@link Board}, the {@link Piece}s used must have a single character string representation by overriding
     * the {@link Object#toString()}.
     *
     * @return The {@link Board}'s {@link String} representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = boardSize - 1; i >= 0; i--) {
            for (int j = 0; j < boardSize; j++) {
                Position currentPosition = Position.fromCoordinates(i, j);
                if (isFirstColumn(j)) {
                    sb.append(currentRowLabel(i));
                }
                if (isCellOccupied(currentPosition)) {
                    sb.append(getPiece(currentPosition));
                } else {
                    sb.append("-");
                }
                if (isNotLastColumn(j)) {
                    sb.append("  ");
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append(columnLabels());
        return sb.toString();
    }

    private @NotNull String columnLabels() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        IntStream.range(0, boardSize).forEach(columnIndex -> sb.append("  ").append((char) ('A' + columnIndex)));
        return sb.toString();
    }

    private boolean isNotLastColumn(int j) {
        return j < boardSize - 1;
    }

    private static @NotNull String currentRowLabel(int rowIndex) {
        StringBuilder sb = new StringBuilder();
        if (hasRowNumberOnlyOneDigit(rowIndex + 1)) {
            sb.append(" ").append(rowIndex + 1).append(" ");
        } else {
            sb.append(rowIndex + 1).append(" ");
        }
        return sb.toString();
    }

    private static boolean hasRowNumberOnlyOneDigit(int rowNumber) {
        return rowNumber < 10;
    }

    private static boolean isFirstColumn(int j) {
        return j == 0;
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
