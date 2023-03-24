import org.jetbrains.annotations.NotNull;

public class Stone {

    public enum Color {WHITE, BLACK}
    private boolean live = false;
    @NotNull
    private final Color color;

    public Stone(@NotNull Color color) {
        this.color = color;
    }

    public boolean isLive() {
        return this.live;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Stone) obj).getColor() == color;
    }
}
