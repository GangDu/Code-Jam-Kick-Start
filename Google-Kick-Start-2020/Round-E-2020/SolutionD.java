import java.util.*;
import java.io.*;

public class Solution {
    private int N, M, S, R;
    private int[] K, product;
    private int[][] streets, city, recipes;
    private List<Integer>[] adj, recipeAdj;
    private long[][] cost;
    private static final long MAX = (long) 1e12;

    private long solution() {
        buildAdj();
        cost = new long[N][S];
        for (int i = 0; i < N; i++) {
            Arrays.fill(cost[i], MAX);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((x, y) -> {
            int[] a = decode(x);
            int[] b = decode(y);
            return Long.compare(cost[a[0]][a[1]], cost[b[0]][b[1]]);
        });
        for (int i = 0; i < N; i++) {
            for (int j: city[i]) {
                cost[i][j - 1] = 0;
                pq.add(encode(i, j - 1));
            }
        }
        while (!pq.isEmpty()) {
            int curr = pq.poll();
            int u = decode(curr)[0];
            int c = decode(curr)[1];
            for (int v: adj[u]) {
                if (cost[u][c] + 1 < cost[v][c]) {
                    cost[v][c] = cost[u][c] + 1;
                    pq.add(encode(v, c));
                }
            }
            for (int i: recipeAdj[c]) {
                long temp = 0;
                for (int j = 0; j < K[i]; j++) {
                    temp += cost[u][recipes[i][j] - 1];
                }
                int stoneType = product[i] - 1;
                if (temp < cost[u][stoneType]) {
                    cost[u][stoneType] = temp;
                    pq.add(encode(u, stoneType));
                }
            }
        }
        long res = Long.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            res = Math.min(res, cost[i][0]);
        }
        return res >= MAX ? -1 : res;
    }

    private void buildAdj() {
        adj = (List<Integer>[]) new List[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < 2 * M; i++) {
            int u = streets[i][0];
            int v = streets[i][1];
            adj[u].add(v);
        }
        recipeAdj = (List<Integer>[]) new List[S];
        for (int i = 0; i < S; i++) {
            recipeAdj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < K[i]; j++) {
                int c = recipes[i][j] - 1;
                recipeAdj[c].add(i);
            }
        }
    }

    private int encode(int u, int c) {
        return u * S + c;
    }

    private int[] decode(int val) {
        return new int[] {val / S, val % S};
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        M = sc.nextInt();
        S = sc.nextInt();
        R = sc.nextInt();
        streets = new int[2 * M][];
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            streets[2 * i] = new int[] {u, v};
            streets[2 * i + 1] = new int[] {v, u};
        }
        city = new int[N][];
        for (int i = 0; i < N; i++) {
            int c = sc.nextInt();
            city[i] = new int[c];
            for (int j = 0; j < c; j++) {
                city[i][j] = sc.nextInt();
            }
        }
        K = new int[R];
        product = new int[R];
        recipes = new int[R][];
        for (int i = 0; i < R; i++) {
            K[i] = sc.nextInt();
            recipes[i] = new int[K[i]];
            for (int j = 0; j < K[i]; j++) {
                recipes[i][j] = sc.nextInt();
            }
            product[i] = sc.nextInt();
        }
        long res = solution();
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