package it.units.sdm.project;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.terminal.MapBoard;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.terminal.FreedomGame;
import it.units.sdm.project.interfaces.Board;

public class TerminalLauncher {
    public static void main(String[] args) {
        Board<Stone> board = new MapBoard<>(8,8);
        Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
        Player blackPlayer = new Player(Color.BLACK, "Lollo", "Rossi");
        FreedomGame freedomGame = new FreedomGame(board, whitePlayer, blackPlayer);
        freedomGame.start();
    }
}
