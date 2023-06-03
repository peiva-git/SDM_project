package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * {@link BoardGame} player.
 */
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

    /**
     * Gets the player's color
     * @return Player's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the player's name
     * @return Player's name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Gets the player's surname
     * @return Player's surname
     */
    public @NotNull String getSurname() {
        return surname;
    }

    /**
     * Two players are equal whether they have the same color
     * @param obj Player instance to be compared with
     * @return True if the players are equal, otherwise the method returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Player) obj).getColor() == color;
    }

    /**
     * String representation of the player.
     * @return String representation of the player that includes player's name and surname
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }
}
