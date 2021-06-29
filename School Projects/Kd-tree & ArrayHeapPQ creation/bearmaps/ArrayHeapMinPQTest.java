package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//@Source Lecture/Hint videos by Josh Hug

public class ArrayHeapMinPQTest {
    private static Random r = new Random(500);

    @Test
    public void testArrayHeap() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        NaiveMinPQ correctTest = new NaiveMinPQ();
        test.add("a", 1);
        correctTest.add("a", 1);
        test.add("b", 2);
        correctTest.add("b", 2);
        test.add("c", 3);
        correctTest.add("c", 3);
        test.add("d", 4);
        correctTest.add("d", 4);
        test.add("e", 5);
        correctTest.add("e", 5);
        test.add("f", 6);
        correctTest.add("f", 6);
        test.add("g", 7);
        correctTest.add("g", 7);
        test.add("h", 8);
        correctTest.add("h", 8);
        test.add("i", 9);
        correctTest.add("i", 9);
        test.add("j", 10);
        correctTest.add("j", 10);
        test.add("k", 11);
        correctTest.add("k", 11);
        test.add("l", 7);
        correctTest.add("l", 7);
        test.add("m", 13);
        correctTest.add("m", 13);
        test.add("n", 14);
        correctTest.add("n", 14);
        test.add("o", 15);
        correctTest.add("o", 15);
        test.changePriority("c", 8);
        correctTest.changePriority("c", 8);
        Assert.assertEquals(correctTest.removeSmallest(), test.removeSmallest());
        test.changePriority("b", 13);
        correctTest.changePriority("b", 13);
        Assert.assertEquals(correctTest.removeSmallest(), test.removeSmallest());
    }

    @Test
    public void testAddEff() {
        int nCalls = 512000;
        System.out.println("Test add runtime");
        ExtrinsicMinPQ arrHeap = new ArrayHeapMinPQ();
        ExtrinsicMinPQ naiHeap = new NaiveMinPQ();
        System.out.println("ArrayHeap");
        calculateTimeAdd(arrHeap, nCalls);
        System.out.println("NaiveHeap");
        calculateTimeAdd(naiHeap, nCalls);
    }

    @Test
    public void testRemoveEff() {
        int nSize = 512000;
        System.out.println("Test remove runtime");
        System.out.println("ArrayHeap");
        calculateTimeRemove(nSize, "arrayHeap");
        System.out.println("NaiveHeap");
        calculateTimeRemove(nSize, "naiveHeap");
    }

    @Test
    public void testSizeEff() {
        int nSize = 512000;
        System.out.println("Test size runtime");
        System.out.println("ArrayHeap");
        calculateTimeSize(nSize, "arrayHeap");
        System.out.println("NaiveHeap");
        calculateTimeSize(nSize, "naiveHeap");
    }

    @Test
    public void testChangeEff() {
        int nSize = 512000;
        calculateTimeChange(nSize);
    }

    @Test
    public void testRemoveCorrectness() {
        int totalCalls = 10000;
        NaiveMinPQ naiveMinPQ = new NaiveMinPQ();
        ArrayHeapMinPQ arrayHeapMinPQ = new ArrayHeapMinPQ();
        List<Point> objects = randomPoints(totalCalls);
        for (Point o : objects) {
            double ra = r.nextDouble() * 2 - 1;
            naiveMinPQ.add(new Point(o.getX(), o.getY()), ra);
            arrayHeapMinPQ.add(o, ra);
        }
        for (int i = 0; i < totalCalls; i++) {
            double rrr = r.nextDouble() * 2 - 1;
            if (rrr > 0.5) {
                Assert.assertEquals(arrayHeapMinPQ.removeSmallest(), naiveMinPQ.removeSmallest());
            } else if (rrr > 0.25) {
                Assert.assertEquals(arrayHeapMinPQ.size(), naiveMinPQ.size());
            } else {
                Assert.assertEquals(arrayHeapMinPQ.getSmallest(), naiveMinPQ.getSmallest());
            }
        }
    }



    @Test
    public void testAddCorrectness() {
        int nSize = 10000;
        NaiveMinPQ naiveMinPQ = new NaiveMinPQ();
        ArrayHeapMinPQ arrayHeapMinPQ = new ArrayHeapMinPQ();
        List<Point> objects = randomPoints(nSize);
        for (Point o : objects) {
            double ra = r.nextDouble() * 2 - 1;
            naiveMinPQ.add(new Point(o.getX(), o.getY()), ra);
            arrayHeapMinPQ.add(o, ra);
            Assert.assertEquals(naiveMinPQ.getSmallest(), arrayHeapMinPQ.getSmallest());
            if (ra > 0.5) {
                Assert.assertEquals(naiveMinPQ.size(), arrayHeapMinPQ.size());
            }
        }
    }

    @Test
    public void testChangeCorrectness() {
        int nSize = 10000;
        NaiveMinPQ naiveMinPQ = new NaiveMinPQ();
        ArrayHeapMinPQ arrayHeapMinPQ = new ArrayHeapMinPQ();
        List<Point> objects = randomPoints(nSize);
        for (Point o : objects) {
            double ra = r.nextDouble() * 2 - 1;
            naiveMinPQ.add(new Point(o.getX(), o.getY()), ra);
            arrayHeapMinPQ.add(o, ra);
            double ran = r.nextDouble() * 2 - 1;
            arrayHeapMinPQ.changePriority(o, ran);
            naiveMinPQ.changePriority(o, ran);
            Assert.assertEquals(arrayHeapMinPQ.getSmallest(), naiveMinPQ.getSmallest());
            if (ran > 0.7) {
                arrayHeapMinPQ.changePriority(arrayHeapMinPQ.getSmallest(), ran);
                naiveMinPQ.changePriority(naiveMinPQ.getSmallest(), ran);
                Assert.assertEquals(arrayHeapMinPQ.removeSmallest(), naiveMinPQ.removeSmallest());
            }
        }
    }

    private void calculateTimeChange(int calls) {
        NaiveMinPQ naiveMinPQ = new NaiveMinPQ();
        ArrayHeapMinPQ arrayHeapMinPQ = new ArrayHeapMinPQ();
        List<Point> objects = randomPoints(calls);
        Stopwatch sw = new Stopwatch();
        List nS = new ArrayList();
        List times = new ArrayList();
        List opCounts = new ArrayList();
        List nn = new ArrayList();
        List t = new ArrayList();
        List oc = new ArrayList();
        for (Point o : objects) {
            double ra = r.nextDouble() * 2 - 1;
            naiveMinPQ.add(new Point(o.getX(), o.getY()), ra);
            arrayHeapMinPQ.add(o, ra);
        }
        System.out.println("Test changePriority runtime");
        System.out.println("ArrayHeap");
        int curr = 1000;
        int n = 0;
        int next = 1000;
        int k = 0;
        for (Object o : objects) {
            n++;
            double randomN = r.nextDouble() * 2 - 1;
            if (n == curr) {
                double sec = sw.elapsedTime();
                nS.add(n);
                times.add(sec);
                opCounts.add(n);
                curr *= 2;
            }
            arrayHeapMinPQ.changePriority(o, randomN);
        }
        printTimingTable(nS, times, opCounts);
        System.out.println("NaiveHeap");
        sw = new Stopwatch();
        for (Object o : objects) {
            k++;
            double randomN = r.nextDouble() * 2 - 1;
            if (k == next) {
                double s = sw.elapsedTime();
                nn.add(k);
                t.add(s);
                oc.add(k);
                next *= 2;
            }
            naiveMinPQ.changePriority(o, randomN);
        }
        printTimingTable(nn, t, oc);
    }

    private void calculateTimeAdd(ExtrinsicMinPQ heap, int calls) {
        Stopwatch sw = new Stopwatch();
        List nS = new ArrayList();
        List times = new ArrayList();
        List opCounts = new ArrayList();
        int curr = 1000;
        for (int n = 0; n <= calls; n++) {
            double randomN = r.nextDouble() * 2 - 1;
            if (n == curr) {
                double sec = sw.elapsedTime();
                nS.add(n);
                times.add(sec);
                opCounts.add(n);
                curr *= 2;
            }
            heap.add(rPoint(), randomN);
        }
        printTimingTable(nS, times, opCounts);
    }

    private void calculateTimeRemove(int calls, String str) {
        ExtrinsicMinPQ heap;
        if (str .equals("arrayHeap")) {
            heap = createArrayHeap(calls + 1);
        } else {
            heap = createHeap(calls + 1);
        }
        Stopwatch sw = new Stopwatch();
        List nS = new ArrayList();
        List times = new ArrayList();
        List opCounts = new ArrayList();
        int curr = 1000;
        for (int n = 0; n <= calls; n++) {
            if (n == curr) {
                double sec = sw.elapsedTime();
                nS.add(n);
                times.add(sec);
                opCounts.add(n);
                curr *= 2;
            }
            heap.removeSmallest();
        }
        printTimingTable(nS, times, opCounts);
    }

    private void calculateTimeSize(int calls, String str) {
        ExtrinsicMinPQ heap;
        if (str.equals("arrayHeap")) {
            heap = createArrayHeap(calls);
        } else {
            heap = createHeap(calls);
        }
        Stopwatch sw = new Stopwatch();
        List nS = new ArrayList();
        List times = new ArrayList();
        List opCounts = new ArrayList();
        int curr = 1000;
        for (int n = 0; n <= calls; n++) {
            if (n == curr) {
                double sec = sw.elapsedTime();
                nS.add(n);
                times.add(sec);
                opCounts.add(n);
                curr *= 2;
            }
            heap.size();
        }
        printTimingTable(nS, times, opCounts);
    }

    private ExtrinsicMinPQ createHeap(int totalHeaps) {
        NaiveMinPQ naiveMinPQ = new NaiveMinPQ();
        List<Point> objects = randomPoints(totalHeaps);
        for (Point o : objects) {
            double ra = r.nextDouble();
            naiveMinPQ.add(o, ra);
        }
        return naiveMinPQ;
    }

    private ArrayHeapMinPQ createArrayHeap(int totalHeaps) {
        ArrayHeapMinPQ arrayHeapMinPQ = new ArrayHeapMinPQ();
        List<Point> objects = randomPoints(totalHeaps);
        for (Point o : objects) {
            double ra = r.nextDouble() * 2 - 1;
            arrayHeapMinPQ.add(o, ra);
        }
        return arrayHeapMinPQ;
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

    //@Source function from lab5 by Josh Hug
    private static void printTimingTable(List<Integer> ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < ns.size(); i += 1) {
            int N = ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }
}
