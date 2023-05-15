package it.units.sdm.project;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.jetbrains.annotations.NotNull;

public class NameSizeValidator implements IParameterValidator{

    public static final int MAX_NAME_LENGTH = 30;

    @Override
    public void validate(@NotNull String name, @NotNull String value) throws ParameterException {
        if (value.length() > MAX_NAME_LENGTH) {
            throw new ParameterException("Parameter " + name + "'s value is too long! Maximum allowed size is " + MAX_NAME_LENGTH);
        }
    }
}
