package bearmaps;

import java.util.List;

public class KDTree {
    private static final boolean HORIZONTAL = true;

    private class Node {
        private Point point;
        private boolean orientation;
        private Node left;
        private Node right;

        Node(Point p, boolean o) {
            this.point = p;
            this.orientation = o;
        }
    }

    private Node map;

    public KDTree(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            map = addNewNode(map, points.get(i), HORIZONTAL);
        }
    }

    private Node addNewNode(Node p, Point point, boolean orientation) {
        if (p == null) {
            return new Node(point, orientation);
        }
        if (p.point == point) {
            return p;
        }
        double cmp = comparator(point, p.point, orientation);
        if (cmp < 0) {
            p.left = addNewNode(p.left, point, !p.orientation);
        } else {
            p.right = addNewNode(p.right, point, !p.orientation);
        }
        return p;
    }

    public Point nearest(double x, double y) {
        return nearestNode(map, map, new Point(x, y)).point;
    }

    private Node nearestNode(Node curr, Node bestNode, Point goal) {
        if (curr == null) {
            return bestNode;
        }
        if (Point.distance(curr.point, goal) < Point.distance(bestNode.point, goal)) {
            bestNode = curr;
        }
        int cmp = comparator(curr.point, goal, curr.orientation);
        Node goodSide, badSide;
        if (cmp < 0) {
            goodSide = curr.right;
            badSide = curr.left;
        } else {
            goodSide = curr.left;
            badSide = curr.right;
        }
        bestNode = nearestNode(goodSide, bestNode, goal);
        double alternate = possiblePath(curr.point, goal, curr.orientation);
        if ((alternate * alternate) < Point.distance(bestNode.point, goal)) {
            bestNode = nearestNode(badSide, bestNode, goal);
        }
        return bestNode;
    }

    private double possiblePath(Point a, Point b, boolean orientation) {
        if (HORIZONTAL == orientation) {
            return a.getX() - b.getX();
        } else {
            return a.getY() - b.getY();
        }
    }

    private int comparator(Point a, Point b, boolean orientation) {
        if (HORIZONTAL == orientation) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }
}
