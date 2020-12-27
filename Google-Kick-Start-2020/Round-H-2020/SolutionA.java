import java.util.*;
import java.io.*;

public class Solution {
    private long N, K, S;

    private long solutionNaive() {
        long ans = 0;
        long option1 = K + N;
        long option2 = K + (K - S) + (N - S);
        // System.out.printf("opt1: %d, opt2: %d\n", option1, option2);
        ans = Math.min(option1, option2);
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        N = sc.nextInt();
        K = sc.nextInt();
        S = sc.nextInt();
        long res = solutionNaive();
        // int res = solutionFast();
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