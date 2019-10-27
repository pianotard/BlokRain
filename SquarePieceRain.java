import java.util.function.BiFunction;

public class SquarePieceRain extends AbstractPieceRain {
    
    protected SquarePieceRain(Blok[] bloks) {
        super(bloks);
    }

    protected SquarePieceRain(Blok[] bloks, int x, int y) {
        super(bloks, x, y);
    }

    @Override
    public AbstractPieceRain rotateLeft() {
        Blok[] rotated = new Blok[4];
        rotated[0] = this.bloks[1].translate(-1, 0);
        rotated[1] = this.bloks[3].translate(0, -1);
        rotated[2] = this.bloks[0].translate(0, 1);
        rotated[3] = this.bloks[2].translate(1, 0);
        return new SquarePieceRain(rotated, this.x, this.y);
    }

    @Override
    public AbstractPieceRain rotateRight() {
        return this.rotateLeft().rotateLeft().rotateLeft();
    }

    @Override
    public AbstractPieceRain translate(int x, int y) {
        Blok[] translated = new Blok[4];
        for (int i = 0; i < this.bloks.length; i++) {
            Blok tBlok = this.bloks[i].translate(x, y);
            if (tBlok.exceedsBounds()) {
                return this;
            }
            translated[i] = tBlok;
        }
        return new SquarePieceRain(translated, this.x + x, this.y + y);
    }

    @Override
    public int[] getColumnNumbers() {
        return new int[] {this.x, this.x + 1};
    }

    @Override
    public Blok[] getBloks(int col) {
        int colIndex = col - this.x;
        return new Blok[] {this.bloks[colIndex], this.bloks[colIndex + 2]};
    }

    @Override
    public AbstractPieceRain setCentreX(int centreX) {
        Blok[] set = new Blok[4];
        set[0] = this.bloks[0].setX(centreX);
        set[1] = this.bloks[1].setX(centreX + 1);
        set[2] = this.bloks[2].setX(centreX);
        set[3] = this.bloks[3].setX(centreX + 1);
        return new SquarePieceRain(set, centreX, this.y);
    }

    // 0 - checkered
    // 1 - striped
    public static SquarePieceRain generate(int type) {
        Blok b1 = Blok.white(0, 0), b2, b3 = Blok.black(0, 1), b4;
        if (type == 0) {
            b2 = Blok.black(1, 0);
        } else {
            b2 = Blok.white(1, 0);
        }
        if (type == 0) {
            b4 = Blok.white(1, 1);
        } else {
            b4 = Blok.black(1, 1);
        }
        return new SquarePieceRain(new Blok[] {b1, b2, b3, b4});
    }

    // 0 - solid
    // 1 - L-shaped
    public static SquarePieceRain generate(BiFunction<Integer, Integer, Blok> mainColor, 
            BiFunction<Integer, Integer, Blok> altColor, int type) {
        Blok b1 = mainColor.apply(0, 0), b2, b3 = mainColor.apply(0, 1), b4 = mainColor.apply(1, 1);
        if (type == 0) {
            b2 = mainColor.apply(1, 0);
        } else {
            b2 = altColor.apply(1, 0);
        }
        return new SquarePieceRain(new Blok[] {b1, b2, b3, b4});
    }

    @Override
    public String toString() {
        return this.bloks[0] + " " + this.bloks[1] + "\n" + this.bloks[2] + " " + this.bloks[3];
    }
}
