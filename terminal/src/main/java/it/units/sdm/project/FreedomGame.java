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
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
        while (gameStatus != GAME_OVER) {
            playTurn();
        }
        System.out.println(board);
        System.out.println();
        System.out.println("The game is over!");
        Player winner = statusObserver.getCurrentWinner(whitePlayer, blackPlayer);
        displayTheWinner(winner);
        reset();
    }

    private void playTurn() {
        displayInitialTurnText(getNextPlayer());
        if (gameStatus == LAST_MOVE && userInput.isLastMoveAPass()) {
            gameStatus = GAME_OVER;
        } else {
            Position chosenPosition = getPositionFromUser();
            nextMove(chosenPosition);
        }
        System.out.println();
    }

    private void displayInitialTurnText(@NotNull Player player) {
        System.out.println(board);
        System.out.println();
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Game Status: " + gameStatus);
        if(gameStatus == LAST_MOVE) {
            System.out.println("You can decide to either play or pass");
            System.out.print("Do you want to pass? (Yes/No): ");
        }
    }

    @NotNull
    private Position getPositionFromUser() {
        while (true) {
            displayValidPositions();
            System.out.print("Move " + (playersMovesHistory.size() + 1) + ": ");
            Position chosenPosition = userInput.getPosition();
            try {
                if (isChosenPositionValid(chosenPosition)) {
                    return chosenPosition;
                }
            } catch (InvalidPositionException exception) {
                System.out.println("The specified cell is outside of the board range!");
            }
        }
    }

    private void displayValidPositions() {
        switch (gameStatus) {
            case FREEDOM:
                System.out.println("Pick any empty cell!");
                break;
            case LAST_MOVE:
                System.out.println("You only have one possible move!");
                break;
            case NO_FREEDOM:
                System.out.print("Yuo can pick one of the following positions: ");
                Position lastPosition = playersMovesHistory.getLast().getPosition();
                Set<Position> adjacentPositions = board.getAdjacentPositions(lastPosition);
                adjacentPositions.stream().sorted().forEach(adjacentPosition -> {
                    int displayedRow = adjacentPosition.getRow() + 1;
                    char displayedColumn = (char) ('A' + adjacentPosition.getColumn());
                    System.out.print(displayedColumn);
                    System.out.print(displayedRow);
                    System.out.print(" ");
                });
                System.out.println();
        }
    }


    private boolean isChosenPositionValid(Position chosenPosition) throws InvalidPositionException {
        if (board.isCellOccupied(chosenPosition)) {
            System.out.println("The picked cell is already occupied!");
            return false;
        }
        if (gameStatus == NO_FREEDOM) {
            Position lastPosition = playersMovesHistory.getLast().getPosition();
            Set<Position> adjacentPositions = board.getAdjacentPositions(lastPosition);
            if (!adjacentPositions.contains(chosenPosition)) {
                System.out.println("The specified cell is not adjacent to the last occupied one!");
                return false;
            }
        }
        return true;
    }

    private void displayTheWinner(Player winner) {
        if (winner != null) {
            System.out.println("The winner is: " + winner);
        } else {
            System.out.println("Tie!");
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
        if (playersMovesHistory.isEmpty()) return null;
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
