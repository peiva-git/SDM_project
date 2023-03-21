import org.jetbrains.annotations.NotNull;

public interface UserInput {
    @NotNull Position getPosition();
    boolean playLastMove();
}
