package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


public interface Board<P> {
    void clearCell(@NotNull Position position) throws InvalidPositionException;

    void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException;

    @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException;

    @NotNull Set<Position> getPositions();

    default boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        return getPiece(position) != null;
    }

    default void clearBoard() {
        getPositions().forEach(this::clearCell);
    }

    default long getNumberOfFreeCells() {
        return getPositions().stream()
                .filter(position -> !isCellOccupied(position))
                .count();
    }

    default boolean areAdjacentCellsOccupied(@NotNull Position position) throws InvalidPositionException {
        return getAdjacentPositions(position).stream()
                .allMatch(this::isCellOccupied);
    }

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

    default boolean isBoardPositionValid(@NotNull Position position) {
        try {
            getPiece(position);
            return true;
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


}
