import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FreedomPointsCounter {

    private enum Direction {HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT}

    @NotNull
    private final Board board;
    @NotNull
    private int whitePlayerScore = 0;
    @NotNull
    private int blackPlayerScore = 0;

    public FreedomPointsCounter(@NotNull Board board) {
        this.board = board;
    }

    public int getWhitePlayerScore() {
        return whitePlayerScore;
    }

    public int getBlackPlayerScore() {
        return blackPlayerScore;
    }

    @NotNull
    public void count() {
        for (Map.Entry<Position, Cell> entry : board) {
            Position currentPosition = entry.getKey();
            checkFreedomLineFrom(currentPosition);
        }
    }

    private void checkFreedomLineFrom(@NotNull Position position) {
        if (!board.isCellOccupied(position)) return;
        Stone.Color playerColor = board.getStone(position).getColor();

        if (countStonesOfTheSameColor(position, 1, Direction.HORIZONTAL) == 4) {
            incrementScoreOf(playerColor);
        }
        if (countStonesOfTheSameColor(position, 1, Direction.VERTICAL) == 4) {
            incrementScoreOf(playerColor);
        }
        if (countStonesOfTheSameColor(position, 1, Direction.DIAGONAL_LEFT) == 4) {
            incrementScoreOf(playerColor);
        }
        if (countStonesOfTheSameColor(position, 1, Direction.DIAGONAL_RIGHT) == 4) {
            incrementScoreOf(playerColor);
        }

    }

    private void incrementScoreOf(Stone.Color playerColor) {
        if (playerColor == Stone.Color.WHITE) {
            whitePlayerScore = whitePlayerScore + 1;
        } else {
            blackPlayerScore = blackPlayerScore +1;
        }
    }

    private int countStonesOfTheSameColor(@NotNull Position currentPosition, int currentStoneCount, Direction direction) {
        Position nextPosition;
        if (currentStoneCount == 1) {
            try {
                if (getThePreviousStoneColor(currentPosition, direction) == board.getStone(currentPosition).getColor()) {
                    return currentStoneCount;
                }
            } catch (InvalidPositionException ignored) {

            }
        }
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
            if (nextStone != null && board.getStone(currentPosition).getColor() == nextStone.getColor()) {
                return countStonesOfTheSameColor(nextPosition, currentStoneCount + 1, direction);
            } else {
                return currentStoneCount;
            }
        } catch (InvalidPositionException exception) {
            return currentStoneCount;
        }
    }

    private Stone.Color getThePreviousStoneColor(Position currentPosition, Direction direction) throws InvalidPositionException {
        switch (direction) {
            case HORIZONTAL:
                return board.getStone(new Position(currentPosition.getRow(), currentPosition.getColumn() - 1)).getColor();
            case VERTICAL:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn())).getColor();
            case DIAGONAL_LEFT:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getColor();
            default:
                return board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getColor();
        }
    }


}
