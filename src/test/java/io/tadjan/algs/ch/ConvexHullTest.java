package io.tadjan.algs.ch;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

public class ConvexHullTest {

    @Test
    public void empty() {
        test(asSet(), asDeque());
    }

    @Test
    public void one() {
        test(asSet(p(0, 0)), asDeque(p(0, 0)));
    }

    @Test
    public void two() {
        test(asSet(p(0, 0), p(1, 0)), asDeque(p(0, 0), p(1, 0)));
    }

    @Test
    public void three_collinear() {
        test(asSet(p(0, 0), p(1, 0), p(2, 0)), asDeque(p(0, 0), p(2, 0)));
    }

    @Test
    public void four_collinear() {
        test(asSet(p(0, 0), p(1, 1), p(2, 2), p(3, 3)), asDeque(p(0, 0), p(3, 3)));
    }

    @Test
    public void five_collinear() {
        test(asSet(p(0, 0), p(0, 1), p(0, 2), p(0, 3), p(0, 4)), asDeque(p(0, 0), p(0, 4)));
    }

    @Test
    public void triangle_3a() {
        test(asSet(
                p(0, 0), p(1, 0),
                p(0, 1)),
                asDeque(p(0, 0), p(1, 0), p(0, 1)));
    }

    @Test
    public void triangle_3b() {
        test(asSet(
                p(0, 0), p(1, 0),
                p(1, 1)),
                asDeque(p(0, 0), p(1, 0), p(1, 1)));
    }

    @Test
    public void triangle_3c() {
        test(asSet(
                p(1, 0),
                p(0, 1), p(1, 1)),
                asDeque(p(1, 0), p(1, 1), p(0, 1)));
    }

    @Test
    public void triangle_3d() {
        test(asSet(
                p(0, 0),
                p(0, 1), p(1, 1)),
                asDeque(p(0, 0), p(1, 1), p(0, 1)));
    }


    @Test
    public void triangle_6a() {
        test(asSet(
                p(0, 0), p(1, 0), p(2, 0),
                p(0, 1), p(1, 1),
                p(0, 2)),
                asDeque(p(0, 0), p(2, 0), p(0, 2)));
    }

    @Test
    public void triangle_6b() {
        test(asSet(
                p(0, 0), p(1, 0), p(2, 0),
                p(1, 1), p(2, 1),
                p(2, 2)),
                asDeque(p(0, 0), p(2, 0), p(2, 2)));
    }

    @Test
    public void triangle_6c() {
        test(asSet(
                p(2, 0),
                p(1, 1), p(2, 1),
                p(0, 2), p(1, 2), p(2, 2)),
                asDeque(p(2, 0), p(2, 2), p(0, 2)));
    }

    @Test
    public void triangle_6d() {
        test(asSet(
                p(0, 0), p(1, 0), p(2, 0),
                p(0, 1), p(1, 1), p(2, 1),
                p(0, 2), p(1, 2), p(2, 2)),
                asDeque(p(0, 0), p(2, 0), p(2, 2), p(0, 2)));
    }

    @Test
    public void square_4() {
        test(asSet(
                p(0, 0), p(1, 0),
                p(0, 1), p(1, 1)),
                asDeque(p(0, 0), p(1, 0), p(1, 1), p(0, 1)));
    }

    @Test
    public void square_9() {
        test(asSet(
                p(0, 0), p(1, 0), p(2, 0),
                p(0, 1), p(1, 1), p(2, 1),
                p(0, 2), p(1, 2), p(2, 2)),
                asDeque(p(0, 0), p(2, 0), p(2, 2), p(0, 2)));
    }

    @Test
    public void square_16() {
        test(asSet(
                p(0, 0), p(1, 0), p(2, 0), p(3, 0),
                p(0, 1), p(1, 1), p(2, 1), p(3, 1),
                p(0, 2), p(1, 2), p(2, 2), p(3, 2),
                p(0, 3), p(1, 3), p(2, 3), p(3, 3)
        ), asDeque(p(0, 0), p(3, 0), p(3, 3), p(0, 3)));
    }

    @Test
    public void pentagon_15c() {
        test(asSet(
                p(1, 0), p(2, 0), p(3, 0),
                p(0, 1), p(1, 1), p(2, 1), p(3, 1),
                p(0, 2), p(1, 2), p(2, 2), p(3, 2),
                p(0, 3), p(1, 3), p(2, 3), p(3, 3)
        ), asDeque(p(1, 0), p(3, 0), p(3, 3), p(0, 3), p(0, 1)));
    }

    @Test
    public void pentagon_13c() {
        test(asSet(
                p(2, 0), p(3, 0),
                p(1, 1), p(2, 1), p(3, 1),
                p(0, 2), p(1, 2), p(2, 2), p(3, 2),
                p(0, 3), p(1, 3), p(2, 3), p(3, 3)
        ), asDeque(p(2, 0), p(3, 0), p(3, 3), p(0, 3), p(0, 2)));
    }

    @Test
    public void triangle_13c() {
        test(asSet(
                p(3, 0),
                p(2, 1), p(3, 1),
                p(1, 2), p(2, 2), p(3, 2),
                p(0, 3), p(1, 3), p(2, 3), p(3, 3)
        ), asDeque(p(3, 0), p(3, 3), p(0, 3)));
    }

    private static void test(Set<Point> points, Deque<Point> expected) {
        Deque<Point> actual = ConvexHull.getHull(points);
        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(expected.containsAll(actual));
    }

    private static Point p(double x, double y) {
        return new Point(x, y);
    }

    private static Set<Point> asSet(Point... points) {
        return Arrays.stream(points).collect(Collectors.toSet());
    }

    private static Deque<Point> asDeque(Point... points) {
        return Arrays.stream(points).collect(Collectors.toCollection(ArrayDeque::new));
    }
}

