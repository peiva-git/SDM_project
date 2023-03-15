import org.jetbrains.annotations.NotNull;

public class Game {

    private enum GameStatus {NOT_STARTED, STARTED, FINISHED}
    @NotNull
    private final Player whitePlayer;
    @NotNull
    private final Player blackPlayer;
    @NotNull
    private final Board board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;

    public Game(@NotNull Board board,@NotNull Player player1,@NotNull Player player2) {
        this.whitePlayer = player1;
        this.blackPlayer = player2;
        this.board = board;
    }

    public void start() {
        board.clearBoard();
        gameStatus = GameStatus.STARTED;
        while (gameStatus == GameStatus.STARTED) {
            turn(whitePlayer);
            if(gameStatus == GameStatus.FINISHED) break;
            turn(blackPlayer);
        }
        end();
    }

    public void turn(@NotNull Player player) {
        if(board.hasMoreThanOneFreeCell()) {
            player.putStone(board);
        } else {
            player.lastMove(board);
            gameStatus = GameStatus.FINISHED;
        }
    }

    private void end() {
        // TODO
    }

}
