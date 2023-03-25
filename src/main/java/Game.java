import exceptions.InvalidPositionException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Game {

    private enum GameStatus {NOT_STARTED, STARTED, FREEDOM, NO_FREEDOM, LAST_MOVE, GAME_OVER}

    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final Board board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    private final LinkedList<Move> allPlayersMoves = new LinkedList<>();
    private final TextInput userInput = new TextInput();

    private final Score whiteScore;

    private final Score blackScore;

    public Game(@NotNull Board board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        this.whiteScore = new Score(this.whitePlayer);
        this.blackScore = new Score(this.blackPlayer);
    }

    public void start() {
        System.out.println("Game starting up, clearing board...");
        // board.clearBoard();
        gameStatus = GameStatus.STARTED;
        while (gameStatus != GameStatus.GAME_OVER) {
            turn();
        }
        System.out.println("The winner is: " + getTheWinner());
        end();
    }

    private void end() {
        userInput.close();
    }

    public void turn() {
        Player currentPlayer = nextPlayer();
        Position chosenPosition = null;
        System.out.println(board);
        switch (getCurrentGameStatus()) {
            case FREEDOM:
                chosenPosition = getPositionWithFreedom(currentPlayer);
                board.putStone(chosenPosition, currentPlayer.getColor());
                break;
            case NO_FREEDOM:
                chosenPosition = getPositionWithNoFreedom(currentPlayer);
                board.putStone(chosenPosition, currentPlayer.getColor());
                break;
            case LAST_MOVE:
                chosenPosition = playLastMove(currentPlayer);
                if (chosenPosition != null) {
                    board.putStone(chosenPosition, currentPlayer.getColor());
                }
                gameStatus = GameStatus.GAME_OVER;
                break;
        }
        allPlayersMoves.add(new Move(currentPlayer, chosenPosition));
    }

    @NotNull
    private Player nextPlayer() {
        try {
            Player previousPlayer = allPlayersMoves.getLast().getPlayer();
            if (previousPlayer.equals(whitePlayer)) return blackPlayer;
            return whitePlayer;
        } catch (NoSuchElementException exception) {
            return this.whitePlayer;
        }
    }

    private GameStatus getCurrentGameStatus() {
        if (board.hasBoardMoreThanOneFreeCell()) {
            if (allPlayersMoves.isEmpty() || board.areAdjacentCellsOccupied(allPlayersMoves.getLast().getPosition())) {
                return GameStatus.FREEDOM;
            } else {
                return GameStatus.NO_FREEDOM;
            }
        } else {
            return GameStatus.LAST_MOVE;
        }
    }

    @Contract("_ -> new")
    private @NotNull Position getPositionWithFreedom(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Freeedom! You can place a stone on any unoccupied cell");
        return getPositionFromUser();
    }

    @Contract("_ -> new")
    private @NotNull Position getPositionWithNoFreedom(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("You can place a stone near the last stone placed by the other player");
        Position lastPosition = allPlayersMoves.getLast().getPosition();
        Set<Position> adjacentPositions = board.getAdjacentPositions(lastPosition);
        System.out.print("Yuo can pick one of the following positions: ");
        adjacentPositions.stream().sorted().forEach(adjacentPosition -> {
            int row = adjacentPosition.getRow();
            char column = (char) ('A' + adjacentPosition.getColumn() - 1);
            System.out.print(column);
            System.out.print(row);
            System.out.print(" ");
        });
        System.out.println();
        return getPositionFromUserFromSuggestedSet(adjacentPositions);
    }

    private @Nullable Position playLastMove(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Last move! You can decide to either play or pass");
        System.out.print("Do you want to pass? (Yes/No): ");
        if (userInput.isLastMoveAPass()) {
            return userInput.getPosition();
        }
        return null;
    }

    private boolean isPositionInsideBoardRange(@NotNull Position chosenPosition) {
        return board.getNumberOfRows() >= chosenPosition.getRow() && board.getNumberOfColumns() >= chosenPosition.getColumn();
    }

    @NotNull
    private Position getPositionFromUser() {
        System.out.print("Insert the cell name, for example A5: ");
        while (true) {
            Position chosenPosition = userInput.getPosition();
            if (isPositionInsideBoardRange(chosenPosition)) {
                if (board.isCellOccupied(chosenPosition)) {
                    System.out.print("The picked cell is already occupied! Pick again: ");
                } else {
                    return chosenPosition;
                }
            } else {
                System.out.print("The specified cell is outside of the board range! Pick again: ");
            }
        }
    }

    @Contract("_ -> new")
    private @NotNull Position getPositionFromUserFromSuggestedSet(@NotNull Set<Position> suggestedPositions) {
        System.out.print("Insert the cell name: ");
        while (true) {
            Position chosenPosition = userInput.getPosition();
            if (isPositionInsideBoardRange(chosenPosition)) {
                if (suggestedPositions.contains(chosenPosition)) {
                    if (!board.isCellOccupied(chosenPosition)) {
                        return chosenPosition;
                    } else {
                        System.out.print("The picked cell is already occupied! Pick again: ");
                    }
                } else {
                    System.out.print("The specified cell is not adjacent to the last occupied one! Pick again: ");
                }
            } else {
                System.out.print("The specified cell is outside of the board range! Pick again: ");
            }
        }
    }

    @NotNull
    private Player getTheWinner() {
        for (Map.Entry<Position, Cell> entry : board) {
            Position currentPosition = entry.getKey();
            checkFreedomLine(currentPosition);
        }
        if(blackScore.getNumberOfFreedomLines() > whiteScore.getNumberOfFreedomLines()) {
            return blackPlayer;
        }
        return whitePlayer;
    }


    private void checkFreedomLine(@NotNull Position position) {
        Stone.Color color = board.getStone(position).getColor();
        if (countHorizontally(position, 1) == 4) {
            if(color == Stone.Color.BLACK) {
                blackScore.incrementNumberOfFreedomLines();
            } else {
                whiteScore.incrementNumberOfFreedomLines();
            }
        }
        if (countVertically(position, 1) == 4) {
            if(color == Stone.Color.BLACK) {
                blackScore.incrementNumberOfFreedomLines();
            } else {
                whiteScore.incrementNumberOfFreedomLines();
            }
        }
        if (countDiagonallyRight(position, 1) == 4) {
            if(color == Stone.Color.BLACK) {
                blackScore.incrementNumberOfFreedomLines();
            } else {
                whiteScore.incrementNumberOfFreedomLines();
            }
        }
        if (countDiagonallyLeft(position, 1) == 4) {
            if(color == Stone.Color.BLACK) {
                blackScore.incrementNumberOfFreedomLines();
            } else {
                whiteScore.incrementNumberOfFreedomLines();
            }
        }
    }

    private int countVertically(@NotNull Position currentPosition, int currentStoneCount) {
        if(currentStoneCount == 1) {
            try {
                if (board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn())).getColor() == board.getStone(currentPosition).getColor()) {
                    return currentStoneCount;
                }
            } catch (InvalidPositionException ignored) {
            }
        }

        try {
            Position nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn());
            if (board.getStone(currentPosition).getColor() == board.getStone(nextPosition).getColor()) {
                return countVertically(nextPosition, currentStoneCount + 1);
            } else {
                return currentStoneCount;
            }
        } catch (InvalidPositionException ignored) {
            return currentStoneCount;
        }
    }

    private int countHorizontally(@NotNull Position currentPosition, int currentStoneCount) {
        if(currentStoneCount == 1) {
            try {
                if (board.getStone(new Position(currentPosition.getRow(), currentPosition.getColumn() - 1)).getColor() == board.getStone(currentPosition).getColor()) {
                    return currentStoneCount;
                }
            } catch (InvalidPositionException ignored) {
            }
        }
        try {
            Position nextPosition = new Position(currentPosition.getRow(), currentPosition.getColumn() + 1);
            if (board.getStone(currentPosition).getColor() == board.getStone(nextPosition).getColor()) {
                return countHorizontally(nextPosition, currentStoneCount + 1);
            } else {
                return currentStoneCount;
            }
        } catch (InvalidPositionException ignored) {
            return currentStoneCount;
        }
    }

    private int countDiagonallyLeft(@NotNull Position currentPosition, int currentStoneCount) {
        if(currentStoneCount == 1) {
            try {
                if (board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getColor() == board.getStone(currentPosition).getColor()) {
                    return currentStoneCount;
                }
            } catch (InvalidPositionException ignored) {
            }
        }
        try {
            Position nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn() - 1);
            if (board.getStone(currentPosition).getColor() == board.getStone(nextPosition).getColor()) {
                return countDiagonallyLeft(nextPosition, currentStoneCount + 1);
            } else {
                return currentStoneCount;
            }
        } catch (InvalidPositionException ignored) {
            return currentStoneCount;
        }
    }

    private int countDiagonallyRight(@NotNull Position currentPosition, int currentStoneCount) {
        if(currentStoneCount == 1) {
            try {
                if (board.getStone(new Position(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getColor() == board.getStone(currentPosition).getColor()) {
                    return currentStoneCount;
                }
            } catch (InvalidPositionException ignored) {
            }
        }
        try {
            Position nextPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
            if (board.getStone(currentPosition).getColor() == board.getStone(nextPosition).getColor()) {
                return countDiagonallyRight(nextPosition, currentStoneCount + 1);
            } else {
                return currentStoneCount;
            }
        } catch (InvalidPositionException ignored) {
            return currentStoneCount;
        }
    }

}
