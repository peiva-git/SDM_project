import org.jetbrains.annotations.NotNull;

public class Cell {

    public enum Status {FREE, OCCUPIED}

    private Stone stone;
    private Status status = Status.FREE;

    public Cell() {
    }
    public Cell(@NotNull Stone stone) {
        this.stone = stone;
        this.status = Status.OCCUPIED;
    }

    public Stone getStone() {
        return stone;
    }

    public Status isOccupied() {
        return status;
    }

    public void setStone(@NotNull Stone stone) {
        this.stone = stone;
        this.status = Status.OCCUPIED;
    }

}
