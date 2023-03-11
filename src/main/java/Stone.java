public class Stone {

    private boolean isLive = false;
    private final Color color;

    Stone(Color color) {
        this.color = color;
    }

    public boolean isLive() {
        return this.isLive;
    }

    public Color getColor() {
        return color;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
