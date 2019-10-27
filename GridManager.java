import java.awt.Point;
import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GridManager {

    private Random rng = new Random();
    private int seed = rng.nextInt();
    private Grid grid = new Grid();
    private AbstractPieceRain apr;

    public GridManager() {
        this.rng.setSeed(this.seed);
        this.spawnAPR();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println(this.seed);
        while (true) {
            System.out.println(this);
            String comd = sc.next();
            if (comd.equals("l") || comd.equals("a")) {
                this.moveAPRLeft();
                continue;
            }
            if (comd.equals("r") || comd.equals("d")) {
                this.moveAPRRight();
                continue;
            }
            if (comd.equals("q")) {
                this.rotateAPRLeft();
                continue;
            }
            if (comd.equals("e")) {
                this.rotateAPRRight();
                continue;
            }
            try {
                this.collapseAPR();
                if (Main.debug) System.out.println("after collapseAPR" + this);
                this.markErasables();
                if (Main.debug) System.out.println("after markErasable" + this);
                this.spawnAPR();
                if (Main.debug) System.out.println("after spawnAPR" + this);
                this.eraseErasables();
                if (Main.debug) System.out.println("after erase" + this);
                this.collapseGrid();
                if (Main.debug) System.out.println("after collapse" + this);
                if (!Main.debug) System.out.println(this);
            } catch (APRClashException e) {
                break;
            }
        }
        System.out.println(this);
    }

    private void rotateAPRRight() {
        this.grid = this.grid.remove(this.apr);
        this.apr = this.apr.rotateRight();
        this.grid = this.grid.add(this.apr); 
    }

    private void rotateAPRLeft() {
        this.grid = this.grid.remove(this.apr);
        this.apr = this.apr.rotateLeft();
        this.grid = this.grid.add(this.apr); 
    }

    private void moveAPRLeft() {
        this.grid = this.grid.remove(this.apr);
        this.apr = this.apr.translate(-1, 0);
        this.grid = this.grid.add(this.apr);
    }

    private void moveAPRRight() {
        this.grid = this.grid.remove(this.apr);
        this.apr = this.apr.translate(1, 0);
        this.grid = this.grid.add(this.apr); 
    }

    private void eraseErasables() {
        List<Point> erasables = this.grid.findErasableBloks().stream()
            .distinct()
            .collect(Collectors.toList());
        for (Point p : erasables) {
            Blok b = this.grid.getBlok(p);
            this.grid = this.grid.remove(b);
        }
    }

    private void markErasables() {
        List<Point> erasables = this.grid.findErasableBloks();
        for (Point p : erasables) {
            Blok b = this.grid.getBlok(p);
            Blok setForErasure = b.setForErasure();
            this.grid = this.grid.remove(b).add(setForErasure);
        } 
    }

    private void collapseGrid() {
        List<Blok> collapsable = this.grid.findCollapsableBloks();
        for (Blok b : collapsable) {
            Blok collapsed = b.setY(this.grid.getFloor(b.getX()));
            this.grid = this.grid.remove(b).add(collapsed);
        }
    }

    private void collapseAPR() throws APRClashException {
        int[] columns = this.apr.getColumnNumbers();
        List<Blok> originalList = new ArrayList<>();
        List<Blok> collapsedList = new ArrayList<>();
        Grid dummyGrid = this.grid;
        for (int col : columns) {
            Blok[] bloks = this.apr.getBloks(col);
            originalList.addAll(Arrays.asList(bloks));
            Blok[] collapsed = new Blok[bloks.length];
            for (int i = bloks.length - 1; i >= 0; i--) {
                Blok b = bloks[i];
                try {
                    collapsed[i] = b.setY(dummyGrid.getFloor(col));
                    dummyGrid = dummyGrid.remove(b).add(collapsed[i]);
                } catch (IllegalCoordinateException e) {
                    System.err.println(e);
                    throw new APRClashException(this.apr + "");
                }
            }
            collapsedList.addAll(Arrays.asList(collapsed));
        }
        for (Blok b : originalList) {
            this.grid = this.grid.remove(b);
        }
        for (Blok b : collapsedList) {
            this.grid = this.grid.add(b);
        }
    }

    private void spawnAPR() {
        this.apr = this.generateRandomRain();
        this.grid = this.grid.add(apr); 
    }

    private AbstractPieceRain generateRandomRain() {
        int square = this.rng.nextInt(2);
        AbstractPieceRain gen;
        if (square == 1) {
            int equal = this.rng.nextInt(2);
            if (equal == 1) {
                int checkered = this.rng.nextInt(2);
                gen = SquarePieceRain.generate(checkered);
            } else {
                int white = this.rng.nextInt(2);
                int solid = this.rng.nextInt(2);
                if (white == 1) {
                    gen = SquarePieceRain.generate(
                            (x, y) -> Blok.white(x, y),
                            (x, y) -> Blok.black(x, y), solid);
                } else {
                    gen = SquarePieceRain.generate(
                            (x, y) -> Blok.black(x, y),
                            (x, y) -> Blok.white(x, y), solid);
                }
            }
        } else {
            int white = this.rng.nextInt(2);
            gen = LongPieceRain.generate(white);
        }
        return gen.setCentreX(this.grid.getCentreX());
    }

    public int getSeed() {
        return this.seed;
    }

    @Override
    public String toString() {
        return this.grid + "";
    }
}
