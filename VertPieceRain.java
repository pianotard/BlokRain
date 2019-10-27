public class VertPieceRain extends AbstractPieceRain {

    protected VertPieceRain(Blok[] bloks) {
        super(bloks);
    }

    protected VertPieceRain(Blok[] bloks, int x, int y) {
        super(bloks, x, y);
    }

    @Override
    public AbstractPieceRain rotateLeft() {
        Blok[] rotated = new Blok[4];
        rotated[0] = this.bloks[0].translate(-2, 1);
        rotated[1] = this.bloks[1].translate(-1, 0);
        rotated[2] = this.bloks[2].translate(0, -1);
        rotated[3] = this.bloks[3].translate(1, -2);
        AbstractPieceRain lpr = new LongPieceRain(rotated, this.x - 2, this.y + 1);
        if (lpr.x < 0) {
            lpr = lpr.translate(2, 0);
        } else if (lpr.x + 4 >= Grid.WIDTH) {
            lpr = lpr.translate(-1, 0);
        }
        return lpr;
    }


    @Override
    public AbstractPieceRain rotateRight() {
        Blok[] rotated = new Blok[4];
        rotated[0] = this.bloks[3].translate(-1, -2);
        rotated[1] = this.bloks[2].translate(0, -1);
        rotated[2] = this.bloks[1].translate(1, 0);
        rotated[3] = this.bloks[0].translate(2, 1);
        AbstractPieceRain lpr = new LongPieceRain(rotated, this.x - 1, this.y + 1);
        if (lpr.x < 0) {
            lpr = lpr.translate(1, 0);
        } else if (lpr.x + 4 >= Grid.WIDTH) {
            lpr = lpr.translate(-2, 0);
        }
        return lpr;
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
        return new VertPieceRain(translated, this.x + x, this.y + y); 
    }

    @Override
    public int[] getColumnNumbers() {
        return new int[] {this.x};
    }

    @Override
    public Blok[] getBloks(int col) {
        if (col == this.x) {
            return this.bloks;
        }
        return null;
    }

    @Override
    public AbstractPieceRain setCentreX(int centreX) {
        Blok[] set = new Blok[4];
        for (int i = 0; i < 4; i++) {
            set[i] = this.bloks[i].setX(centreX);
        }
        return new VertPieceRain(set, centreX, this.y);
    }

    @Override
    public String toString() {
        return this.bloks[0] + "\n" + this.bloks[1] + "\n" + this.bloks[2] + "\n" + this.bloks[3];
    }
}
