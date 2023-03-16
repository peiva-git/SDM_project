import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Game {

    private enum GameStatus {NOT_STARTED, STARTED, FREEDOM, NO_FREEDOM, LAST_MOVE, FINISHED}

    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final Board board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    private final LinkedList<Move> allPlayersMoves = new LinkedList<>();

    public Game(@NotNull Board board, @NotNull Player whitePlayer, @NotNull Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
    }

    public void start() {
        board.clearBoard();
        gameStatus = GameStatus.STARTED;
        while (gameStatus != GameStatus.FINISHED) {
            turn();
        }
        end();
    }

    public void turn() {
        Player currentPlayer = nextPlayer();
        Position position = null;
        checkCurrentGameStatus();
        switch (gameStatus) {
            case FREEDOM:
                position = getPositionWithFreedom(currentPlayer);
                break;
            case NO_FREEDOM:
                position = getPositionWithNoFreedom(currentPlayer);
                break;
            case LAST_MOVE:
                position = playLastMove(currentPlayer);
                gameStatus = GameStatus.FINISHED;
                break;
        }
        allPlayersMoves.add(new Move(currentPlayer, position));
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

    private void checkCurrentGameStatus() {
        BoardAnalyzer boardAnalyzer = new BoardAnalyzer(board);
        if(boardAnalyzer.hasBoardMoreThanOneFreeCell()) {
            if(boardAnalyzer.areAdjacentCellsOccupied(allPlayersMoves.getLast().getPosition())) {
                gameStatus = GameStatus.FREEDOM;
            } else {
                gameStatus = GameStatus.NO_FREEDOM;
            }
        } else {
            gameStatus = GameStatus.LAST_MOVE;
        }
    }

    private Position getPositionWithFreedom(Player player) {
        // TODO
        return null;
    }

    private Position getPositionWithNoFreedom(Player player) {
        // TODO
        return null;
    }

    private Position playLastMove(Player player) {
        // TODO
        return null;
    }

    private void end() {
        // TODO
    }

}
