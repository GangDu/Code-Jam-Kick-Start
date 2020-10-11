import java.util.*;
import java.io.*;

public class Solution {
    private int N, M, K; 
    private int[] A;
    private Map<List<Integer>, Double> dp;
    private List<Integer> target;


    private double solutionNaive() {
        dp = new HashMap<List<Integer>, Double>();
        List<Integer> list = new ArrayList<Integer>();
        target = new ArrayList<Integer>();
        for (int i = 0; i < M; i++) {
            list.add(0);
            target.add(0);
        }
        for (int i = 0; i < K; i++) {
            target.set(M - K + i, A[i]);
        }
        dp.put(target, 0.0);
        double ans = dfs(list);
        // System.out.println(dp);
        return ans;
    }

    private double dfs(List<Integer> list) {
        if (dp.containsKey(list)) return dp.get(list);
        double cs = 0.0;
        int count = 0, s = 0;
        for (int i = 0; i < M; i++) {
            if (i == 0 || list.get(i) != list.get(i - 1)) {
                count = 1;
            } else {
                count++;
            }
            if ((i == M - 1 || list.get(i) != list.get(i + 1)) && list.get(i) < target.get(i)) {
                list.set(i, list.get(i) + 1);
                s += count;
                cs += dfs(list) * count;
                list.set(i, list.get(i) - 1);
            }
        }
        dp.put(new ArrayList<Integer>(list), 1.0 * M / s + cs / s);
        // System.out.println(list);
        // System.out.printf("s: %d, cs: %f\n", s, cs);
        return dp.get(list);
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        A = new int[K];
        for (int i = 0; i < K; i++) {
            A[i] = sc.nextInt();
        }
        double res = solutionNaive();
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