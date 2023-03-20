import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    private final Scanner userInput = new Scanner(System.in);

    public Game(@NotNull Board board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
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
        System.out.println("8 -  -  -  -  -  -  -  -\n"
                + "7 -  -  -  -  -  -  -  -\n"
                + "6 -  -  -  -  -  -  -  -\n"
                + "5 -  -  -  -  -  -  -  -\n"
                + "4 -  -  -  -  -  -  -  -\n"
                + "3 -  -  -  -  -  -  -  -\n"
                + "2 -  -  -  -  -  -  -  -\n"
                + "1 -  -  -  -  -  -  -  -\n"
                + "  A  B  C  D  E  F  G  H");
        switch (getCurrentGameStatus()) {
            case FREEDOM:
                chosenPosition = getPositionWithFreedom(currentPlayer);
                break;
            case NO_FREEDOM:
                chosenPosition = getPositionWithNoFreedom(currentPlayer);
                break;
            case LAST_MOVE:
                chosenPosition = playLastMove(currentPlayer);
                gameStatus = GameStatus.GAME_OVER;
                break;
        }
        System.out.println("Last chosen position, row: " + chosenPosition.getRow() + ", column: " + chosenPosition.getColumn());
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
        System.out.print("Insert the cell name, for example A5: ");
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][0-9]")) {
                int row = Integer.parseInt(input.substring(1));
                int column = input.charAt(0) - 'A' + 1;
                if (board.getNumberOfRows() >= row && board.getNumberOfColumns() >= column) {
                    return new Position(row, column);
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
    private @NotNull Position getPositionWithNoFreedom(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("You can place a stone near the last stone placed by the other player");
        System.out.print("Insert the cell name, for example A5: ");
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][0-9]")) {
                int row = Integer.parseInt(input.substring(1));
                int column = input.charAt(0) - 'A' + 1;
                if (board.getNumberOfRows() >= row && board.getNumberOfColumns() >= column) {
                    return new Position(row, column);
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

    private Position playLastMove(Player player) {
        // TODO
        return null;
    }

    private void end() {
        // TODO
    }

}
