package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface Board<P> {
    void clearCell(@NotNull Position position) throws InvalidPositionException;
    void putPiece(@NotNull P piece,@NotNull  Position position) throws InvalidPositionException;
    @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException;
    @NotNull Set<Position> getPositions();
    default long getNumberOfFreeCells() {
        return getPositions().stream()
                .filter(position -> !isCellOccupied(position))
                .count();
    }
    default boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        return getPiece(position) != null;
    }

    default void clearBoard() {
        getPositions().forEach(this::clearCell);
    }

}
