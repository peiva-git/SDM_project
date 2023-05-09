package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

public class Player {

    private final Color color;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;

    public Player(Color color, @NotNull String name, @NotNull String surname) {
        this.color = color;
        this.name = name;
        this.surname = surname;
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
