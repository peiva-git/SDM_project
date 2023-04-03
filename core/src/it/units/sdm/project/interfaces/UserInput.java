package it.units.sdm.project.interfaces;

import it.units.sdm.project.Position;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UserInput {
    @NotNull Position getPosition();
}
