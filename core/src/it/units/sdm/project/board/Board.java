package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public interface Board<P> {
    /**
     * Remove the piece at the given position
     * @param position The position to clear
     * @throws InvalidPositionException In case the position is outside of board bounds
     */

    void clearCell(@NotNull Position position) throws InvalidPositionException;
    /**
     * Puts a new piece on this board
     * @param piece The new piece to be put on this board
     * @param position The position where the piece will be placed
     * @throws InvalidPositionException In case the position is outside of board bounds, or already occupied
     */

    void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException;

    /**
     * Gets the piece from the chosen position, if any
     * @param position The position where to get the piece from
     * @return The piece at the specified position, or null if empty
     * @throws InvalidPositionException In case the position is outside of board bounds
     */
    @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException;

    /**
     * Returns all the positions on this board
     * @return All the possible board positions
     */
    @NotNull Set<Position> getPositions();

    /**
     * Checks whether the chosen position is occupied
     * @param position The chosen position
     * @return true if the position's occupied, false otherwise
     * @throws InvalidPositionException In the case the position is outside of board bounds
     */
    default boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        return getPiece(position) != null;
    }

    /**
     * Removes all the pieces from the board
     */
    default void clearBoard() {
        getPositions().forEach(this::clearCell);
    }

    /**
     * Returns the number of unoccupied cells
     * @return The number of unoccupied cells
     */
    default long getNumberOfFreeCells() {
        return getPositions().stream()
                .filter(position -> !isCellOccupied(position))
                .count();
    }

    default @NotNull Set<Position> getFreePositions() {
        return getPositions().stream()
                .filter(position -> !isCellOccupied(position))
                .collect(Collectors.toSet());
    }
  
    /**
     * Checks whether the positions adjacent to the chosen position are occupied
     * @param position The chosen position
     * @return true if they're all occupied, false otherwise
     * @throws InvalidPositionException In the case the chosen position is outside of board bounds
     */
    default boolean areAdjacentCellsOccupied(@NotNull Position position) throws InvalidPositionException {
        return getAdjacentPositions(position).stream()
                .allMatch(this::isCellOccupied);
    }

    /**
     * Gets the positions which are adjacent to the chosen position
     * @param position The chosen position
     * @return A set of all the adjacent positions
     * @throws InvalidPositionException In the case the chosen position is out of board bounds
     */
    default @NotNull Set<Position> getAdjacentPositions(@NotNull Position position) throws InvalidPositionException {
        Set<Position> adjacentPositions = new HashSet<>(8);
        if (!isBoardPositionValid(position)) throw new InvalidPositionException("Invalid board position!");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (i == 0 && j == 0) continue;
                    if (isBoardPositionValid(Position.fromCoordinates(position.getRow() + i, position.getColumn() + j))) {
                        adjacentPositions.add(Position.fromCoordinates(position.getRow() + i, position.getColumn() + j));
                    }
                } catch (InvalidPositionException ignored) {
                }
            }
        }
        return adjacentPositions;
    }

    /**
     * Checks whether a position is inside of board bounds and unoccupied
     * @param position The chosen position
     * @return true id the position meets the above-mentioned criteria, false otherwise
     */
    default boolean isBoardPositionValid(@NotNull Position position) {
        try {
            getPiece(position);
            return true;
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


}
