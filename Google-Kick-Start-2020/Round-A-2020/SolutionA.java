import java.util.*;
import java.io.*;

public class Solution {
    private int N, B;
    private int[] A;

    private int solutionNaive() {
        int res = 0;
        Arrays.sort(A);
        for (int i = 0; i < N; i++) {
            if (B >= A[i]) {
                res++;
                B -= A[i];
            } else {
                break;
            }
        }
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        B = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
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