import java.util.*;
import java.io.*;

public class Solution {
    private int W, H, L, U, R, D;

    private double solutionNaive() {
        double[][] dp = new double[W+1][H+1];
        dp[1][1] = 1;
        for (int i = 1; i <= W; i++) {
            for (int j = 1; j <= H; j++) {
                if (i >= L && i <= R && j >= U && j <= D) {
                    dp[i][j] = 0;
                    continue;
                }
                if (i == 1 && j == 1) continue;
                double a = (j == H) ? dp[i-1][j] : dp[i-1][j] * 0.5;
                double b = (i == W) ? dp[i][j-1] : dp[i][j-1] * 0.5;
                dp[i][j] = a + b;
            }
        }
        return dp[W][H];
    }

    private double solutionFast() {
        if (H == 1 || W == 1) return 0.0;
        double[] nFactLog = preComputeNFactLog();
        double[] lastRow = new double[W + 1];
        double[] lastCol = new double[H + 1];

        for (int col = 1; col < W; col++) {
            lastRow[col] = lastRow[col-1] + 0.5 * computeProbOfSquare(H-1, col, nFactLog);
        }
        for (int row = 1; row < H; row++) {
            lastCol[row] = lastCol[row-1] + 0.5 * computeProbOfSquare(row, W-1, nFactLog);
        }
        // Compute bottom-left
        int row = D + 1;
        int col = L - 1;
        double p1 = 0.0;
        while (row <= H && col >= 1) {
            p1 += row == H ? lastRow[col] : computeProbOfSquare(row, col, nFactLog);
            row++; col--;
        }
        // compute top-right
        row = U - 1;
        col = R + 1;
        double p2 = 0.0;
        while (row >= 1 && col <= W) {
            p2 += col == W ? lastCol[row] : computeProbOfSquare(row, col, nFactLog);
            row--; col++;
        }
        return p1 + p2;
    }

    private double[] preComputeNFactLog() {
        double[] nFactLog = new double[W + H];
        for (int i = 1; i < nFactLog.length; i++) {
            nFactLog[i] = Math.log(i) / Math.log(2) + nFactLog[i-1];
        }
        return nFactLog;
    }

    private double computeProbOfSquare(int row, int col, double[] nFactLog) {
        double temp = nFactLog[row+col-2] - nFactLog[row-1] 
            - nFactLog[col-1] - (row+col-2);
        double p = Math.pow(2, temp);
        return p;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        W = sc.nextInt();
        H = sc.nextInt();
        L = sc.nextInt();
        U = sc.nextInt();
        R = sc.nextInt();
        D = sc.nextInt();
        // double res = solutionNaive();
        double res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 10;
        while (true) {
            W = rand.nextInt(n) + 1;
            H = rand.nextInt(n) + 1;
            D = rand.nextInt(H) + 1;
            U = rand.nextInt(D) + 1;
            R = rand.nextInt(W) + 1;
            L = rand.nextInt(R) + 1;
            double res1 = solutionNaive();
            double res2 = solutionFast();
            if (Math.abs(res1 - res2) <= 1e-6) {
                System.out.println("OK");
            } else {
                System.out.println("Wrong Answer");
                System.out.printf("res1: %f, res2: %f\n", res1, res2);
                System.out.printf("W: %d, H: %d\nL: %d, U: %d\nR: %d, D: %d\n"
                    , W, H, L, U, R, D);
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