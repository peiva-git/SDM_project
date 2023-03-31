package it.units.sdm.project.interfaces;

import it.units.sdm.project.Player;
import org.jetbrains.annotations.Nullable;

public interface Game {
    void start();
    @Nullable Player getWinner();
}
