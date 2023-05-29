package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface Board<P> {
    boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException;
    void clearCell(@NotNull Position position);
    void clearBoard();
    void putPiece(@NotNull P piece,@NotNull  Position position);
    @Nullable P getPiece(@NotNull Position position);
    @NotNull Set<Position> getPositions();
    boolean hasBoardMoreThanOneFreeCell();
    boolean areAdjacentCellsOccupied(Position position);
    @NotNull Set<Position> getAdjacentPositions(Position position);
}
