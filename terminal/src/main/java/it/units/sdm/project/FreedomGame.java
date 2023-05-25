package it.units.sdm.project;

import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class FreedomGame {

    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final Board<Stone> board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private final TerminalInputReader userInput = new TerminalInputReader();

    public FreedomGame(@NotNull Board<Stone> board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
    }

    public void start() {
        System.out.println("Welcome to Freedom!");
        System.out.println("Game starting up, clearing board...\n");
        board.clearBoard();
        gameStatus = GameStatus.FREEDOM;
        while (gameStatus != GameStatus.GAME_OVER) {
            playTurn();
        }
        Player winner = getCurrentWinner();
        if (winner != null) {
            System.out.println("The winner is: " + winner);
        } else {
            System.out.println("Tie!");
        }
        end();
    }

    private void end() {
        userInput.close();
    }

    private void playTurn() {
        Player currentPlayer = nextPlayer();
        Position chosenPosition = null;
        System.out.println(board);
        switch (gameStatus) {
            case FREEDOM:
                chosenPosition = getPositionWithFreedom(currentPlayer);
                board.putPiece(new Stone(currentPlayer.getColor()), chosenPosition);
                break;
            case NO_FREEDOM:
                chosenPosition = getPositionWithNoFreedom(currentPlayer);
                board.putPiece(new Stone(currentPlayer.getColor()), chosenPosition);
                break;
            case LAST_MOVE:
                chosenPosition = playLastMove(currentPlayer);
                if (chosenPosition != null) {
                    board.putPiece(new Stone(currentPlayer.getColor()), chosenPosition);
                }
                gameStatus = GameStatus.GAME_OVER;
                break;
        }
        if (chosenPosition != null) {
            playersMovesHistory.add(new Move(currentPlayer, chosenPosition));
        } else {
            LinkedList<Move> currentPlayersMoves = playersMovesHistory.stream()
                    .filter(move -> move.getPlayer().equals(currentPlayer))
                    .collect(Collectors.toCollection(LinkedList::new));
            // the current player chose to skip his move, so the position will stay the same
            playersMovesHistory.add(currentPlayersMoves.getLast());
        }
        updateCurrentGameStatus();
    }

    @NotNull
    private Player nextPlayer() {
        try {
            Player previousPlayer = playersMovesHistory.getLast().getPlayer();
            if (previousPlayer.equals(whitePlayer)) return blackPlayer;
            return whitePlayer;
        } catch (NoSuchElementException exception) {
            return this.whitePlayer;
        }
    }

    private void updateCurrentGameStatus() {
        if (board.hasBoardMoreThanOneFreeCell()) {
            if (playersMovesHistory.isEmpty() || board.areAdjacentCellsOccupied(playersMovesHistory.getLast().getPosition())) {
                gameStatus = GameStatus.FREEDOM;
            } else {
                gameStatus = GameStatus.NO_FREEDOM;
            }
        } else {
            gameStatus = GameStatus.LAST_MOVE;
        }
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

    private @Nullable Position playLastMove(@NotNull Player player) {
        System.out.println(player.getName() + " " + player.getSurname() + ", it's your turn!");
        System.out.println("Last move! You can decide to either play or pass");
        System.out.print("Do you want to pass? (Yes/No): ");
        if (!userInput.isLastMoveAPass()) {
            return userInput.getPosition();
        }
        return null;
    }

    private boolean isPositionInsideBoardRange(@NotNull Position chosenPosition) {
        return board.getNumberOfRows() > chosenPosition.getRow() && board.getNumberOfColumns() > chosenPosition.getColumn();
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

    private @NotNull Position getPositionFromUserWithinSuggestedSet(@NotNull Set<Position> suggestedPositions) {
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

    @Nullable
    public Player getCurrentWinner() {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        if (freedomPointsCounter.getWhitePlayerScore() > freedomPointsCounter.getBlackPlayerScore()) {
            return whitePlayer;
        } else if (freedomPointsCounter.getBlackPlayerScore() > freedomPointsCounter.getWhitePlayerScore()) {
            return blackPlayer;
        }
        return null;
    }
}
