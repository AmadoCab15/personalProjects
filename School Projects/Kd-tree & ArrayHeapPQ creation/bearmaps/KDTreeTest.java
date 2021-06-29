package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*@Source Josh Hug Hint video*/

public class KDTreeTest {
    private static Random r = new Random(500);

    @Test
    public void testKD() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(1, 5);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(4, 4);
        KDTree tree = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
    }

    private Point rPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List points = new ArrayList();
        for (int i = 0; i < N; i++) {
            points.add(rPoint());
        }
        return points;
    }

    @Test
    public void testCorrectness() {
        helperCorrectness(10000, 500);
        helperCorrectness(50000, 3000);
        helperCorrectness(60000, 4000);
        helperCorrectness(100000, 5000);
    }

    private void helperCorrectness(int totalPoints, int totalQueries) {
        List<Point> points = randomPoints(totalPoints);
        NaivePointSet ns = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        List<Point> queries = randomPoints(totalQueries);
        for (Point p : queries) {
            Point ex = ns.nearest(p.getX(), p.getY());
            Point ac = kd.nearest(p.getX(), p.getY());
            Assert.assertEquals(ex, ac);
        }
    }

    @Test
    public void testKDConsRandom() {
        List pts = new ArrayList();
        List nnn = new ArrayList();
        List ttt = new ArrayList();
        List ooo = new ArrayList();
        Stopwatch w = new Stopwatch();
        int curr = 1000;
        for (int n = 0; n <= 2000000; n++) {
            int randomX = StdRandom.uniform(150);
            int randomY = StdRandom.uniform(150);
            Point p = new Point(randomX, randomY);
            pts.add(p);
            if (n == curr) {
                KDTree test0 = new KDTree(pts);
                double s = w.elapsedTime();
                nnn.add(n);
                ttt.add(s);
                ooo.add(n);
                curr *= 2;
                w = new Stopwatch();
            }

        }
        printTimingTable(nnn, ttt, ooo);
    }

    @Test
    public void testNear() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 5);
        Point p3 = new Point(4, 3);
        Point p4 = new Point(4, 9);
        Point p5 = new Point(6, 6);
        Point p6 = new Point(7, 2);
        Point p7 = new Point(8, 9);
        Point p8 = new Point(6, 7);
        Point p9 = new Point(7.9, 8.9);
        KDTree tree = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        Point j = tree.nearest(7.9, 8.9);
        System.out.println("X Values: " + j.getX() + " Y Value: " + j.getY());
    }

    @Test
    public void testNearTiming() {
        List points = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            int randomX = StdRandom.uniform(1000);
            int randomY = StdRandom.uniform(1000);
            Point p = new Point(randomX, randomY);
            points.add(p);
        }
        KDTree test = new KDTree(points);

        List nS = new ArrayList();
        List times = new ArrayList();
        List opCounts = new ArrayList();
        Stopwatch sw = new Stopwatch();
        int curr = 1000;
        for (int n = 0; n < 10000000; n++) {
            if (n == curr) {
                double sec = sw.elapsedTime();
                nS.add(n);
                times.add(sec);
                opCounts.add(n);
                curr *= 2;

            }
            test.nearest(6, 6.999);
        }
        printTimingTable(nS, times, opCounts);

        NaivePointSet test2 = new NaivePointSet(points);
        List no = new ArrayList();
        List t = new ArrayList();
        List op = new ArrayList();
        Stopwatch clock = new Stopwatch();
        int next = 1000;
        for (int i = 0; i < 10000000; i++) {
            if (i == next) {
                double seco = clock.elapsedTime();
                no.add(i);
                t.add(seco);
                op.add(i);
                next *= 2;

            }
            test2.nearest(6, 6.999);
        }
        printTimingTable(no, t, op);
    }

    //@Source function from lab5 by Josh Hug
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }
}


