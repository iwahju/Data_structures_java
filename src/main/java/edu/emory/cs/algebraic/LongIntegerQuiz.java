package edu.emory.cs.algebraic;

import java.util.Arrays;

/** @author Jinho D. Choi */
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) {
        super(n);
    }

    public LongIntegerQuiz(String n) {
        super(n);
    }

    @Override
    protected void addDifferentSign(LongInteger n) {

        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m];

        if (this.compareAbs(n) >= 0) {
            System.arraycopy(digits, 0, result, 0, digits.length);
            for (int i = 0; i < n.digits.length; i++) {
                result[i] -= n.digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }
        } else {
            System.arraycopy(n.digits, 0, result, 0, n.digits.length);
                for (int i = 0; i < digits.length; i++) {
                    result[i] -= digits[i];
                    if (result[i] < 0) {
                        result[i] += 10;
                        result[i + 1] -= 1;
                    }
                }
                sign = n.sign;
            }
            int k = m;
            while (result[k - 1] == 0) {
                if (k - 1 == 0) {
                    break;
                }
                k--;
            }
            digits = Arrays.copyOf(result, k);

        }
    }