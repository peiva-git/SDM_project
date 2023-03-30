import org.jetbrains.annotations.NotNull;


public interface Board {
    int getNumberOfRows();
    int getNumberOfColumns();
    boolean isCellOccupied(@NotNull Position position);
    void clearCell(@NotNull Position position);
    void clearBoard();

}
