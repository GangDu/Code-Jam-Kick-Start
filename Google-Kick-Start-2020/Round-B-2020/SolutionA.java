import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] H;

    private int solutionNaive() {
        int res = 0;
        for (int i = 1; i < N - 1; i++) {
            if (H[i] > H[i-1] && H[i] > H[i+1]) {
                res++;
            }
        }
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        H = new int[N];
        for (int i = 0; i < N; i++) {
            H[i] = sc.nextInt();
        }
        int res = solutionNaive();
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