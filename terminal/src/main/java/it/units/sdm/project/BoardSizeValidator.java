package it.units.sdm.project;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to validate the {@link it.units.sdm.project.board.Board} size parameter in the
 * terminal version of the {@link FreedomGame}, which is given as a command line argument.
 * This validation is done by implementing the {@link IParameterValidator} interface, which is part of the
 * <a href="https://jcommander.org/">JCommander</a> project.
 */
public class BoardSizeValidator implements IParameterValidator {

    @Override
    public void validate(@NotNull String name, @NotNull String value) throws ParameterException {
        try {
            int boardSize = Integer.parseInt(value);
            if (boardSize > Board.MAX_BOARD_SIZE || boardSize < Board.MIN_BOARD_SIZE) {
                throw new ParameterException("Parameter " + name + " should specify a board size between "
                        + Board.MIN_BOARD_SIZE + " and " + Board.MAX_BOARD_SIZE);
            }
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " needs to be an integer between "
                    + Board.MIN_BOARD_SIZE + " and " + Board.MAX_BOARD_SIZE);
        }
    }
}
