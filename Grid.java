import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Grid {
    
    public static final int WIDTH = 10;
    public static final int HEIGHT = 12;
    public static final int SPAWN = 2;
    public static final char EMPTY = '-';

    private char[][] grid = new char[HEIGHT][WIDTH];

    public Grid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i < SPAWN) {
                    grid[i][j] = ' ';
                } else {
                    grid[i][j] = EMPTY;
                }
            }
        }
    }

    public List<Blok> findCollapsableBloks() {
        List<Blok> collapsable = new ArrayList<>();
        boolean hole = false;
        for (int j = 0; j < WIDTH; j++) {
            for (int i = HEIGHT - 1; i >= 2; i--) {
                char here = this.grid[i][j];
                if (here == EMPTY) {
                    hole = true;
                    continue;
                }
                if (hole) {
                    collapsable.add(this.getBlok(new Point(i, j)));
                }
            }
            hole = false;
        }
        return collapsable;
    }

    public List<Point> findErasableBloks() {
        List<Point> erasables = new ArrayList<>();
        for (int i = 2; i < HEIGHT - 1; i++) {
            for (int j = 0; j < WIDTH - 1; j++) {
                char here = Character.toLowerCase(this.grid[i][j]);
                if (here == EMPTY) {
                    continue;
                }
                char right = Character.toLowerCase(this.grid[i][j + 1]);
                char down = Character.toLowerCase(this.grid[i + 1][j]);
                char downRight = Character.toLowerCase(this.grid[i + 1][j + 1]);
                if (here == right && right == down && down == downRight) {
                    erasables.add(new Point(i, j));
                    erasables.add(new Point(i, j + 1));
                    erasables.add(new Point(i + 1, j));
                    erasables.add(new Point(i + 1, j + 1));
                }
            }
        }
        return erasables;
    }

    public int getFloor(int colNo) {
        for (int y = HEIGHT - 1; y >= 0; y--) {
            char c = this.grid[y][colNo];
            if (c == EMPTY) {
                return y;
            }
        }
        return -1;
    }

    public Grid remove(AbstractPieceRain apr) {
        Grid clone = this.clone();
        for (Blok b : apr.getBloks()) {
            clone = clone.remove(b);
        }
        return clone;
    }

    public Grid remove(Blok blok) {
        Grid clone = this.clone();
        clone.grid[blok.getY()][blok.getX()] = blok.getY() < SPAWN ? ' ' : EMPTY;
        return clone;
    }

    public Grid add(AbstractPieceRain apr) {
        Grid clone = this.clone();
        for (Blok b : apr.getBloks()) {
            clone = clone.add(b);
        }
        return clone;
    }

    public Grid add(Blok blok) {
        Grid clone = this.clone();
        clone.grid[blok.getY()][blok.getX()] = blok.getChar();
        return clone;
    }

    public Blok getBlok(Point coordinates) {
        char c = this.grid[coordinates.x][coordinates.y];
        if (c == EMPTY) {
            System.out.println(coordinates);
            System.out.println("hi");
            return Blok.empty();
        }
        return Blok.generate(c, coordinates.y, coordinates.x);
    }

    public int getCentreX() {
        return (WIDTH - 1) / 2;
    }

    public Grid clone() {
        char[][] cloned = new char[HEIGHT][WIDTH];
        for (int i = 0; i < cloned.length; i++) {
            for (int j = 0; j < cloned[i].length; j++) {
                cloned[i][j] = this.grid[i][j];
            }
        }
        return new Grid(cloned);
    }

    private Grid(char[][] grid) {
        this.grid = grid;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (char[] row : this.grid) {
            for (char c : row) {
                builder.append(c + " ");
            }
            builder.append("\n");
        }
        return builder + "";
    }
}
