package it.units.sdm.project;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.Scanner;

/**
 * This class handles user input in the terminal version of the Freedom {@link it.units.sdm.project.game.BoardGame}.
 * By default, the user's input is expected to be found on {@code System.in}.
 */
public class TerminalInputReader implements Closeable {
    Scanner userInput = new Scanner(System.in);

    /**
     * Returns the {@link Position} that was given by the user
     * @return The chosen {@link Position}
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
     * Returns whether the user has decided to pass the last {@link it.units.sdm.project.game.Move} or to play it
     * @return  {@code true} if the user wants to pass, {@code false} otherwise
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
