package it.units.sdm.project;

import it.units.sdm.project.core.FreedomGame;
import it.units.sdm.project.core.MapBoard;

public class TerminalLauncher {
    public static void main(String[] args) {
        MapBoard<Stone> board = new MapBoard<>(8,8);
        Player whitePlayer = new Player(Stone.Color.WHITE, "Mario", "Rossi");
        Player blackPlayer = new Player(Stone.Color.BLACK, "Lollo", "Rossi");
        FreedomGame freedomGame = new FreedomGame(board, whitePlayer, blackPlayer);
        freedomGame.start();
    }
}