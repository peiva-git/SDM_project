import org.jetbrains.annotations.Nullable;

public interface Game {
    void start();
    void playTurn();
    @Nullable
    Player getWinner();
}
