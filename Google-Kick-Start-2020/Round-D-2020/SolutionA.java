import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] V;

    private long solutionNaive() {
        int res = 0;
        int maxVal = -1;
        for (int i = 0; i < N - 1; i++) {
            if (V[i] > maxVal && V[i] > V[i+1]) {
                res++;
            }
            maxVal = Math.max(maxVal, V[i]);
        }
        res += V[N-1] > maxVal ? 1 : 0;
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        V = new int[N];
        for (int i = 0; i < N; i++) {
            V[i] = sc.nextInt();
        }
        long res = solutionNaive();
        // long res = solutionFast();
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