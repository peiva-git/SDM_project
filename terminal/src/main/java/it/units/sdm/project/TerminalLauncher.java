package it.units.sdm.project;

import com.badlogic.gdx.graphics.Color;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.game.Player;

public class TerminalLauncher {

    @Parameter(names = {"-s", "--size"}, description = "Square board size")
    private int boardSize = 8;

    @Parameter(names = {"--white-name"}, description = "White player name")
    private String whitePlayerName = "Mario";

    @Parameter(names = {"--white-surname"}, description = "White player surname")
    private String whitePlayerSurname = "Rossi";

    @Parameter(names = {"--black-name"}, description = "Black player name")
    private String blackPlayerName = "Lollo";

    @Parameter(names = {"--black-surname"}, description = "Black player surname")
    private String blackPlayerSurname = "Rossi";

    public static void main(String[] args) {
        TerminalLauncher launcher = new TerminalLauncher();
        JCommander.newBuilder()
                .addObject(launcher)
                .build()
                .parse(args);

        Board<Stone> board = new MapBoard<>(launcher.boardSize, launcher.boardSize);
        Player whitePlayer = new Player(Color.WHITE, launcher.whitePlayerName, launcher.whitePlayerSurname);
        Player blackPlayer = new Player(Color.BLACK, launcher.blackPlayerName, launcher.blackPlayerSurname);
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
