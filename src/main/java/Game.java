public class Game {

    private enum GameStatus {NOTSTARTED, STARTED, FINISHED}
    Player whitePlayer;
    Player blackPlayer;
    Board board;
    GameStatus gameStatus = GameStatus.NOTSTARTED;

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
            turn(blackPlayer);
        }
        end();
    }

    void turn(Player player) {
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
