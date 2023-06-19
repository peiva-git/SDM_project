package utility;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.*;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.stream.IntStream;

public class BoardUtils {

    public static void fillBoardWithWhiteStones(@NotNull Board<Piece> board) {
        for (Position position : board.getPositions()) {
            board.putPiece(new Stone(Color.WHITE), position);
        }
    }

    public static @NotNull Board<Piece> parseBoardFromString(@NotNull String printedBoard, int numberOfRows, int numberOfColumns) {
        Scanner scanner = new Scanner(printedBoard);
        Board<Piece> board = new MapBoard<>(numberOfRows, numberOfColumns);
        while (scanner.hasNextLine()) {
            if (scanner.hasNextInt()) {
                int currentRow = scanner.nextInt() - 1;
                IntStream.rangeClosed(0, numberOfColumns - 1)
                        .forEach(currentColumn -> {
                            String placeholder = scanner.next("[WB-]");
                            if (placeholder.equals("W")) {
                                board.putPiece(new Stone(Color.WHITE), Position.fromCoordinates(currentRow, currentColumn));
                            } else if (placeholder.equals("B")) {
                                board.putPiece(new Stone(Color.BLACK), Position.fromCoordinates(currentRow, currentColumn));
                            }
                        });
                scanner.nextLine();
            } else {
                scanner.close();
                break;
            }
        }
        return board;
    }

}
