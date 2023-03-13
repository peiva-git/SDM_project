import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cell {
    @Nullable
    private Stone stone;

    public Cell() {
    }
    public Cell(@NotNull Stone stone) {
        this.stone = stone;
    }

    @Nullable
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
