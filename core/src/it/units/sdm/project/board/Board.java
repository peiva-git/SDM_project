package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface Board<P> {
    boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException;
    void clearCell(@NotNull Position position) throws InvalidPositionException;
    void clearBoard();
    void putPiece(@NotNull P piece,@NotNull  Position position) throws InvalidPositionException;
    @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException;
    @NotNull Set<Position> getPositions();

}
