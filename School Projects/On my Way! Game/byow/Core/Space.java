package byow.Core;

public class Space {

    public Space upRoom;
    public Space downRoom;
    public Space leftRoom;
    public Space rightRoom;
    public Point upPoint;
    public Point downPoint;
    public Point leftPoint;
    public Point rightPoint;
    public Point center;

    Space() {
    }

    Space(Point origin, int hor, int ver) {
        center = origin;
        upPoint = new Point(origin.x, ver + origin.y);
        downPoint = new Point(origin.x, origin.y - ver);
        leftPoint = new Point(origin.x - hor, origin.y);
        rightPoint = new Point(origin.x + hor, origin.y);
    }
}
