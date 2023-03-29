import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FreedomPointsCounter {

    private enum Direction {HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT}

    private final static int MAX_NUMBER_OF_STONES = 4;
    @NotNull
    private Board board;
    private final Set<FreedomLine> blackFreedomLines = new HashSet<>();
    private final Set<FreedomLine> whiteFreedomLines = new HashSet<>();

    public FreedomPointsCounter(@NotNull Board board) {
        this.board = board;
    }

    public int getWhitePlayerScore() {
        return whiteFreedomLines.size();
    }

    public int getBlackPlayerScore() {
        return blackFreedomLines.size();
    }

    public void setBoard(@NotNull Board board) {
        this.board = board;
    }

    public void count() {
        this.blackFreedomLines.clear();
        this.whiteFreedomLines.clear();
        for (Position currentPosition : board) {
            if (board.getStone(currentPosition) == null) continue;
            checkAllFreedomLinesFrom(currentPosition);
        }
    }

    private void checkAllFreedomLinesFrom(@NotNull Position position) {
        checkFreedomLine(position, Direction.HORIZONTAL);
        checkFreedomLine(position, Direction.VERTICAL);
        checkFreedomLine(position, Direction.DIAGONAL_LEFT);
        checkFreedomLine(position, Direction.DIAGONAL_RIGHT);
    }

    private void checkFreedomLine(@NotNull Position startingPosition, @NotNull Direction direction) {
        Stone startingStone = Objects.requireNonNull(board.getStone(startingPosition), "Should be not-null, checked by count method");
        Stone.Color stoneColor = startingStone.getColor();
        FreedomLine line = getLineOfTheSameColorFrom(new FreedomLine(stoneColor, startingPosition), direction);
        if (line.size() == MAX_NUMBER_OF_STONES && !isPartOfABiggerLine(line, direction)) {
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
                    nextPosition = new Position(currentPosition.getRow(), currentPosition.getColumn() + 1);
                    break;
                case VERTICAL:
                    nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn());
                    break;
                case DIAGONAL_LEFT:
                    nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn() - 1);
                    break;
                default:
                    nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
            }
            Stone nextStone = board.getStone(nextPosition);
            Stone currentStone = board.getStone(currentPosition);
            if (hasTheNextStoneTheSameColorOfTheCurrentStone(currentStone, nextStone)) {
                tempLine.addPosition(nextPosition);
                return getLineOfTheSameColorFrom(tempLine, direction);
            } else {
                return tempLine;
            }
        } catch (InvalidPositionException exception) {
            return tempLine;
        }
    }

    private boolean hasTheNextStoneTheSameColorOfTheCurrentStone(@NotNull Stone currentStone, @Nullable Stone nextStone) {
        return nextStone != null && currentStone.getColor() == nextStone.getColor();
    }

    public boolean isPartOfABiggerLine(@NotNull FreedomLine freedomLine, @NotNull Direction direction) {
        return hasThePreviousStoneTheSameColor(freedomLine.first(), direction);
    }

    private boolean hasThePreviousStoneTheSameColor(@NotNull Position position, @NotNull Direction direction) {
        try {
            Stone previousStone = getThePreviousStone(position, direction);
            if (previousStone == null) return false;
            return previousStone.getColor() == board.getStone(position).getColor();
        } catch (InvalidPositionException exception) {
            return false;
        }
    }


    @Nullable
    private Stone getThePreviousStone(@NotNull Position currentPosition, @NotNull Direction direction) throws InvalidPositionException {
        switch (direction) {
            case HORIZONTAL:
                return board.getStone(new Position(currentPosition.getRow(), currentPosition.getColumn() - 1));
            case VERTICAL:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn()));
            case DIAGONAL_LEFT:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() + 1));
            default:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() - 1));
        }
    }

    private void addFreedomLineTo(@NotNull Stone.Color playerColor, @NotNull FreedomLine freedomLine) {
        if (playerColor == Stone.Color.WHITE) {
            whiteFreedomLines.add(freedomLine);
        } else {
            blackFreedomLines.add(freedomLine);
        }
    }


}
