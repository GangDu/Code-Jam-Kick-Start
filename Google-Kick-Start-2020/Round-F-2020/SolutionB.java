import java.util.*;
import java.io.*;

public class Solution {
    private int N, K; 
    private int[] S, E;

    private int solutionNaive() {
        int ans = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x, y) -> Integer.compare(x[0], y[0]));
        for (int i = 0; i < N; i++) {
            pq.add(new int[] {S[i], E[i]});
        }
        int calibrate = 0;
        while (!pq.isEmpty()) {
            int[] arr = pq.poll();
            if (calibrate >= arr[1]) {
                continue;
            } else if (calibrate < arr[0]) {
                int time = (arr[1] - arr[0] + K - 1) / K;
                calibrate = arr[0] + time * K;
                ans += time;
            } else {
                int time = (arr[1] - calibrate + K - 1) / K;
                calibrate = calibrate + time * K;
                ans += time;
            }
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        K = sc.nextInt();
        S = new int[N];
        E = new int[N];
        for (int i = 0; i < N; i++) {
            S[i] = sc.nextInt();
            E[i] = sc.nextInt();
        }
        int res = solutionNaive();
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