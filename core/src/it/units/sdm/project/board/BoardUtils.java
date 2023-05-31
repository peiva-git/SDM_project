package it.units.sdm.project.board;

import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BoardUtils {

    public static long getNumberOfFreeCells(@NotNull Board<? extends Stone> board) {
        return board.getPositions().stream()
                .filter(position -> !board.isCellOccupied(position))
                .count();
    }

    public static boolean areAdjacentCellsOccupied(@NotNull Board<? extends Stone> board, @NotNull Position position) throws InvalidPositionException {
        return getAdjacentPositions(board, position).stream()
                .allMatch(board::isCellOccupied);
    }
    public static @NotNull Set<Position> getAdjacentPositions(@NotNull Board<? extends Stone> board, @NotNull Position position) throws InvalidPositionException {
        Set<Position> adjacentPositions = new HashSet<>(8);
        if(!checkPosition(board, position)) throw new InvalidPositionException("Invalid board position!");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if(i == 0 && j == 0) continue;
                    if (checkPosition(board, Position.fromCoordinates(position.getRow() + i,position.getColumn() + j))) {
                        adjacentPositions.add(Position.fromCoordinates(position.getRow() + i, position.getColumn() + j));
                    }
                } catch (InvalidPositionException ignored) {
                }
            }
        }
        return adjacentPositions;
    }

    private static boolean checkPosition(@NotNull Board<? extends Stone> board, @NotNull Position position) {
        try{
            board.getPiece(position);
            return true;
        } catch (InvalidPositionException exception) {
            return false;
        }
    }

}
