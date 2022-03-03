package edu.emory.cs.sort.hybrid;

import java.lang.reflect.Array;
import java.util.*;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    private final List<int[]> keyArray2 = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public T[] sort(T[][] input) {
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                keyArray2.add(new int[] {i, j});
            }
        }
        sort(input, 0, size);
        int beginIndex = 0;
        for (T[] t : input) {
            System.arraycopy(t, 0, output, beginIndex, t.length);
            beginIndex += t.length;
        }
        keyArray2.clear();
        return output;
    }

    protected void sort(T[][] array, int beginIndex, int endIndex) {
        if (beginIndex >= endIndex) return;
        int pivot = partition(array, beginIndex, endIndex);
        sort(array, beginIndex, pivot);
        sort(array, pivot + 1, endIndex);
    }

    protected int partition(T[][] array, int beginIndex, int endIndex) {
        int fst = beginIndex, snd = endIndex;
        int[] beginT = getIndex(beginIndex);
        T beginVal = array[beginT[0]][beginT[1]];
        while (true) {
            while (++fst < endIndex) {
                int[] idx = getIndex(fst);
                if (beginVal.compareTo(array[idx[0]][idx[1]]) < 0) {
                    break;
                }
            }
            while (--snd > beginIndex) {
                int[] idx = getIndex(snd);
                if (beginVal.compareTo(array[idx[0]][idx[1]]) > 0) {
                    break;
                }
            }
            if (fst >= snd) break;
            swap(array, fst, snd);
        }
        swap(array, beginIndex, snd);
        return snd;
    }

    private void swap(T[][] array, int idxA, int idxB) {
        int[] i = getIndex(idxA), j = getIndex(idxB);
        T tmp = array[i[0]][i[1]];
        array[i[0]][i[1]] = array[j[0]][j[1]];
        array[j[0]][j[1]] = tmp;
    }


    public int[] getIndex(int idx) {
        return  keyArray2.get(idx);
    }
}
