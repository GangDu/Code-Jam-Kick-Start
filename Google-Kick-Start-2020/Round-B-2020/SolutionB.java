import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private long D;
    private long[] X;

    private long solutionNaive() {
        long[] arr = new long[N];
        long high = D;
        for (int i = N-1; i >= 0; i--) {
            arr[i] = (high / X[i]) * X[i];
            high = arr[i];
        }
        return arr[0];
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        D = sc.nextLong();
        X = new long[N];
        for (int i = 0; i < N; i++) {
            X[i] = sc.nextLong();
        }
        long res = solutionNaive();
        pw.println(res);
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