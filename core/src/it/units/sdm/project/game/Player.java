package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents a {@link it.units.sdm.project.game.gui.FreedomGame} player.
 */
public class Player {
    @NotNull
    private final Color color;
    @NotNull
    private final String username;

    /**
     * Creates a {@link BoardGame} {@link Player} with some basic information
     * @param color {@link Player}'s {@link Color}. Can be either {@link Color#BLACK} or {@link Color#WHITE}
     * @param username {@link Player}'s username
     * @throws RuntimeException In case the {@link Color}'s different from {@link Color#BLACK} or {@link Color#WHITE}
     */
    public Player(@NotNull Color color, @NotNull String username) throws IllegalArgumentException {
        if (!isColorValid(color)) {
            throw new IllegalArgumentException("Invalid player color, can be either black or white");
        }
        this.color = color;
        this.username = username;
    }

    private boolean isColorValid(Color playersColor) {
        return playersColor == Color.BLACK || playersColor == Color.WHITE;
    }

    /**
     * Gets the {@link Player}'s {@link Color}
     * @return {@link Player}'s {@link Color}
     */
    @NotNull
    public Color getColor() {
        return color;
    }

    /**
     * Returns {@code this} {@link Player}'s username
     * @return The {@link Player}'s username
     */
    public @NotNull String getUsername() {
        return username;
    }

    /**
     * Two {@link Player}s are equal if they have the same {@link Color}
     * @param obj The {@link Object} to compare {@code this} {@link Player} with
     * @return {@code true} if the {@link Player}s are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Player) obj).getColor() == color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name, surname);
    }

    /**
     * {@link String} representation of {@code this} {@link Player}.
     * @return A {@link String} representation of {@code this} {@link Player},
     * that includes {@code this} {@link Player}'s username
     */
    @Override
    public String toString() {
        return username;
    }
}
