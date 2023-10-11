package revenko.ru.classes;

public class Boundry {
    public int getXMin() {
        return xMin;
    }

    public int getYin() {
        return yMin;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public Boundry(int xMin, int yMin, int xMax, int yMax) {
        super();
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public boolean inRange(int x, int y) {
        return (x >= this.getXMin() && x <= this.getXMax()
                && y >= this.getYin() && y <= this.getYMax());
    }

    int xMin, yMin, xMax, yMax;
}
