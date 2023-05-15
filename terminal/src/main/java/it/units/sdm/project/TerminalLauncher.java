package it.units.sdm.project;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.board.Board;

public class TerminalLauncher {
    public static void main(String[] args) {
        Board<Stone> board = new MapBoard<>(8,8);
        Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
        Player blackPlayer = new Player(Color.BLACK, "Lollo", "Rossi");
        FreedomGame freedomGame = new FreedomGame(board, whitePlayer, blackPlayer);
        String welcomeText = " ________                                  __                       \n" +
                "|        \\                                |  \\                      \n" +
                "| ▓▓▓▓▓▓▓▓ ______   ______   ______   ____| ▓▓ ______  ______ ____  \n" +
                "| ▓▓__    /      \\ /      \\ /      \\ /      ▓▓/      \\|      \\    \\ \n" +
                "| ▓▓  \\  |  ▓▓▓▓▓▓\\  ▓▓▓▓▓▓\\  ▓▓▓▓▓▓\\  ▓▓▓▓▓▓▓  ▓▓▓▓▓▓\\ ▓▓▓▓▓▓\\▓▓▓▓\\\n" +
                "| ▓▓▓▓▓  | ▓▓   \\▓▓ ▓▓    ▓▓ ▓▓    ▓▓ ▓▓  | ▓▓ ▓▓  | ▓▓ ▓▓ | ▓▓ | ▓▓\n" +
                "| ▓▓     | ▓▓     | ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓__| ▓▓ ▓▓__/ ▓▓ ▓▓ | ▓▓ | ▓▓\n" +
                "| ▓▓     | ▓▓      \\▓▓     \\\\▓▓     \\\\▓▓    ▓▓\\▓▓    ▓▓ ▓▓ | ▓▓ | ▓▓\n" +
                " \\▓▓      \\▓▓       \\▓▓▓▓▓▓▓ \\▓▓▓▓▓▓▓ \\▓▓▓▓▓▓▓ \\▓▓▓▓▓▓ \\▓▓  \\▓▓  \\▓▓\n" +
                "                                                                    \n" +
                "                                                                    \n" +
                "                                                                    \n";
        System.out.print(welcomeText);
        freedomGame.start();
    }
}
