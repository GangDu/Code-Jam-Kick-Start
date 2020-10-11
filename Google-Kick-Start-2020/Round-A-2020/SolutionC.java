import java.util.*;
import java.io.*;

public class Solution {
    private int N, K;
    private int[] M;

    private int solutionNaive() {
        int[] dif = new int[N - 1];
        int maxDif = 0;
        int idx = 0;
        for (int i = 0; i < N - 1; i++) {
            dif[i] = M[i + 1] - M[i];
            if (maxDif < dif[i]) {
                maxDif = dif[i];
                idx = i;
            }
        }
        dif[idx] = maxDif % 2 == 0 ? maxDif/2 : maxDif/2 + 1;
        int res = 0;
        for (int i = 0; i < N - 1; i++) {
            res = Math.max(res, dif[i]);
        }

        return res;
    }

    private int solutionFast() {
        int[] diff = computeDiff();
        int low = 0;
        int high = (int) 1e9;
        while (low < high - 1) {
            int mid = low + (high - low) / 2;
            // System.out.printf("low: %d, high: %d, mid: %d\n", low, high, mid);
            if (satisfy(diff, mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return high;
    }

    private int[] computeDiff() {
        int[] diff = new int[N-1];
        for (int i = 1; i < N; i++) {
            diff[i-1] = M[i] - M[i-1];
        }
        return diff;
    }

    private boolean satisfy(int[] diff, int d) {
        int count = 0;
        for (int i = 0; i < diff.length; i++) {
            count += (int) Math.ceil(diff[i]*1.0 / d) - 1;
        }
        return count <= K;
    }

    private void stressTest() {
        Random rand = new Random(0);
        K = 1;
        int n = 1000;
        while (true) {
            N = rand.nextInt(n) + 2;
            M = new int[N];
            M[0] = rand.nextInt(n) + 1;
            for (int i = 1; i < N; i++) {
                M[i] = M[i-1] + rand.nextInt(n) + 1;
            }
            int res1 = solutionNaive();
            int res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.printf("N: %d, K: %d\n", N, K);
                System.out.println(Arrays.toString(M));
                break;
            }
        }
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        K = sc.nextInt();
        M = new int[N];
        for (int i = 0; i < N; i++) {
            M[i] = sc.nextInt();
        }
        // int res = solutionNaive();
        int res = solutionFast();
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