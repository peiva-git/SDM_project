package it.units.sdm.project.core.game;

import it.units.sdm.project.core.board.Position;
import it.units.sdm.project.core.board.Stone;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class FreedomLine {

    private final Stone.Color color;
    private final SortedSet<Position> cellPositions = new TreeSet<>();

    public FreedomLine(@NotNull Stone.Color color) {
        this.color = color;
    }

    public FreedomLine(@NotNull Stone.Color color, @NotNull Position... positions) {
        this.color = color;
        cellPositions.addAll(Arrays.asList(positions));
    }

    public Stone.Color getColor() {
        return color;
    }

    public SortedSet<Position> getCellPositions() {
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
