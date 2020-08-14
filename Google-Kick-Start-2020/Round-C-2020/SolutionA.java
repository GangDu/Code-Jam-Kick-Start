import java.util.*;
import java.io.*;

public class Solution {
    private int N, K;
    private int[] A;

    private int solutionNaive() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            if (check(i)) res++;
        }
        return res;
    }

    private boolean check(int pos) {
        int k = K;
        for (int i = pos; i < N; i++) {
            if (A[i] == k) {
                k--;
            } else {
                break;
            }
            if (k == 0) {
                return true;
            }
        }
        return false;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        K = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
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