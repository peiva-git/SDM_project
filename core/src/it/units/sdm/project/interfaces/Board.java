package it.units.sdm.project.interfaces;

import it.units.sdm.project.core.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface Board<P> extends Iterable<Position> {
    int getNumberOfRows();
    int getNumberOfColumns();
    boolean isCellOccupied(@NotNull Position position);
    void clearCell(@NotNull Position position);
    void clearBoard();
    void putPiece(@NotNull P piece,@NotNull  Position position);
    @Nullable P getPiece(@NotNull Position position);
    @NotNull Set<Position> getPositions();

    interface Cell<P> {
        void putPiece(P piece);
        P getPiece();
        boolean isOccupied();
        void clear();
    }

}
