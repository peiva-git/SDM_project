import org.jetbrains.annotations.NotNull;

public class Cell {
    private Stone stone;

    public Cell() {
    }
    public Cell(@NotNull Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }

    public boolean isOccupied() {
        return stone != null;
    }

    public void putStone(@NotNull Stone stone) {
        this.stone = stone;
    }

    public void clear() {
        this.stone = null;
    }

}
