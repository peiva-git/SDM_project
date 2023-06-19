package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * This interface represents a generic game board. It may be used outside the scope of the freedom game as well.
 *
 * @param <P> The type of the piece to be put on this {@link Board}
 */
public interface Board<P extends Piece> {
    /**
     * Removes the piece {@link P} at the given {@link Position}
     *
     * @param position The {@link Position} to clear
     * @throws InvalidPositionException In case the {@link Position} is outside of {@link Board} bounds
     */
    void clearCell(@NotNull Position position) throws InvalidPositionException;

    /**
     * Puts a new piece {@link P} on this {@link Board}
     *
     * @param piece    The new piece {@link P} to be put on this {@link Board}
     * @param position The {@link Position} where the piece {@link P} will be placed
     * @throws InvalidPositionException In case the {@link Position} is outside of {@link Board} bounds, or already occupied
     */
    void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException;

    /**
     * Gets the piece {@link P} from the chosen position {@link Position}, if any
     *
     * @param position The {@link Position} where to get the piece from
     * @return The piece {@link P} at the specified {@link Position}, or {@code null} if empty
     * @throws InvalidPositionException In case the {@link Position} is outside of {@link Board} bounds
     */
    @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException;

    /**
     * Returns all the {@link Position}s on this {@link Board}
     *
     * @return All the possible {@link Board} {@link Position}s
     */
    @NotNull Set<Position> getPositions();

    /**
     * Checks whether the chosen {@link Position} is occupied
     *
     * @param position The chosen {@link Position}
     * @return {@code true} if the {@link Position}'s occupied, {@code false} otherwise
     * @throws InvalidPositionException In the case the {@link Position} is outside of {@link Board} bounds
     */
    default boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        return getPiece(position) != null;
    }

    /**
     * Removes all the pieces {@link P} from the {@link Board}
     */
    @SuppressWarnings("NewApi")
    default void clearBoard() {
        getPositions().forEach(this::clearCell);
    }

    /**
     * Returns the number of unoccupied cells
     *
     * @return The number of unoccupied cells
     */
    default long getNumberOfFreeCells() {
        return getPositions().stream()
                .filter(position -> !isCellOccupied(position))
                .count();
    }


    /**
     * Checks whether the {@link Position}s adjacent to the chosen {@link Position} are occupied
     *
     * @param position The chosen {@link Position}
     * @return {@code true} if they're all occupied, {@code false} otherwise
     * @throws InvalidPositionException In the case the chosen {@link Position} is outside of {@link Board} bounds
     */
    default boolean areAdjacentCellsOccupied(@NotNull Position position) throws InvalidPositionException {
        return getAdjacentPositions(position).stream()
                .allMatch(this::isCellOccupied);
    }

    /**
     * Gets the {@link Position}s which are adjacent to the chosen {@link Position}
     *
     * @param position The chosen {@link Position}
     * @return A {@link Set} of all the adjacent {@link Position}s
     * @throws InvalidPositionException In the case the chosen {@link Position} is out of {@link Board} bounds
     */
    default @NotNull Set<Position> getAdjacentPositions(@NotNull Position position) throws InvalidPositionException {
        Set<Position> adjacentPositions = new HashSet<>(8);
        if (!isPositionValidForTheBoard(position)) throw new InvalidPositionException("Invalid board position!");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    Position adjacentPosition = Position.fromCoordinates(position.getRow() + i, position.getColumn() + j);
                    if (adjacentPosition.equals(position)) continue;
                    if (isPositionValidForTheBoard(adjacentPosition)) adjacentPositions.add(adjacentPosition);
                } catch (InvalidPositionException ignored) {
                    // The current adjacent position is placed outside the board
                }
            }
        }
        return adjacentPositions;
    }

    /**
     * Checks whether a {@link Position} is inside of {@link Board} bounds and unoccupied
     *
     * @param position The chosen {@link Position}
     * @return {@code true} if the {@link Position} meets the above-mentioned criteria, {@code false} otherwise
     */
    private boolean isPositionValidForTheBoard(@NotNull Position position) {
        try {
            getPiece(position);
            return true;
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


}
