package it.units.sdm.project.board;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

public class Stone {
    @NotNull
    private final Color color;

    public Stone(@NotNull Color color) {
        this.color = color;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Stone) obj).getColor() == color;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
