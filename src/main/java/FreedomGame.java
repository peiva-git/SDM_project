import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class FreedomGame implements Game {

    private enum GameStatus {NOT_STARTED, STARTED, FREEDOM, NO_FREEDOM, LAST_MOVE, GAME_OVER}

    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final FreedomBoard board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private final TextInput userInput = new TextInput();

    public FreedomGame(@NotNull FreedomBoard board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
    }

    @Override
    public void start() {
        System.out.println("Game starting up, clearing board...");
        board.clearBoard();
        gameStatus = GameStatus.STARTED;
        while (gameStatus != GameStatus.GAME_OVER) {
            playTurn();
        }
        Player winner = getWinner();
        if (winner != null) {
            System.out.println("The winner is: " + getWinner());
        } else {
            System.out.println("Tie!");
        }
        end();
    }

    private void end() {
        userInput.close();
    }

    @Override
    public void playTurn() {
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
        if (chosenPosition != null) {
            playersMovesHistory.add(new Move(currentPlayer, chosenPosition));
        } else {
            List<Move> currentPlayersMoves = playersMovesHistory.stream()
                    .filter(move -> move.getPlayer().equals(currentPlayer))
                    .collect(Collectors.toList());
            // the current player chose to skip his move, so the position will stay the same
            playersMovesHistory.add(currentPlayersMoves.get(currentPlayersMoves.size() - 1));
        }
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

    private GameStatus getCurrentGameStatus() {
        if (board.hasBoardMoreThanOneFreeCell()) {
            if (playersMovesHistory.isEmpty() || board.areAdjacentCellsOccupied(playersMovesHistory.getLast().getPosition())) {
                return GameStatus.FREEDOM;
            } else {
                return GameStatus.NO_FREEDOM;
            }
        } else {
            return GameStatus.LAST_MOVE;
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
            int row = adjacentPosition.getRow();
            char column = (char) ('A' + adjacentPosition.getColumn() - 1);
            System.out.print(column);
            System.out.print(row);
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

    @Override
    @Nullable
    public Player getWinner() {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        if (freedomPointsCounter.getWhitePlayerScore() > freedomPointsCounter.getBlackPlayerScore()) {
            return whitePlayer;
        } else if (freedomPointsCounter.getBlackPlayerScore() > freedomPointsCounter.getWhitePlayerScore()) {
            return blackPlayer;
        }
        return null;
    }


    private static class Move {

        @NotNull
        private final Player player;
        @NotNull
        private final Position position;

        public Move(@NotNull Player player, @NotNull Position position) {
            this.player = player;
            this.position = position;
        }

        public @NotNull Player getPlayer() {
            return player;
        }

        public @NotNull Position getPosition() {
            return position;
        }
    }
}
