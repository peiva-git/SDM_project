package it.units.sdm.project;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.Scanner;

public class TerminalInputReader implements Closeable {
    Scanner userInput = new Scanner(System.in);

    /**
     * Returns the position that was given by the user
     * @return The chosen position
     */
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

    /**
     * Returns whether the user has decided to pass the last move or to play it
     * @return true if the user wants to pass, false otherwise
     */
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
        return Position.fromCoordinates(Integer.parseInt(input.substring(1)) - 1, input.charAt(0) - 'A');
    }

    /**
     * Closes the input reader
     */
    @Override
    public void close() {
        userInput.close();
    }
}
