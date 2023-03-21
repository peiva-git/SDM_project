import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UserInput {
    @NotNull Position getPosition();
}
