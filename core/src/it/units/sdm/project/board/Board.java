package it.units.sdm.project.board;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface Board<P> {
    int getNumberOfRows();
    int getNumberOfColumns();
    boolean isCellOccupied(@NotNull Position position);
    void clearCell(@NotNull Position position);
    void clearBoard();
    void putPiece(@NotNull P piece,@NotNull  Position position);
    @Nullable P getPiece(@NotNull Position position);
    @NotNull Set<Position> getPositions();
    boolean hasBoardMoreThanOneFreeCell();
    boolean areAdjacentCellsOccupied(Position position);
    @NotNull Set<Position> getAdjacentPositions(Position position);
}
