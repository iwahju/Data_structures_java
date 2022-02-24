
package edu.emory.cs.sort.distribution;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RadixSortQuiz extends RadixSort {

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sortHelper(array, beginIndex, endIndex, maxBit);
    }

    private void sortHelper(Integer[] array, int beginIndex, int endIndex, int digit) {
        if (digit < 1) return;
        int base = 10;
        buckets = Stream.generate(ArrayDeque<Integer>::new).limit(base).collect(Collectors.toList());
        for (int i = beginIndex; i < endIndex; i++) {
            int div = (int) (array[i] / Math.pow(base, digit - 1)) % base;
            buckets.get(div).add(array[i]);
        }
        int idx = beginIndex;
        for (Deque<Integer> bucket : buckets) {
            for (Integer val : bucket) {
                array[idx++] = val;
            }
        }
        idx = beginIndex;
        for (Deque<Integer> bucket : buckets) {
            sortHelper(array, idx, idx + bucket.size(), digit - 1);
            idx += bucket.size();
        }
    }
}