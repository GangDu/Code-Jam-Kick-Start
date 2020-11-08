import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private long[] A, prefixSum;
    private long score, count;

    private double solutionNaive() {
        double ans = 0;
        // System.out.println(Arrays.toString(A));
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < N; i++) {
            list.add(A[i]);
        }
        score = count = 0;
        dfs(list);
        // System.out.println(score + ", " + count);
        ans = 1.0 * score / count;
        return ans;
    }

    private void dfs(List<Long> list) {
        if (list.size() == 1) {
            count++;
            return;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            List<Long> temp = new ArrayList<Long>();
            long merge = list.get(i) + list.get(i + 1);
            score += merge * fact(list.size() - 1);
            // System.out.println(merge);
            for (int j = 0; j < list.size(); j++) {
                if (j == i || j == i + 1) {
                    if (j == i) temp.add(merge);
                    continue;
                }
                temp.add(list.get(j));
            }
            // System.out.println(temp);
            dfs(temp);
        }
    }

    private long fact(int n) {
        long ans = 1;
        for (int i = 2; i < n; i++) {
            ans *= i;
        }
        return ans;
    }

    private double solutionFast() {
        double ans = 0;
        prefixSum = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            prefixSum[i] = A[i - 1] + prefixSum[i - 1];
        }
        double[][] dp = new double[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = getSum(i, i + 1);
        }
        for (int i = 2; i < N; i++) {
            for (int j = 0; j < N - i; j++) {
                for (int k = j; k < j + i; k++) {
                    dp[j][j + i] += dp[j][k] + dp[k + 1][j + i];
                }
                dp[j][j + i] /= i;
                dp[j][j + i] += getSum(j, j + i);
            }
            
        }
        return dp[0][N - 1];
    }

    private double solutionFastOpt() {
        double ans = 0;
        prefixSum = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            prefixSum[i] = A[i - 1] + prefixSum[i - 1];
        }
        // System.out.println(Arrays.toString(prefixSum) + "\n");
        double[][] dp = new double[N][N];
        double[] rowSum = new double[N];
        double[] colSum = new double[N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = getSum(i, i + 1);
            rowSum[i] = dp[i][i + 1];
            colSum[i + 1] = dp[i][i + 1];
        }
        for (int i = 2; i < N; i++) {
            for (int j = 0; j < N - i; j++) {
                dp[j][j + i] = rowSum[j] + colSum[j + i];
                dp[j][j + i] /= i;
                dp[j][j + i] += getSum(j, j + i);
                rowSum[j] += dp[j][j + i];
                colSum[j + i] += dp[j][j + i];
            }
            
        }
        // for (int i = 0; i < N; i++) {
        //     System.out.println(Arrays.toString(dp[i]));
        // }
        return dp[0][N - 1];
    }

    private long getSum(int i, int j) {
        return prefixSum[j + 1] - prefixSum[i];
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        N = sc.nextInt();
        A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
        }
        // double res = solutionNaive();
        // double res = solutionFast();
        double res = solutionFastOpt();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        while (true) {
            A = new long[N];
            for (int i = 0; i < N; i++) {
                A[i] = rand.nextInt(1000) + 1;
            }
            // double res1 = solutionNaive();
            double res1 = solutionFast();
            double res2 = solutionFastOpt();
            if (Math.abs(res1 - res2) < 1e-6) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %f, res2: %f\n", res1, res2);
                System.out.println(Arrays.toString(A));
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