package it.units.sdm.project;

import it.units.sdm.project.core.FreedomBoard;
import it.units.sdm.project.core.FreedomGame;
import it.units.sdm.project.interfaces.Game;

public class Main {
    public static void main(String[] args) {
        Player white = new Player(Stone.Color.WHITE, "Mario", "Rossi");
        Player black = new Player(Stone.Color.BLACK, "Marco", "Neri");
        FreedomBoard board = new FreedomBoard(8, 8);
        Game game = new FreedomGame(board, white, black);
        game.start();
    }
}
