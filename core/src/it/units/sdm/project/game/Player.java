package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

public class Player {

    private final Color color;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;

    /**
     * Creates a board game player with some basic information
     * @param color Player's color. Can be either black or white
     * @param name Player's name
     * @param surname Player's surname
     * @throws RuntimeException In case the color's different from black or white
     */
    public Player(Color color, @NotNull String name, @NotNull String surname) throws RuntimeException {
        if (!isColorValid(color)) {
            throw new RuntimeException("Invalid player color, can be either black or white");
        }
        this.color = color;
        this.name = name;
        this.surname = surname;
    }

    private boolean isColorValid(Color playersColor) {
        return playersColor == Color.BLACK || playersColor == Color.WHITE;
    }

    public Color getColor() {
        return color;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Player) obj).getColor() == color;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
