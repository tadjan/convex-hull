package io.tadjan.algs.ch;

public class Point {

    private final double x;

    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPolarAngle(Point that) {
        return Math.atan2(that.y - y, that.x - x);
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(x) + Double.hashCode(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Point) {
            Point that = (Point) o;
            return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
