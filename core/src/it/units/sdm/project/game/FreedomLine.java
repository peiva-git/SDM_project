package it.units.sdm.project.game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class FreedomLine {

    private final Color color;
    private final SortedSet<Position> cellPositions = new TreeSet<>();

    public FreedomLine(@NotNull Color color) {
        this.color = color;
    }

    public FreedomLine(@NotNull Color color, @NotNull Position... positions) {
        this.color = color;
        cellPositions.addAll(Arrays.asList(positions));
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @return This method returns all the positions that are in this freedom line,
     * ordered by the custom ordering defined in the Position class
     */
    public Set<Position> getCellPositions() {
        return cellPositions;
    }

    public Position first() {
        return cellPositions.first();
    }

    public Position last() {
        return cellPositions.last();
    }

    public void addPosition(@NotNull Position position) {
        cellPositions.add(position);
    }

    public int size() {
        return cellPositions.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((FreedomLine) obj).getCellPositions().equals(cellPositions);
    }
}
