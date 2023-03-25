import org.jetbrains.annotations.NotNull;

public class Score {

    @NotNull
    final private Player player;

    private int numberOfFreedomLines = 0;

    public Score(@NotNull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumberOfFreedomLines() {
        return numberOfFreedomLines;
    }

    public void incrementNumberOfFreedomLines() {
        this.numberOfFreedomLines = this.numberOfFreedomLines + 1;
    }
}
