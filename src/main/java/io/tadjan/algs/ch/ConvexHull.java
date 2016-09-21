package io.tadjan.algs.ch;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import static java.util.Comparator.comparing;

public class ConvexHull {

    public static Deque<Point> getHull(Set<Point> points) {
        Deque<Point> hull = new ArrayDeque<>();
        if (!points.isEmpty()) {
            Point sentinel = points.stream()
                    .min(comparing(Point::getY).thenComparing(Point::getX)).get();
            Point[] sortedPoints = points.stream()
                    .sorted(comparing(sentinel::getPolarAngle).thenComparing(Point::getX).thenComparing(Point::getY))
                    .toArray(Point[]::new);

            hull.push(sentinel);
            if (sortedPoints.length > 1) {
                hull.push(sortedPoints[1]);
                if (sortedPoints.length > 2) {
                    for (int i = 2; i < sortedPoints.length; i++) {
                        adjust(hull, sortedPoints[i]);
                        hull.push(sortedPoints[i]);
                    }
                    if (hull.size() > 2) {
                        adjust(hull, sentinel);
                    }
                }
            }
        }
        return hull;
    }

    private static void adjust(Deque<Point> hull, Point c) {
        Point b = hull.pop();
        while (!hull.isEmpty() && !isTurnCounterClockwise(hull.peek(), b, c)) {
            b = hull.pop();
        }
        hull.push(b);
    }

    private static boolean isTurnCounterClockwise(Point a, Point b, Point c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX()) > 0;
    }
}
