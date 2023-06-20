package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class represents an implementation of the {@link Board} interface using a {@link TreeMap} to hold
 * information about the pieces {@link P} that are on this {@link Board}. The {@link Position}s on this {@link Board}
 * are ordered based on the ordering defined in the {@link Position} class.
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
     * @param boardSize The number of rows and columns on the {@link Board}
     * @throws InvalidBoardSizeException In case the {@link Board} sizes aren't matching,
     * or they're outside the allowed range of [2, 26]
     */
    public MapBoard(int boardSize) throws InvalidBoardSizeException {
        if (!isBoardSizeValid(boardSize, boardSize)) {
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
     * while cells occupied by the white {@link it.units.sdm.project.game.Player} are marked with a W
     * and cells occupied by the black {@link it.units.sdm.project.game.Player} are marked with a B.
     * @return The {@link Board}'s {@link String} representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = boardSize; i > 0; i--) {
            for (int j = 1; j <= boardSize; j++) {
                if (j == 1) {
                    if (i < 10) {
                        sb.append(" ").append(i).append(" ");
                    } else {
                        sb.append(i).append(" ");
                    }
                }
                if (isCellOccupied(Position.fromCoordinates(i - 1, j - 1))) {
                    sb.append(getPiece(Position.fromCoordinates(i - 1, j - 1)));
                } else {
                    sb.append("-");
                }
                if (j < boardSize) {
                    sb.append("  ");
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append(" ");
        for (int j = 0; j < boardSize; j++) {
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
