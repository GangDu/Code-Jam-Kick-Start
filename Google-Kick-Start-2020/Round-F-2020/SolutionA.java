import java.util.*;
import java.io.*;

public class Solution {
    private int N, X; 
    private int[] A;

    private int[] solutionNaive() {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x, y) -> {
            if (x[1] == y[1]) {
                return Integer.compare(x[0], y[0]);
            } else {
                return Integer.compare(x[1], y[1]);
            }
        });
        for (int i = 0; i < N; i++) {
            int time = (A[i] + X - 1) / X;
            pq.add(new int[] {i + 1, time});
        }
        List<Integer> list = new ArrayList<Integer>();
        while (!pq.isEmpty()) {
            int[] arr = pq.poll();
            list.add(arr[0]);
        }
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        X = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }
        int[] res = solutionNaive();
        // int res = solutionFast();
        for (int i = 0; i < N; i++) {
            pw.print(res[i] + " ");
        }
        pw.println();
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