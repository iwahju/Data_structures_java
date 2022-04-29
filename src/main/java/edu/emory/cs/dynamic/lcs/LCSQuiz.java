package edu.emory.cs.dynamic.lcs;

import java.util.HashSet;
import java.util.Set;

public class LCSQuiz extends LCSDynamic {

    int[][] L = new int[0][0];

    public Set<String> solveAll(String a, String b) {
        L = createTable(a.toCharArray(), b.toCharArray());
        Set<String> res = f(a, b, a.length(), b.length());
        res.removeIf(x -> x.length() != L[a.length() - 1][b.length() - 1] + 1);
        return res;
    }

    private Set<String> f(String X, String Y, int m, int n) {
        Set<String> s = new HashSet<>();
        if (m == 0 || n == 0) {
            s.add("");
            return s;
        }
        if (X.charAt(m - 1) == Y.charAt(n - 1)) {
            Set<String> tmp = f(X, Y, m - 1, n - 1);
            for (String str : tmp) s.add(str + X.charAt(m - 1));
        } else {
            if (L[m - 1][n] >= L[m][n - 1]) {
                s = f(X, Y, m - 1, n);
            }
            if (L[m][n - 1] >= L[m - 1][n]) {
                Set<String> tmp = f(X, Y, m, n - 1);
                s.addAll(tmp);
            }
        }
        return s;
    }
}
