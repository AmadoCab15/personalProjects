package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class InnerQ {
        private T ele;
        private double prior;

        InnerQ(T i, double p) {
            this.ele = i;
            this.prior = p;
        }

        private boolean containsK(T item) {
            return mapOfIDs.containsKey(item);
        }
    }

    private ArrayList<InnerQ> items;
    private HashMap<T, Integer> mapOfIDs = new HashMap<>();

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(new InnerQ((T) "sentinel", 0));

    }

    @Override
    public void add(T item, double priority) {
        if (items.get(0).containsK(item)) {
            throw new IllegalArgumentException("Can't add item that exists.");
        }
        items.add(new InnerQ(item, priority));
        mapOfIDs.put(item, items.size() - 1);
        diveUp(items.size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return items.get(0).containsK(item);
    }

    @Override
    public T getSmallest() {
        if (items.size() == 1) {
            throw new NoSuchElementException("The array is empty.");
        }
        return items.get(1).ele;
    }

    @Override
    public T removeSmallest() {
        if (items.size() == 1) {
            throw new NoSuchElementException("Can't remove smallest if empty.");
        }
        int lastIndex = items.size() - 1;
        InnerQ lastItem = items.get(lastIndex);
        T ret = items.get(1).ele;
        swap(items.get(1), lastItem, lastIndex, 1);
        items.remove(lastIndex);
        mapOfIDs.remove(ret);
        diveDown(1);
        return ret;
    }

    @Override
    public int size() {
        return items.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!items.get(0).containsK(item)) {
            throw new NoSuchElementException("Item not found");
        }
        int arrayLocation = mapOfIDs.get(item);
        double oldPrior = items.get(arrayLocation).prior;
        items.get(arrayLocation).prior = priority;
        if (priority > oldPrior) {
            diveDown(arrayLocation);
        } else {
            diveUp(arrayLocation);
        }
    }

    private void diveUp(int in) {
        InnerQ child = items.get(in);
        InnerQ par = items.get(in / 2);
        while (child.prior < par.prior && !par.ele.equals("sentinel")) {
            swap(par, child, mapOfIDs.get(child.ele), mapOfIDs.get(par.ele));
            par = items.get(mapOfIDs.get(child.ele) / 2);
        }
    }

    private void diveDown(int arrIndex) {
        InnerQ anyChild = childExists(arrIndex * 2);
        if (anyChild != null) {
            int bestRoute = childWithSmallestPrior(arrIndex);
            while (items.get(arrIndex).prior > items.get(bestRoute).prior) {
                InnerQ a = items.get(arrIndex);
                InnerQ b = items.get(bestRoute);
                swap(a, b, bestRoute, arrIndex);
                arrIndex = bestRoute;
                bestRoute = childWithSmallestPrior(arrIndex);
            }
        }
    }

    private InnerQ childExists(int index) {
        return index > items.size() - 1 ? null : items.get(index);
    }

    private int childWithSmallestPrior(int index) {
        int left = index * 2;
        int right = index * 2 + 1;
        if (left > items.size() - 1) {
            left = index;
        }
        if (right > items.size() - 1) {
            right = index;
        }
        if (items.get(left).prior < items.get(right).prior) {
            return left;
        }
        return right;
    }

    private void swap(InnerQ p, InnerQ q, int index1, int index2) {
        items.set(index1, p);
        items.set(index2, q);
        mapOfIDs.put(p.ele, index1);
        mapOfIDs.put(q.ele, index2);
    }
}
