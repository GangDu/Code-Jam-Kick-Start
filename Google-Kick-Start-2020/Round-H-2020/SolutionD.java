import java.util.*;
import java.io.*;

public class Solution {
    private int N, Q;
    private String[] S;
    private int[] X, Y;
    private int[][] dist;
    private List<List<Integer>> adj;
    private int[][] adjFast, distFast;

    private int[] solutionNaive() {
        int[] ans = new int[Q];
        buildAdj();
        dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            shortest(i);
        }
        for (int i = 0; i < Q; i++) {
            int d = dist[X[i] - 1][Y[i] - 1];
            if (d != 0) {
                ans[i] = d;
            } else {
                ans[i] = -1;
            }
        }
        return ans;
    }

    private void buildAdj() {
        adj = new ArrayList<List<Integer>>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < N; i++) {
            String a = S[i];
            Set<Character> set = new HashSet<Character>();
            for (char c: a.toCharArray()) {
                set.add(c);
            }
            for (int j = i + 1; j < N; j++) {
                String b = S[j];
                for (char c: b.toCharArray()) {
                    if (set.contains(c)) {
                        adj.get(i).add(j);
                        adj.get(j).add(i);
                        break;
                    }
                }
            }
        }
    }

    private void shortest(int s) {
        Queue<Integer> que = new LinkedList<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        dist[s][s] = 1;
        visited.add(s);
        que.add(s);
        while (!que.isEmpty()) {
            int curr = que.poll();
            for (int u: adj.get(curr)) {
                if (visited.contains(u)) {
                    continue;
                }
                visited.add(u);
                que.add(u);
                dist[s][u] = dist[s][curr] + 1;
            }
        }
    }

    private int[] solutionFast() {
        int[] ans = new int[Q];
        buildAdjFast();
        distFast = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(distFast[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < 26; i++) {
            shortestFast(i);
        }
        // for (int i = 0; i < 26; i++) {
        //     for (int j = 0; j < 26; j++) {
        //         if (distFast[i][j] == Integer.MAX_VALUE) {
        //             System.out.print("0 ");
        //         } else {
        //             System.out.print(distFast[i][j] + " ");
        //         }
        //     }
        //     System.out.println();
        // }
        for (int i = 0; i < Q; i++) {
            ans[i] = Integer.MAX_VALUE;
            for (char c1: S[X[i] - 1].toCharArray()) {
                int u = c1 - 'A';
                for (char c2: S[Y[i] - 1].toCharArray()) {
                    int v = c2 - 'A';
                    ans[i] = Math.min(ans[i], distFast[u][v]);
                }
            }
            if (ans[i] == Integer.MAX_VALUE) ans[i] = -1;
        }
        return ans;
    }

    private void buildAdjFast() {
        adjFast = new int[26][26];
        for (int i = 0; i < N; i++) {
            for (char c1: S[i].toCharArray()) {
                int u = c1 - 'A';
                for (char c2: S[i].toCharArray()) {
                    int v = c2 - 'A';
                    adjFast[u][v] = 1;
                    adjFast[v][u] = 1;
                }
            }
        }
    }

    private void shortestFast(int s) {
        Queue<Integer> que = new LinkedList<Integer>();
        boolean[] visited = new boolean[26];
        distFast[s][s] = 2;
        que.add(s);
        while (!que.isEmpty()) {
            int u = que.poll();
            for (int i = 0; i < 26; i++) {
                if (adjFast[u][i] == 0 || distFast[s][i] != Integer.MAX_VALUE) continue;
                que.add(i);
                distFast[s][i] = distFast[s][u] + 1;
            }
        }
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        N = sc.nextInt();
        Q = sc.nextInt();
        S = new String[N];
        for (int i = 0; i < N; i++) {
            S[i] = sc.next();
        }
        X = new int[Q];
        Y = new int[Q];
        for (int i = 0; i < Q; i++) {
            X[i] = sc.nextInt();
            Y[i] = sc.nextInt();
        }
        // int[] res = solutionNaive();
        int[] res = solutionFast();
        for (int i = 0; i < Q; i++) {
            pw.print(res[i] + " ");
        }
        pw.println();
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        Q = 100;
        while (true) {
            S = new String[N];
            for (int i = 0; i < N; i++) {
                int len = rand.nextInt(20) + 1;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    sb.append((char) ('A' + rand.nextInt(26)));
                }
                S[i] = sb.toString();
            }
            X = new int[Q];
            Y = new int[Q];
            for (int i = 0; i < Q; i++) {
                Y[i] = rand.nextInt(N - 1) + 2;
                X[i] = rand.nextInt(Y[i] - 1) + 1;
            }
            int[] res1 = solutionNaive();
            int[] res2 = solutionFast();
            if (Arrays.equals(res1, res2)) {
                System.out.println("OK");
            } else {
                System.out.println(Arrays.toString(res1));
                System.out.println(Arrays.toString(res2));
                System.out.printf("N: %d, Q: %d\n", N, Q);
                System.out.println(Arrays.toString(S));
                System.out.printf("X: %s\n", Arrays.toString(X));
                System.out.printf("Y: %s\n", Arrays.toString(Y));
                break;
            }
        }
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