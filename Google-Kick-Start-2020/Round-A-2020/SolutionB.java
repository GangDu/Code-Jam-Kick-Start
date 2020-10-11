import java.util.*;
import java.io.*;

public class Solution {
    private int N, K, P;
    private int[][] beauty;

    private int solutionNaive() {
        int[][] prefixSum = computePrefixSum();
        int res = traverse(prefixSum, 0, 0, 0, 0);
        return res;
    }

    private int traverse(int[][] a, int idx, int n, int val, int res) {
        if (n == P) return val;
        if (idx == N) return val;
        for (int i = 0; i <= K; i++) {
            if (n + i > P) break;
            int newVal = val + a[idx][i];
            int temp = traverse(a, idx + 1, n + i, newVal, res);
            res = Math.max(res, temp);
        }
        return res;
    }

    private int[][] computePrefixSum() {
        int[][] prefixSum = new int[N][K + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= K; j++) {
                prefixSum[i][j] = prefixSum[i][j - 1] + beauty[i][j - 1];
            }
        }
        return prefixSum;
    }

    private int solutionFast() {
        int[][] prefixSum = computePrefixSum();
        int[][] dp = new int[N][P + 1];
        for (int j = 1; j <= P && j <= K; j++) {
            dp[0][j] = prefixSum[0][j];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= P && j <= (i+1)*K; j++) {
                for (int k = 0; k <= j && k <= K; k++) {
                    int temp = prefixSum[i][k] + dp[i-1][j-k];
                    dp[i][j] = Math.max(dp[i][j], temp);
                }
            }
        }
        return dp[N-1][P];
    }

    private void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        K = sc.nextInt();
        P = sc.nextInt();
        beauty = new int[N][K];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                beauty[i][j] = sc.nextInt();
            }
            
        }
        // int res = solutionNaive();
        int res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 5;
        while (true) {
            N = rand.nextInt(n) + 1;
            K = rand.nextInt(n) + 1;
            P = rand.nextInt(N*K) + 1;
            beauty = new int[N][K];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < K; j++) {
                    beauty[i][j] = rand.nextInt(n) + 1;
                }
            }
            int res1 = solutionNaive();
            int res2 = solutionFast();
            // System.out.println("run");
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.printf("N: %d, K: %d, P: %d\n", N, K, P);
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < K; j++) {
                        System.out.print(beauty[i][j] + " ");
                    }
                    System.out.println();
                }
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