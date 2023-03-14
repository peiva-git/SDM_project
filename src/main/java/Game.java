public class Game {

    private enum GameStatus {NOT_STARTED, STARTED, FINISHED}
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;

    public Game(Board board, Player player1, Player player2) {
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

    public void turn(Player player) {
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
