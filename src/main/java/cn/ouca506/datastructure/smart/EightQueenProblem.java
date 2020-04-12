package cn.ouca506.datastructure.smart;

import java.util.Arrays;

/**
 * 八皇后问题——递归调用
 * 记住回溯这个词
 * @author windmill666
 * @date 2020/4/7 20:31
 */

public class EightQueenProblem {

    private int count = 0;

    private int[] queens = new int[8];

    public void run() {
        placeQueen(0);
        System.out.println("共有的解法：" + count);
    }

    private void placeQueen(int n) {
        if (n == 8) {
            count++;
            System.out.println(Arrays.toString(queens));
            return;
        }
        for (int i = 0; i < 8; i++) {
            queens[n] = i + 1;
            if (checkCrush(n)) {
                placeQueen(n + 1);
            }
        }
    }

    private boolean checkCrush(int n) {
        for (int i = 0; i < n; i++) {
            if (queens[n] == queens[i] || Math.abs(queens[n] - queens[i]) == Math.abs(n - i)) {
                return false;//冲突
            }
        }
        return true;
    }
}
