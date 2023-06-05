package it.units.sdm.project;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.board.MapBoard.MAX_BOARD_SIZE;
import static it.units.sdm.project.board.MapBoard.MIN_BOARD_SIZE;

public class BoardSizeValidator implements IParameterValidator {

    @Override
    public void validate(@NotNull String name, @NotNull String value) throws ParameterException {
        try {
            int boardSize = Integer.parseInt(value);
            if (boardSize > MAX_BOARD_SIZE || boardSize < MIN_BOARD_SIZE) {
                throw new ParameterException("Parameter " + name + " should specify a board size between "
                        + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
            }
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " needs to be an integer between "
                    + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
        }
    }
}
