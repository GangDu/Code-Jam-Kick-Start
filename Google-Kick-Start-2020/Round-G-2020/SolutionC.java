import java.util.*;
import java.io.*;

public class Solution {
    private int W, N;
    private int[] X;
    private long[] pre;

    private long solutionNaive() {
        long ans = Long.MAX_VALUE;
        for (int i: X) {
            long temp = 0;
            for (int j = 0; j < W; j++) {
                int move = Math.abs(i - X[j]);
                move = Math.min(move, X[j] + N - i);
                move = Math.min(move, N - X[j] + i);
                temp += move;
            }
            // System.out.printf("i: %d, temp: %d\n", i, temp);
            ans = Math.min(ans, temp);
        }
        return ans;
    }

    private long solutionFast() {
        Arrays.sort(X);
        // System.out.println(Arrays.toString(X));
        pre = new long[W + 1];
        for (int i = 1; i <= W; i++) {
            pre[i] = pre[i - 1] + X[i - 1];
        }
        for (int i = 0; i < W; i++) {
            
        }
        // System.out.println(Arrays.toString(pre));
        long ans = Long.MAX_VALUE;
        for (int i = 0; i < W; i++) {
            long temp = 0;
            int p = findP(i);
            temp += 1L * (i - p + 1) * X[i] - getSum(p, i);
            if (p > 0) {
                temp += 1L * p * (N - X[i]) + getSum(0, p - 1);
            }
            int b = findB(i);
            temp += getSum(i, b - 1) - 1L * (b - i) * X[i];
            if (b < W) {
                temp += 1L * (W - b) * (N + X[i]) - getSum(b, W - 1);
            }
            ans = Math.min(ans, temp);
            // System.out.printf("i: %d, p: %d, b: %d, temp: %d\n", i, p, b, temp);
        }
        return ans;
    }

    private long getSum(int i, int j) {
        return pre[j + 1] - pre[i];
    }

    private int findP(int i) {
        int l = 0, r = i - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (X[i] - X[mid] < N - X[i] + X[mid]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    private int findB(int i) {
        int l = i + 1, r = W;
        while (l < r) {
            int mid = (l + r) / 2;
            if (X[mid] - X[i] < N - X[mid] + X[i]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        W = sc.nextInt();
        N = sc.nextInt();
        X = new int[W];
        for (int i = 0; i < W; i++) {
            X[i] = sc.nextInt();
        }
        // long res = solutionNaive();
        long res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        W = 50;
        while (true) {
            X = new int[W];
            for (int i = 0; i < W; i++) {
                X[i] = rand.nextInt(N) + 1;
            }
            long res1 = solutionNaive();
            long res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.printf("W: %d, N: %d\n", W, N);
                System.out.println(Arrays.toString(X));
                break;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        // sol.stressTest();
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
        int caseCnt = sc.nextInt();
        for (int caseNum = 0; caseNum < caseCnt; caseNum++) {
            pw.print("Case #" + (caseNum + 1) + ": ");
            sol.solve(sc, pw);
        }
        pw.flush();
        pw.close();
    }
}