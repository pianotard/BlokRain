public class LongPieceRain extends AbstractPieceRain {
    
    protected LongPieceRain(Blok[] bloks) {
        super(bloks);
    }

    public LongPieceRain(Blok[] bloks, int x, int y) {
        super(bloks, x, y);
    }

    @Override
    public AbstractPieceRain rotateLeft() {
        Blok[] rotated = new Blok[4];
        rotated[0] = this.bloks[3].translate(-2, -1);
        rotated[1] = this.bloks[2].translate(-1, 0);
        rotated[2] = this.bloks[1].translate(0, 1);
        rotated[3] = this.bloks[0].translate(1, 2);
        return new VertPieceRain(rotated, this.x + 1, this.y - 1);
    }

    @Override
    public AbstractPieceRain rotateRight() {
        Blok rotated[] = new Blok[4];
        rotated[0] = this.bloks[0].translate(2, -1);
        rotated[1] = this.bloks[1].translate(1, 0);
        rotated[2] = this.bloks[2].translate(0, 1);
        rotated[3] = this.bloks[3].translate(-1, 2);
        return new VertPieceRain(rotated, this.x + 2, this.y - 1);
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
        return new LongPieceRain(translated, this.x + x, this.y + y);
    }

    @Override
    public int[] getColumnNumbers() {
        return new int[] {this.x, this.x + 1, this.x + 2, this.x + 3};
    }

    @Override
    public Blok[] getBloks(int col) {
        int colIndex = col - this.x;
        return new Blok[] {this.bloks[colIndex]};
    }

    @Override
    public AbstractPieceRain setCentreX(int centreX) {
        Blok[] set = new Blok[4];
        set[0] = this.bloks[0].setX(centreX - 1);
        set[1] = this.bloks[1].setX(centreX);
        set[2] = this.bloks[2].setX(centreX + 1);
        set[3] = this.bloks[3].setX(centreX + 2);
        return new LongPieceRain(set, centreX - 1, this.y);
    }

    // 0 - white
    // 1 - black
    public static LongPieceRain generate(int color) {
        Blok[] bloks = new Blok[4];
        for (int i = 0; i < 4; i++) {
            if (color == 0) {
                bloks[i] = Blok.white(i, 1);
            } else {
                bloks[i] = Blok.black(i, 1);
            }
        }
        return new LongPieceRain(bloks);
    }

    @Override
    public String toString() {
        return this.bloks[0] + " " + this.bloks[1] + " " + this.bloks[2] + " " + this.bloks[3];
    }
}
