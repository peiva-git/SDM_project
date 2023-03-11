import org.jetbrains.annotations.NotNull;

public class Cell {
    private Stone stone;
    private boolean occupied = false;

    public Cell() {
    }
    public Cell(@NotNull Stone stone) {
        this.stone = stone;
        this.occupied = true;
    }

    public Stone getStone() {
        return stone;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public void putStone(@NotNull Stone stone) {
        this.stone = stone;
        this.occupied = true;
    }

    public void clear() {
        this.stone = null;
        this.occupied = false;
    }

}
