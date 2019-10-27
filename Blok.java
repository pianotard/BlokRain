public class Blok {
    
    private char color;
    private int x, y;

    private Blok(char color, int x, int  y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Blok translate(int x, int y) {
        return new Blok(this.color, this.x + x, this.y + y);
    }

    public Blok setForErasure() {
        if (this.color == 'W' || this.color == 'B') {
            return this;
        }
        return new Blok(Character.toUpperCase(this.color), this.x, this.y);
    }

    public static Blok black(int x, int y) {
        return new Blok('b', x, y);
    }

    public static Blok white(int x, int y) {
        return new Blok('w', x, y);
    }

    public static Blok empty() {
        return new Blok('e', -1, -1);
    }

    public static Blok generate(char col, int x, int y) {
        if (col == 'w' || col == 'W') {
            return Blok.white(x, y);
        } else if (col == 'b' || col == 'B') {
            return Blok.black(x, y);
        } else {
            return Blok.empty();
        }
    }

    public Blok setX(int x) {
        return new Blok(this.color, x, this.y);
    }

    public Blok setY(int y) throws IllegalCoordinateException {
        if (y < 0) {
            throw new IllegalCoordinateException(y + "");
        }
        return new Blok(this.color, this.x, y);
    }

    public Blok clone() {
        return new Blok(this.color, this.x, this.y);
    }

    public char getChar() {
        return this.color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean exceedsBounds() {
        return this.x < 0 || this.x >= Grid.WIDTH || this.y < 0 || this.y >= Grid.HEIGHT;
    }

    public void printDetails() {
        String color = (this.color == 'w' || this.color == 'W') ? "white" : "black";
        System.out.println(color + " blok @ " + this.x + ", " + this.y);
    }

    @Override
    public String toString() {
        return this.color + "";
    }
}
