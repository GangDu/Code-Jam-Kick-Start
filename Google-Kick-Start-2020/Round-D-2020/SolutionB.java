import java.util.*;
import java.io.*;

public class Solution {
    private int K;
    private int[] A;

    private int solutionNaive() {
        int[] converted = new int[K];
        return traverse(converted, 0);
    }

    private int traverse(int[] converted, int k) {
        int res = Integer.MAX_VALUE;
        if (k == K) {
            return count(converted);
        }
        for (int i = 0; i < 4; i++) {
            converted[k] = i;
            res = Math.min(res, traverse(converted, k + 1));
        }
        return res;
    }

    private int count(int[] converted) {
        int res = 0;
        for (int i = 1; i < K; i++) {
            if (A[i] > A[i-1] && converted[i] <= converted[i-1]) res++;
            if (A[i] < A[i-1] && converted[i] >= converted[i-1]) res++;
            if (A[i] == A[i-1] && converted[i] != converted[i-1]) res++;
        }
        return res;
    }

    private int solutionDP() {
        int[][] dp = new int[K][4];
        for (int i = 1; i < K; i++) {
            for (int j = 0; j < 4; j++) {
                dp[i][j] = dp[i-1][0] + computeP(i, 0, j);
                for (int jj = 1; jj < 4; jj++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][jj] + computeP(i, jj, j));
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            res = Math.min(res, dp[K-1][i]);
        }
        return res;
    }

    private int computeP(int i, int jj, int j) {
        if (A[i] > A[i-1] && j <= jj) return 1;
        if (A[i] < A[i-1] && j >= jj) return 1;
        if (A[i] == A[i-1] && j != jj) return 1;
        return 0;
    }

    private int solutionGreedy() {
        int upward = 0;
        int downward = 0;
        int res = 0;
        for (int i = 1; i < K; i++) {
            if (A[i] == A[i-1]) continue;
            if (A[i] > A[i-1]) {
                upward++;
                downward = 0;
            } else {
                upward = 0;
                downward++;
            }
            if (upward > 3 || downward > 3) {
                res++;
                upward = downward = 0;
            }
        }
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        K = sc.nextInt();
        A = new int[K];
        for (int i = 0; i < K; i++) {
            A[i] = sc.nextInt();
        }
        // long res = solutionNaive();
        // long res = solutionDP();
        long res = solutionGreedy();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        while (true) {
            K = rand.nextInt(1000) + 1;
            A = new int[K];
            for (int i = 0; i < K; i++) {
                A[i] = rand.nextInt(10) + 1;
            }
            int res1 = solutionDP();
            int res2 = solutionGreedy();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
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