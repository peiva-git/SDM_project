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
    private final String name;
    @NotNull
    private final String surname;

    /**
     * Creates a {@link BoardGame} {@link Player} with some basic information
     * @param color {@link Player}'s {@link Color}. Can be either {@link Color#BLACK} or {@link Color#WHITE}
     * @param name {@link Player}'s name
     * @param surname {@link Player}'s surname
     * @throws RuntimeException In case the {@link Color}'s different from {@link Color#BLACK} or {@link Color#WHITE}
     */
    public Player(@NotNull Color color, @NotNull String name, @NotNull String surname) throws IllegalArgumentException {
        if (!isColorValid(color)) {
            throw new IllegalArgumentException("Invalid player color, can be either black or white");
        }
        this.color = color;
        this.name = name;
        this.surname = surname;
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
     * Gets the {@link Player}'s name
     * @return {@link Player}'s name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Gets the {@link Player}'s surname
     * @return {@link Player}r's surname
     */
    public @NotNull String getSurname() {
        return surname;
    }

    /**
     * Two {@link Player}s are equal if they have the same {@link Color}
     * @param obj The {@link Object} to compare {@code this} with
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
     * @return {@link String} representation of {@code this} {@link Player},
     * that includes {@code this} {@link Player}'s name and surname
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }
}
