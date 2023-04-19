package it.units.sdm.project;

import it.units.sdm.project.core.board.MapBoard;
import it.units.sdm.project.core.board.Stone;
import it.units.sdm.project.core.game.Player;
import it.units.sdm.project.interfaces.Board;

public class TerminalLauncher {
    public static void main(String[] args) {
        Board<Stone> board = new MapBoard<>(8,8);
        Player whitePlayer = new Player(Stone.Color.WHITE, "Mario", "Rossi");
        Player blackPlayer = new Player(Stone.Color.BLACK, "Lollo", "Rossi");
        FreedomGame freedomGame = new FreedomGame(board, whitePlayer, blackPlayer);
        freedomGame.start();
    }
}
