package it.units.sdm.project.interfaces;

import it.units.sdm.project.core.board.Position;
import org.jetbrains.annotations.NotNull;


public interface Board<P> extends Iterable<Position> {
    int getNumberOfRows();
    int getNumberOfColumns();
    boolean isCellOccupied(@NotNull Position position);
    void clearCell(@NotNull Position position);
    void clearBoard();
    void putPiece(P piece, Position position);
    P getPiece(Position position);

    interface Cell<P> {
        void putPiece(P piece);
        P getPiece();
        boolean isOccupied();
        void clear();
    }

}
