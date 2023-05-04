package it.units.sdm.project.game.terminal;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.Scanner;

public class TextInput implements Closeable {
    Scanner userInput = new Scanner(System.in);

    public @NotNull Position getPosition() {
        Position chosenPosition;
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][1-9][0-9]?")) {
                chosenPosition = parsePositionFromFormattedUserInput(input);
                break;
            } else {
                System.out.print("Wrong input format, try again: ");
                input = userInput.nextLine();
            }
        }
        return chosenPosition;
    }

    public boolean isLastMoveAPass() {
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("Yes|No")) {
                return input.equals("Yes");
            } else {
                System.out.print("Wrong input format, try again: ");
                input = userInput.nextLine();
            }
        }
    }

    @Contract("_ -> new")
    private @NotNull Position parsePositionFromFormattedUserInput(@NotNull String input) {
        return Position.fromCoordinates(Integer.parseInt(input.substring(1)), input.charAt(0) - 'A' + 1);
    }

    @Override
    public void close() {
        userInput.close();
    }
}
