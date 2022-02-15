
package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;
    private final int K = 3;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    private int compare(T x, T y) {
        return priority.compare(x, y);
    }

    @Override
    public void add(T key) {
        keys.add(key);
        heapifyUp(keys.size() - 1);
    }

    private void heapifyUp(int i) {
        if (i < 0) {
            return;
        }
        int ptr = (i - 1) / K;
        if (compare(keys.get(ptr), keys.get(i)) < 0) {
            Collections.swap(keys, ptr, i);
            heapifyUp(ptr);
        }
    }

    @Override
    public T remove() {
        if (size() == 0) {
            return null;
        }

        if (keys.size() == 1) {
            return keys.remove(0);
        }
        T res = keys.get(0);
        keys.set(0, keys.get(size() - 1));
        keys.remove(keys.size() - 1);
        heapifyDown(0);
        return res;
    }

    private void heapifyDown(int pos) {
        if ((K * pos + 1) > keys.size()) {
            return;
        }
        int ptr = K * pos + 1;
        for (int i = 2; i <= K; i++) {
            int j = K * pos + i;
            if (j < keys.size()) {
                if (compare(keys.get(j), keys.get(ptr)) > 0) {
                    ptr = j;
                }
            }
        }
        if ((ptr < keys.size() && pos < keys.size() && ptr >= 0 && pos >= 0)
                && compare(keys.get(ptr), keys.get(pos)) > 0) {
            Collections.swap(keys, ptr, pos);
            heapifyDown(ptr);
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public String toString() {
        return "TernaryHeapQuiz{" +
                "keys=" + keys +
                ", K=" + K +
                '}';
    }
}