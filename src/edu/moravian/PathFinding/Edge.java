package edu.moravian.PathFinding;

import edu.moravian.math.Point2D;

public class Edge {
    private Point2D p1;
    private Point2D p2;

    public Edge(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point2D getP1() {
        return p1;
    }

    public Point2D getP2() {
        return p2;
    }
}