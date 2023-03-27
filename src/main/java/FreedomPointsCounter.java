import exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FreedomPointsCounter {

    private enum Direction {HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT}
    private final static int MAX_NUMBER_OF_STONES = 4;
    @NotNull
    private Board board;
    private int whitePlayerScore = 0;
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

    public void setBoard(@NotNull Board board) {
        this.board = board;
    }

    public void count() {
        this.blackPlayerScore = 0;
        this.whitePlayerScore = 0;
        for (Map.Entry<Position, Cell> entry : board) {
            Position currentPosition = entry.getKey();
            checkFreedomLineFrom(currentPosition);
        }
    }

    private void checkFreedomLineFrom(@NotNull Position position) {

        Stone stone = board.getStone(position);
        if(stone == null) return;
        Stone.Color playerColor = stone.getColor();

        int currentStoneCount = 1;
        if (countStonesOfTheSameColorFrom(position, currentStoneCount, Direction.HORIZONTAL) == MAX_NUMBER_OF_STONES) {
            incrementScoreOf(playerColor);
        } else if (countStonesOfTheSameColorFrom(position, currentStoneCount, Direction.VERTICAL) == MAX_NUMBER_OF_STONES) {
            incrementScoreOf(playerColor);
        } else if (countStonesOfTheSameColorFrom(position, currentStoneCount, Direction.DIAGONAL_LEFT) == MAX_NUMBER_OF_STONES) {
            incrementScoreOf(playerColor);
        } else if (countStonesOfTheSameColorFrom(position, currentStoneCount, Direction.DIAGONAL_RIGHT) == MAX_NUMBER_OF_STONES) {
            incrementScoreOf(playerColor);
        }

    }

    private void incrementScoreOf(@NotNull Stone.Color playerColor) {
        if (playerColor == Stone.Color.WHITE) {
            whitePlayerScore = whitePlayerScore + 1;
        } else {
            blackPlayerScore = blackPlayerScore + 1;
        }
    }

    private int countStonesOfTheSameColorFrom(@NotNull Position currentPosition, int currentStoneCount, @NotNull Direction direction) {
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
                return countStonesOfTheSameColorFrom(nextPosition, currentStoneCount + 1, direction);
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
