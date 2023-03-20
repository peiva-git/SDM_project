public class Main {
    public static void main(String[] args) {
        Player white = new Player(Stone.Color.WHITE, "Mario", "Rossi");
        Player black = new Player(Stone.Color.BLACK, "Marco", "Neri");
        Board board = new Board(8, 8);
        Game game = new Game(board, white, black);
        game.start();
    }
}
