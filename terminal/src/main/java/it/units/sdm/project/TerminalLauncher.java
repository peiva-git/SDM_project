package it.units.sdm.project;

import com.badlogic.gdx.graphics.Color;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.game.Player;

public class TerminalLauncher {

    @Parameter(names = {"-s", "--size"}, description = "Square board size", validateWith = BoardSizeValidator.class)
    private int boardSize = 8;

    @Parameter(names = {"--white-name"}, description = "White player name", validateWith = NameSizeValidator.class)
    private String whitePlayerName = "Mario";

    @Parameter(names = {"--white-surname"}, description = "White player surname", validateWith = NameSizeValidator.class)
    private String whitePlayerSurname = "Rossi";

    @Parameter(names = {"--black-name"}, description = "Black player name", validateWith = NameSizeValidator.class)
    private String blackPlayerName = "Lollo";

    @Parameter(names = {"--black-surname"}, description = "Black player surname", validateWith = NameSizeValidator.class)
    private String blackPlayerSurname = "Rossi";

    public static void main(String[] args) {
        TerminalLauncher launcher = new TerminalLauncher();
        try {
            JCommander.newBuilder()
                    .addObject(launcher)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            e.usage();
            return;
        }

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
