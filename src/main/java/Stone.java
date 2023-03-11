public class Stone {

    private boolean live = false;
    private final Color color;

    public Stone(Color color) {
        this.color = color;
    }

    public boolean isLive() {
        return this.live;
    }

    public Color getColor() {
        return this.color;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
