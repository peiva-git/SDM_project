import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class TextInput implements UserInput {
    Scanner userInput = new Scanner(System.in);
    @Override
    public @NotNull Position getPosition() {
        Position chosenPosition;
        String input = userInput.nextLine();
        while (true) {
            if (input.matches("[A-Z][0-9]")) {
                chosenPosition = parsePositionFromFormattedUserInput(input);
                break;
            } else {
                System.out.print("Wrong input format, try again: ");
                input = userInput.nextLine();
            }
        }
        return chosenPosition;
    }

    @Override
    public boolean isLastMoveAPass() {
        String input = userInput.nextLine();
        while (true){
            if (!input.matches("Yes|No")) continue;
            return input.equals("Yes");
        }
    }

    @Contract("_ -> new")
    private @NotNull Position parsePositionFromFormattedUserInput(@NotNull String input) {
        return new Position(Integer.parseInt(input.substring(1)), input.charAt(0) - 'A' + 1);
    }

}