import org.jetbrains.annotations.NotNull;

public class Player {

    private Stone.Color color;

    public Player(Stone.Color color) {
        this.color = color;
    }

    public void setColor(Stone.Color color) {
        this.color = color;
    }

    public void putStone(@NotNull Board board) {
        // TODO
    }

    public void lastMove(@NotNull Board board) {
        // TODO
    }

    private boolean checkFreedom() {
        // TODO
        return false;
    }

}
