public class Player {

    private Stone.Color color;

    public Player(Stone.Color color) {
        this.color = color;
    }

    public Stone.Color getColor() {
        return color;
    }

    public void setColor(Stone.Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Player) obj).getColor() == color;
    }

}
