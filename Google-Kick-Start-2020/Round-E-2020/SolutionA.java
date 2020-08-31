import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] A;

    private int solutionNaive() {
        int res = 2;
        int diff = A[0] - A[1], count = 2;
        for (int i = 1; i < N - 1; i++) {
            int temp = A[i] - A[i + 1];
            if (diff != temp) {
                res = Math.max(res, count);
                count = 2;
                diff = temp;
            } else {
                count++;
            }
        }
        res = Math.max(res, count);
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }
        int res = solutionNaive();
        pw.println(res);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
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