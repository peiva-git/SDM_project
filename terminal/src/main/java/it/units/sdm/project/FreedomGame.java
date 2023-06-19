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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus.*;

/**
 * This class represents a terminal-based implementation of the Freedom {@link BoardGame}.
 * Therefore, by default, user input is expected from {@code System.in} while the output gets printed on {@code System.out}.
 */
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

    /**
     * Creates a new terminal based {@link FreedomGame}
     * @param board The {@link Board} used in the {@link FreedomGame}
     * @param whitePlayer The first {@link Player}
     * @param blackPlayer The second {@link Player}
     */
    public FreedomGame(@NotNull Board<Stone> board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        statusObserver = new FreedomBoardStatusObserver(board);
        gameStatus = statusObserver.getCurrentGameStatus(getLastMove());
    }

    /**
     * Starts the {@link FreedomGame}.
     * By default, the game will be waiting for user input on {@code System.in} and
     * printing on {@code System.out}
     */
    public void start() {
        System.out.println("Welcome to Freedom!");
        System.out.println("Game starting up, clearing board...\n");
        board.clearBoard();
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
        printPromptForPlayerFeedback(getNextPlayer());
        if (gameStatus == LAST_MOVE && userInput.isLastMoveAPass()) {
            gameStatus = GAME_OVER;
        } else {
            Position chosenPosition = getPositionFromUser();
            nextMove(chosenPosition);
        }
        System.out.println();
    }

    private void printPromptForPlayerFeedback(@NotNull Player player) {
        System.out.println(board);
        System.out.println();
        System.out.println(player.getUsername() + ", it's your turn!");
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
            default:
                System.out.print("Yuo can pick one of the following positions: ");
                Position lastPosition = Objects.requireNonNull(getLastMove(),
                        "There should be at least one move stored already when in NO_FREEDOM state").getPosition();
                String formattedAdjacentPositions = board.getAdjacentPositions(lastPosition).stream()
                        .sorted()
                        .map(Position::toString)
                        .collect(Collectors.joining(", "));
                System.out.println(formattedAdjacentPositions);
        }
    }


    private boolean isChosenPositionValid(Position chosenPosition) throws InvalidPositionException {
        if (board.isCellOccupied(chosenPosition)) {
            System.out.println("The picked cell is already occupied!");
            return false;
        }
        if (gameStatus == NO_FREEDOM) {
            Position lastPosition = Objects.requireNonNull(getLastMove(),
                    "There should be at least one move stored already when in NO_FREEDOM state").getPosition();
            Set<Position> adjacentPositions = board.getAdjacentPositions(lastPosition);
            if (!adjacentPositions.contains(chosenPosition)) {
                System.out.println("The specified cell is not adjacent to the last occupied one!");
                return false;
            }
        }
        return true;
    }

    private void displayTheWinner(@Nullable Player winner) {
        if (winner != null) {
            System.out.println("The winner is: " + winner);
        } else {
            System.out.println("Tie!");
        }
    }

    @Override
    public @NotNull Board<Stone> getBoard() {
        return board;
    }

    @Override
    public @NotNull Player getWhitePlayer() {
        return whitePlayer;
    }

    @Override
    public @NotNull Player getBlackPlayer() {
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
