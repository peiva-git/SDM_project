import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
    private final Scanner userInput = new Scanner(System.in);

    private final MainLogger logger;

    public Game(@NotNull Board board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        try {
            logger = new MainLogger("debug.log");
        } catch (IOException e) {
            System.out.println("Unable to start logger: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void start() {
        System.out.println("Game starting up, clearing board...");
        board.clearBoard();
        gameStatus = GameStatus.STARTED;
        while (gameStatus != GameStatus.GAME_OVER) {
            turn();
        }
        end();
    }

    public void turn() {
        Player currentPlayer = nextPlayer();
        Position chosenPosition = null;
        printBoardStatus();
        switch (getCurrentGameStatus()) {
            case FREEDOM:
                chosenPosition = getPositionWithFreedom(currentPlayer);
                board.getCell(chosenPosition).putStone(new Stone(currentPlayer.getColor()));
                break;
            case NO_FREEDOM:
                chosenPosition = getPositionWithNoFreedom(currentPlayer);
                board.getCell(chosenPosition).putStone(new Stone(currentPlayer.getColor()));
                break;
            case LAST_MOVE:
                chosenPosition = playLastMove(currentPlayer);
                if (chosenPosition != null) {
                    board.getCell(chosenPosition).putStone(new Stone(currentPlayer.getColor()));
                }
                gameStatus = GameStatus.GAME_OVER;
                break;
        }
        allPlayersMoves.add(new Move(currentPlayer, chosenPosition));
    }

    private void printBoardStatus() {
        System.out.println(
                  "8 -  -  -  -  -  -  -  -\n"
                + "7 -  -  -  -  -  -  -  -\n"
                + "6 -  -  -  -  -  -  -  -\n"
                + "5 -  -  -  -  -  -  -  -\n"
                + "4 -  -  -  -  -  -  -  -\n"
                + "3 -  -  -  -  -  -  -  -\n"
                + "2 -  -  -  -  -  -  -  -\n"
                + "1 -  -  -  -  -  -  -  -\n"
                + "  A  B  C  D  E  F  G  H");
        StringBuilder sb = new StringBuilder();
        Set<Position> customSortedPositions = board.getCells().keySet().stream().sorted((firstPosition, secondPosition) -> {
            if (firstPosition.getRow() == secondPosition.getRow()) {
                return firstPosition.getColumn() - secondPosition.getColumn();
            } else {
                return Integer.compare(secondPosition.getRow(), firstPosition.getRow());
            }
        }).collect(Collectors.toCollection(LinkedHashSet::new));
        for (Position position : customSortedPositions) {
            logger.log(Level.INFO, "Current position; row: " + position.getRow() + ", column: " + position.getColumn());
        }
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
        if(board.hasBoardMoreThanOneFreeCell()) {
            try {
                if (board.areAdjacentCellsOccupied(allPlayersMoves.getLast().getPosition())) {
                    return GameStatus.FREEDOM;
                } else {
                    return GameStatus.NO_FREEDOM;
                }
            } catch (NoSuchElementException exception) {
                return GameStatus.FREEDOM;
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
        String input = userInput.nextLine();
        while (!input.matches("Yes|No")) {
            System.out.print("Wrong input format, answer either Yes or No, try again: ");
            input = userInput.nextLine();
        }
        if (input.equals("Yes")) {
            return null;
        } else {
            return getPositionFromUser();
        }
    }

    private boolean isPositionInsideBoardRange(@NotNull Position chosenPosition) {
        return board.getNumberOfRows() >= chosenPosition.getRow() && board.getNumberOfColumns() >= chosenPosition.getColumn();
    }

    @Contract("_ -> new")
    private static @NotNull Position parsePositionFromFormattedUserInput(@NotNull String input) {
        return new Position(Integer.parseInt(input.substring(1)), input.charAt(0) - 'A' + 1);
    }

    @NotNull
    private Position getPositionFromUser() {
        System.out.print("Insert the cell name, for example A5: ");
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][0-9]")) {
                Position chosenPosition = parsePositionFromFormattedUserInput(input);
                if (isPositionInsideBoardRange(chosenPosition)) {
                    if (board.getCell(chosenPosition).isOccupied()) {
                        System.out.print("The picked cell is already occupied! Pick again: ");
                        input = userInput.nextLine();
                    } else {
                        return chosenPosition;
                    }
                } else {
                    System.out.print("The specified cell is outside of the board range! Pick again: ");
                    input = userInput.nextLine();
                }
            } else {
                System.out.print("Wrong input format, try again: ");
                input = userInput.nextLine();
            }
        }
    }

    @Contract("_ -> new")
    private @NotNull Position getPositionFromUserFromSuggestedSet(@NotNull Set<Position> suggestedPositions) {
        System.out.print("Insert the cell name: ");
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][0-9]")) {
                Position chosenPosition = parsePositionFromFormattedUserInput(input);
                if (isPositionInsideBoardRange(chosenPosition)) {
                    if (suggestedPositions.contains(chosenPosition)) {
                        if (!board.getCell(chosenPosition).isOccupied()) {
                            return chosenPosition;
                        } else {
                            System.out.print("The picked cell is already occupied! Pick again: ");
                            input = userInput.nextLine();
                        }
                    } else {
                        System.out.print("The specified cell is not adjacent to the last occupied one! Pick again: ");
                        input = userInput.nextLine();
                    }
                } else {
                    System.out.print("The specified cell is outside of the board range! Pick again: ");
                    input = userInput.nextLine();
                }
            } else {
                System.out.print("Wrong input format, try again: ");
                input = userInput.nextLine();
            }
        }
    }

    private void end() {
        userInput.close();
    }

}
