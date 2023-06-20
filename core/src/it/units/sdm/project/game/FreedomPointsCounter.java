package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.game.FreedomLine.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class counts {@link Player} points in a Freedom {@link BoardGame}. Every non-overlapping {@link FreedomLine}
 * with a size of 4 represents a point.
 */
public class FreedomPointsCounter {
    private static final int MAX_NUMBER_OF_STONES = 4;
    @NotNull
    private final Board<? extends Piece> board;
    private final Set<FreedomLine> blackFreedomLines = new HashSet<>();
    private final Set<FreedomLine> whiteFreedomLines = new HashSet<>();

    /**
     * Creates an instance of a {@link FreedomPointsCounter}
     * @param board {@link Board} on which to count a {@link Player}'s points
     */
    public FreedomPointsCounter(@NotNull Board<? extends Piece> board) {
        this.board = board;
    }

    /**
     * Gets the {@link Player}'s score
     * @param player Freedom {@link Player}
     * @return The {@link Player}'s score
     */
    public int getPlayerScore(@NotNull Player player) {
        return getPlayerScore(player.getColor());
    }

    /**
     * Gets the {@link Player}'s score
     * @param color Freedom {@link Player}'s {@link Color}, it can be either {@link Color#WHITE} or {@link Color#BLACK}
     * @return The {@link Player}'s score
     */
    public int getPlayerScore(@NotNull Color color) throws IllegalArgumentException {
        if (color == Color.WHITE) {
            this.whiteFreedomLines.clear();
            count(color);
            return whiteFreedomLines.size();
        } else if (color == Color.BLACK){
            this.blackFreedomLines.clear();
            count(color);
            return blackFreedomLines.size();
        }
        throw new IllegalArgumentException("Invalid player color, can be either black or white");
    }

    /**
     * Counts all the {@link FreedomLine}s on the {@link Board}.
     */
    private void count(@NotNull Color color) {
        board.getPositions().stream()
                .filter(position -> {
                    Piece stone = board.getPiece(position);
                    return !(stone == null || stone.getPieceColor() != color);
                })
                .forEach(this::checkAllFreedomLinesFromPosition);
    }

    /**
     * Finds all {@link FreedomLine}s with size equal to 4 considering all {@link Direction}s
     * from a starting {@link Position}.
     *
     * @param position starting {@link Position}
     */
    private void checkAllFreedomLinesFromPosition(@NotNull Position position) {
        checkFreedomLine(position, Direction.HORIZONTAL);
        checkFreedomLine(position, Direction.VERTICAL);
        checkFreedomLine(position, Direction.DIAGONAL_LEFT);
        checkFreedomLine(position, Direction.DIAGONAL_RIGHT);
    }

    /**
     * This method finds {@link FreedomLine}s with size equal to 4 by considering a specific {@link Direction} and a starting {@link Position}.
     * It also checks whether the obtained {@link FreedomLine} is part of a bigger {@link FreedomLine} by checking the {@link Color} of
     * the {@link Piece} that comes before the {@link Piece} on the {@code startingPosition} with respect to the {@link Direction} direction of the {@link FreedomLine}s.
     *
     * @param startingPosition The starting {@link Position} from which to begin counting
     * @param direction The {@link Direction} in which this method starts counting
     */
    private void checkFreedomLine(@NotNull Position startingPosition, @NotNull Direction direction) {
        Piece startingStone = Objects.requireNonNull(board.getPiece(startingPosition), "Should be not-null, checked by count method");
        Color stoneColor = startingStone.getPieceColor();
        FreedomLine line = getLineOfTheSameColorFrom(new FreedomLine(board, startingPosition), direction);
        if (line.size() == MAX_NUMBER_OF_STONES && !isPartOfABiggerFreedomLine(line, direction)) {
            addFreedomLineTo(stoneColor, line);
        }
    }

    @NotNull
    private FreedomLine getLineOfTheSameColorFrom(@NotNull FreedomLine tempLine, @NotNull Direction direction) {

        Position nextPosition;
        Position currentPosition = tempLine.last();
        try {
            switch (direction) {
                case HORIZONTAL:
                    nextPosition = Position.fromCoordinates(currentPosition.getRow(), currentPosition.getColumn() + 1);
                    break;
                case VERTICAL:
                    nextPosition = Position.fromCoordinates(currentPosition.getRow() + 1, currentPosition.getColumn());
                    break;
                case DIAGONAL_LEFT:
                    nextPosition = Position.fromCoordinates(currentPosition.getRow() + 1, currentPosition.getColumn() - 1);
                    break;
                default:
                    nextPosition = Position.fromCoordinates(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
            }
            tempLine.addPosition(nextPosition);
            return getLineOfTheSameColorFrom(tempLine, direction);
        } catch (InvalidPositionException exception) {
            return tempLine;
        }
    }

    private boolean isPartOfABiggerFreedomLine(@NotNull FreedomLine freedomLine, @NotNull Direction direction) {
        return hasThePreviousStoneTheSameColor(freedomLine.first(), direction);
    }

    private boolean hasThePreviousStoneTheSameColor(@NotNull Position position, @NotNull Direction direction) {
        try {
            Piece currentStone = board.getPiece(position);
            Piece previousStone = getThePreviousStone(position, direction);
            if (previousStone == null) return false;
            return previousStone.getPieceColor() == Objects.requireNonNull(currentStone, "currentStone belongs to a FreedomLine").getPieceColor();
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


    @Nullable
    private Piece getThePreviousStone(@NotNull Position currentPosition, @NotNull Direction direction) throws InvalidPositionException {
        switch (direction) {
            case HORIZONTAL:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow(), currentPosition.getColumn() - 1));
            case VERTICAL:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn()));
            case DIAGONAL_LEFT:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn() - 1));
            default:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn() + 1));
        }
    }

    private void addFreedomLineTo(@NotNull Color playerColor, @NotNull FreedomLine freedomLine) {
        if (playerColor == Color.WHITE) {
            whiteFreedomLines.add(freedomLine);
        } else {
            blackFreedomLines.add(freedomLine);
        }
    }


}
