import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Line {

    private final Stone.Color color;
    private final Set<Position> cellPositions = new HashSet<>();

    public Line(Stone.Color color) {
        this.color = color;
    }

    public Stone.Color getColor() {
        return color;
    }

    public Set<Position> getCellPositions() {
        return cellPositions;
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
        return ((Line) obj).getCellPositions().equals(cellPositions);
    }
}
