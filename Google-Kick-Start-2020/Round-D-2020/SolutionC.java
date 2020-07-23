import java.util.*;
import java.io.*;

public class Solution {
    private int N, A, B;
    private int[] T;
    private List<Integer>[] adj;
    private int[] visitsA, visitsB;

    private double solutionNaive() {
        long res = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int temp = compute(i, j);
                res += temp;
            }
        }
        return res / Math.pow(N, 2);
    }

    private int compute(int a, int b) {
        Set<Integer> painted = new HashSet<Integer>();
        painted.add(a);
        painted.add(b);
        int curr = a - 2;
        int step = 0;
        while (curr != -1) {
            curr = T[curr] - 2;
            step++;
            if (step % A == 0) painted.add(curr + 2);
        }
        curr = b - 2;
        step = 0;
        while (curr != -1) {
            curr = T[curr] - 2;
            step++;
            if (step % B == 0) painted.add(curr + 2);
        }
        return painted.size();
    }

    private double solutionFast() {
        buildAdj();
        visitsA = new int[N];
        visitsB = new int[N];
        List<Integer> path = new ArrayList<Integer>();
        dfs(0, path);
        double res = 0;
        for (int i = 0; i < N; i++) {
            res += (double) (visitsA[i] + visitsB[i]) * N;
            res -= (double) visitsA[i] * visitsB[i];
        }
        return res / N / N;
    }

    private void buildAdj() {
        adj = (List<Integer>[]) new List[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 1; i < N; i++) {
            adj[T[i-1]-1].add(i);
        }
    }

    private void dfs(int u, List<Integer> path) {
        path.add(u);
        visitsA[u] = 1;
        visitsB[u] = 1;
        for (int v: adj[u]) {
            dfs(v, path);
        }
        path.remove(path.size() - 1);
        if (path.size() >= A) visitsA[path.get(path.size() - A)] += visitsA[u];
        if (path.size() >= B) visitsB[path.get(path.size() - B)] += visitsB[u];
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        T = new int[N-1];
        for (int i = 0; i < N - 1; i++) {
            T[i] = sc.nextInt();
        }
        // double res = solutionNaive();
        double res = solutionFast();
        pw.printf("%.12f\n", res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        while (true) {
            A = rand.nextInt(1) + 1;
            B = rand.nextInt(1) + 1;
            T = new int[N-1];
            for (int i = 0; i < N - 1; i++) {
                T[i] = rand.nextInt(i + 1) + 1;
            }
            double res1 = solutionNaive();
            double res2 = solutionFast();
            // System.out.println("Run: " + res2);
            if (Math.abs(res1 - res2) < 1e-6) {
                System.out.println("OK");
                System.out.printf("res1: %f, res2: %f\n", res1, res2);
            } else {
                System.out.println("Wrong Answer");
                System.out.printf("res1: %f, res2: %f\n", res1, res2);
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                try {
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
                } finally {
                }
            }
        }, "1", 1 << 26).start();
    }
}