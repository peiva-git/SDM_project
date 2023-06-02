package it.units.sdm.project;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.game.*;
import it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Set;

import static it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus.*;

public class FreedomGame implements BoardGame {

    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final Board<Stone> board;
    @NotNull
    private GameStatus gameStatus;
    @NotNull
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    @NotNull
    private final TerminalInputReader userInput = new TerminalInputReader();
    @NotNull
    private final FreedomBoardStatusObserver statusObserver;

    public FreedomGame(@NotNull Board<Stone> board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        statusObserver = new FreedomBoardStatusObserver(board);
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
    }

    public void start() {
        System.out.println("Welcome to Freedom!");
        System.out.println("Game starting up, clearing board...\n");
        board.clearBoard();
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
        while (gameStatus != GAME_OVER) {
            playTurn();
        }
        Player winner = statusObserver.getCurrentWinner(whitePlayer, blackPlayer);
        System.out.println(board);
        displayTheWinner(winner);
        reset();
    }

    private void displayTheWinner(Player winner) {
        if (winner != null) {
            System.out.println("The winner is: " + winner);
        } else {
            System.out.println("Tie!");
        }
    }

    private void playTurn() {
        Position chosenPosition = null;
        System.out.println(board);
        switch (gameStatus) {
            case FREEDOM:
                chosenPosition = getPositionWithFreedom(getNextPlayer());
                break;
            case NO_FREEDOM:
                chosenPosition = getPositionWithNoFreedom(getNextPlayer());
                break;
            case LAST_MOVE:
                chosenPosition = playLastMove(getNextPlayer());
                if (chosenPosition == null) {
                    gameStatus = GAME_OVER;
                    return;
                }
                break;
            case GAME_OVER:
                return;
        }
        nextMove(chosenPosition);
    }

    private @NotNull Position getPositionWithFreedom(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Freeedom! You can place a stone on any unoccupied cell");
        return getPositionFromUser();
    }

    private @NotNull Position getPositionWithNoFreedom(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("You can place a stone near the last stone placed by the other player");
        Position lastPosition = playersMovesHistory.getLast().getPosition();
        Set<Position> adjacentPositions = board.getAdjacentPositions(lastPosition);
        System.out.print("Yuo can pick one of the following positions: ");
        adjacentPositions.stream().sorted().forEach(adjacentPosition -> {
            int displayedRow = adjacentPosition.getRow() + 1;
            char displayedColumn = (char) ('A' + adjacentPosition.getColumn());
            System.out.print(displayedColumn);
            System.out.print(displayedRow);
            System.out.print(" ");
        });
        System.out.println();
        return getPositionFromUserWithinSuggestedSet(adjacentPositions);
    }

    private @Nullable Position playLastMove(@NotNull Player player) throws RuntimeException {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Last move! You can decide to either play or pass");
        System.out.print("Do you want to pass? (Yes/No): ");
        if (!userInput.isLastMoveAPass()) {
            if (board.getFreePositions().size() != 1)
                throw new RuntimeException("When playing the last move, there should be only one free cell left on the board");
            return board.getFreePositions().iterator().next();
        }
        return null;
    }

    @NotNull
    private Position getPositionFromUser() {
        System.out.print("Insert the cell name, for example A5: ");
        while (true) {
            Position chosenPosition = userInput.getPosition();
            try {
                if (board.isCellOccupied(chosenPosition)) {
                    System.out.print("The picked cell is already occupied! Pick again: ");
                } else {
                    return chosenPosition;
                }
            } catch (InvalidPositionException exception){
                System.out.print("The specified cell is outside of the board range! Pick again: ");
            }
        }
    }

    private @NotNull Position getPositionFromUserWithinSuggestedSet(@NotNull Set<Position> suggestedPositions) {
        System.out.print("Insert the cell name: ");
        while (true) {
            Position chosenPosition = userInput.getPosition();
            try {
                if (suggestedPositions.contains(chosenPosition)) {
                    if (!board.isCellOccupied(chosenPosition)) {
                        return chosenPosition;
                    } else {
                        System.out.print("The picked cell is already occupied! Pick again: ");
                    }
                } else {
                    System.out.print("The specified cell is not adjacent to the last occupied one! Pick again: ");
                }
            } catch(InvalidPositionException exception) {
                System.out.print("The specified cell is outside of the board range! Pick again: ");
            }
        }
    }

    @Override
    public @NotNull Board<?> getBoard() {
        return board;
    }

    @Override
    public @NotNull Player getFirstPlayer() {
        return whitePlayer;
    }

    @Override
    public @NotNull Player getSecondPlayer() {
        return blackPlayer;
    }

    @Override
    public @Nullable Move getLastMove() {
        if(playersMovesHistory.isEmpty()) return null;
        return playersMovesHistory.getLast();
    }

    @Override
    public void nextMove(@NotNull Position position) {
        board.putPiece(new Stone(getNextPlayer().getColor()), position);
        playersMovesHistory.add(new Move(getNextPlayer(), position));
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
    }

    @Override
    public void reset() {
        board.clearBoard();
        userInput.close();
    }
}
