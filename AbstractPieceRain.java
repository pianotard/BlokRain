public abstract class AbstractPieceRain {

    protected Blok[] bloks;
    protected int x;
    protected int y;

    protected AbstractPieceRain(Blok[] bloks) {
        this.bloks = bloks;
        this.x = 0;
        this.y = 0;
    }

    protected AbstractPieceRain(Blok[] bloks, int x, int y) {
        this(bloks);
        this.x = x;
        this.y = y;
    }

    public abstract int[] getColumnNumbers();
    public abstract Blok[] getBloks(int col);
    public abstract AbstractPieceRain setCentreX(int centreX);
    public abstract AbstractPieceRain translate(int x, int y);
    public abstract AbstractPieceRain rotateLeft();
    public abstract AbstractPieceRain rotateRight();

    public boolean exceedsBounds() {
        for (Blok b : this.bloks) {
            if (b.exceedsBounds()) {
                return true;
            }
        }
        return false;
    }

    public Blok[] getBloks() {
        return this.bloks;
    }
}
