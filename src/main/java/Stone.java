import org.jetbrains.annotations.NotNull;

public class Stone {

    public enum Color {WHITE, BLACK}
    @NotNull
    private final Color color;

    public Stone(@NotNull Color color) {
        this.color = color;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Stone) obj).getColor() == color;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
