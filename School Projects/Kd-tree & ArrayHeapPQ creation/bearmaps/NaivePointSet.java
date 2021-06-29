package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> lst;

    public NaivePointSet(List<Point> points) {
        lst = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point closestPoint = lst.get(0);
        Point p = new Point(x, y);
        for (int i = 1; i < lst.size(); i++) {
            if (Point.distance(lst.get(i), p) < Point.distance(closestPoint, p)) {
                closestPoint = lst.get(i);
            }
        }
        return closestPoint;
    }

    //@Source Code from lecture 19
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 5);
        Point p3 = new Point(4, 3);
        Point p4 = new Point(4, 9);
        Point p5 = new Point(6, 6);
        Point p6 = new Point(7, 2);
        Point p7 = new Point(8, 9);
        Point p8 = new Point(6, 7);
        NaivePointSet tree = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        Point j = tree.nearest(7.9, 8.9);
        System.out.println("X Values: " + j.getX() + " Y Value: " + j.getY());
    }
}
