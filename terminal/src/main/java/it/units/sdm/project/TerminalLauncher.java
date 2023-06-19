package it.units.sdm.project;

import com.badlogic.gdx.graphics.Color;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.game.Player;

/**
 * This class holds the terminal-based {@link FreedomGame}'s entry point.
 */
public class TerminalLauncher {
    private static final String ASCII_LOGO = " ________                                  __                       \n" +
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
    @SuppressWarnings("FieldMayBeFinal")
    @Parameter(names = {"-s", "--size"}, description = "Square board size", validateWith = BoardSizeValidator.class)
    private int boardSize = 8;

    @SuppressWarnings("FieldMayBeFinal")
    @Parameter(names = {"--white-username"}, description = "White player username", validateWith = NameSizeValidator.class)
    private String whitePlayerUsername = "player_one";

    @SuppressWarnings("FieldMayBeFinal")
    @Parameter(names = {"--black-username"}, description = "Black player username", validateWith = NameSizeValidator.class)
    private String blackPlayerUsername = "player_two";

    /**
     * The terminal-based {@link FreedomGame}'s entry point
     * @param args Command-line-supplied arguments
     */
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

        Board<Piece> board = new MapBoard<>(launcher.boardSize, launcher.boardSize);
        Player whitePlayer = new Player(Color.WHITE, launcher.whitePlayerUsername);
        Player blackPlayer = new Player(Color.BLACK, launcher.blackPlayerUsername);
        FreedomGame freedomGame = new FreedomGame(board, whitePlayer, blackPlayer);
        System.out.print(ASCII_LOGO);
        freedomGame.start();
    }
}
