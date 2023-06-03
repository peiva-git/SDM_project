package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.game.FreedomLine.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class counts {@link Player} points in a Freedom {@link BoardGame}. Every stone line
 * of the same color, which size is 4, is a point.
 */
public class FreedomPointsCounter {
    private final static int MAX_NUMBER_OF_STONES = 4;
    @NotNull
    private final Board<? extends Stone> board;
    private final Set<FreedomLine> blackFreedomLines = new HashSet<>();
    private final Set<FreedomLine> whiteFreedomLines = new HashSet<>();

    /**
     * Creates an instance of {@link FreedomPointsCounter}
     * @param board Board on which counting Freedom {@link Player} points
     */
    public FreedomPointsCounter(@NotNull Board<? extends Stone> board) {
        this.board = board;
    }

    /**
     * Gets the {@link Player} score
     * @param player Freedom player
     * @return The player's score
     */
    public int getPlayerScore(@NotNull Player player) {
        return getPlayerScore(player.getColor());
    }

    /**
     * Gets the {@link Player} score
     * @param color Freedom player's color, it can be either white or black
     * @return The player's color
     */
    public int getPlayerScore(@NotNull Color color) {
        if (color == Color.WHITE) {
            this.whiteFreedomLines.clear();
            count(color);
            return whiteFreedomLines.size();
        } else {
            this.blackFreedomLines.clear();
            count(color);
            return blackFreedomLines.size();
        }
    }

    /**
     * The method count() counts all the freedom lines on the board.
     */
    private void count(@NotNull Color color) {
        board.getPositions().stream()
                .filter(position -> {
                    Stone stone = board.getPiece(position);
                    return !(stone == null || stone.getColor() != color);
                })
                .forEach(this::checkAllFreedomLinesFromPosition);
    }

    /**
     * This method finds freedom lines with size equal to 4 to all the directions
     * from a starting position
     *
     * @param position starting position
     */
    private void checkAllFreedomLinesFromPosition(@NotNull Position position) {
        checkFreedomLine(position, Direction.HORIZONTAL);
        checkFreedomLine(position, Direction.VERTICAL);
        checkFreedomLine(position, Direction.DIAGONAL_LEFT);
        checkFreedomLine(position, Direction.DIAGONAL_RIGHT);
    }

    /**
     * This method finds freedom line with size equal to 4 by considering a specific direction from a starting position.
     * It also checks whether the obtained freedom line is part of a bigger freedom line by checking the color of
     * the stone that comes before the stone on the startingPosition with respect to the direction of the lines.
     *
     * @param startingPosition starting position
     * @param direction        direction in which it counts
     */
    private void checkFreedomLine(@NotNull Position startingPosition, @NotNull Direction direction) {
        Stone startingStone = Objects.requireNonNull(board.getPiece(startingPosition), "Should be not-null, checked by count method");
        Color stoneColor = startingStone.getColor();
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

    public boolean isPartOfABiggerFreedomLine(@NotNull FreedomLine freedomLine, @NotNull Direction direction) {
        return hasThePreviousStoneTheSameColor(freedomLine.first(), direction);
    }

    private boolean hasThePreviousStoneTheSameColor(@NotNull Position position, @NotNull Direction direction) {
        try {
            Stone currentStone = board.getPiece(position);
            Stone previousStone = getThePreviousStone(position, direction);
            if (previousStone == null || currentStone == null) return false;
            return previousStone.getColor() == currentStone.getColor();
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


    @Nullable
    private Stone getThePreviousStone(@NotNull Position currentPosition, @NotNull Direction direction) throws InvalidPositionException {
        switch (direction) {
            case HORIZONTAL:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow(), currentPosition.getColumn() - 1));
            case VERTICAL:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn()));
            case DIAGONAL_LEFT:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn() + 1));
            default:
                return board.getPiece(Position.fromCoordinates(currentPosition.getRow() - 1, currentPosition.getColumn() - 1));
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
