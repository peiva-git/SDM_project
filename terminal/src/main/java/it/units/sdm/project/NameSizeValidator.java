package it.units.sdm.project;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to validate the {@link it.units.sdm.project.game.Player}'s name and surname size parameter
 * in the terminal version of the game. These two parameters are given as command line arguments.
 * The validation is done by implementing the {@link IParameterValidator} interface,
 * which is part of the <a href="https://jcommander.org/">JCommander</a> project.
 */
public class NameSizeValidator implements IParameterValidator{

    /**
     * Maximum allowed {@link it.units.sdm.project.game.Player} name or surname size.
     */
    public static final int MAX_NAME_LENGTH = 30;

    @Override
    public void validate(@NotNull String name, @NotNull String value) throws ParameterException {
        if (value.length() > MAX_NAME_LENGTH) {
            throw new ParameterException("Parameter " + name + "'s value is too long! Maximum allowed size is " + MAX_NAME_LENGTH);
        }
    }
}
