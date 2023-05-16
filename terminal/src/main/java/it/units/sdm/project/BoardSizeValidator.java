package it.units.sdm.project;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.jetbrains.annotations.NotNull;

public class BoardSizeValidator implements IParameterValidator {

    public static final int MIN_BOARD_SIZE = 1;
    public static final int MAX_BOARD_SIZE = 99;

    @Override
    public void validate(@NotNull String name, @NotNull String value) throws ParameterException {
        int boardSize;
        try {
            boardSize = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " needs to be an integer between "
                    + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
        }
        if (boardSize > MAX_BOARD_SIZE || boardSize < MIN_BOARD_SIZE) {
            throw new ParameterException("Parameter " + name + " should specify a board size between "
                    + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
        }
    }
}
