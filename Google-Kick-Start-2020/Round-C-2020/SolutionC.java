import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] A;
    private int[] s;
    private static final int MAX = (int) 1e7;
    private int[] p = new int[MAX];

    private void preCompute() {
        s = new int[(int)Math.sqrt(MAX)];
        for (int i = 0; i < s.length; i++) {
            s[i] = i * i;
        }
    }

    private int solutionNaive() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            int temp = A[i];
            if (check(temp)) res++;
            for (int j = i + 1; j < N; j++) {
                temp += A[j];
                if (check(temp)) res++;
            }
        }
        return res;
    }

    private boolean check(int num) {
        int temp = (int) Math.sqrt(num);
        return (temp * temp == num);
    }

    private long solutionFast() {
        int offset = 0;
        int prefixSum = 0;
        for (int i = 0; i < N; i++) {
            prefixSum += A[i];
            offset = Math.min(prefixSum, offset);
        }
        offset = -offset;
        int[] res = new int[N];
        Arrays.fill(p, 0);
        p[offset] = 1;
        prefixSum = 0;
        for (int i = 0; i < N; i++) {
            prefixSum += A[i];
            for (int j: s) {
                int temp = prefixSum - j + offset;
                if (temp < 0) break;
                res[i] += p[temp];
            }
            p[prefixSum + offset]++;
        }
        long ans = 0;
        for (int i: res) {
            ans += i;
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }
        // long res = solutionNaive();
        long res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 100;
        while (true) {
            N = rand.nextInt(n) + 1;
            A = new int[N];
            for (int i = 0; i < N; i++) {
                A[i] = rand.nextInt(201) - 100;
            }
            long res1 = solutionNaive();
            long res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.println(Arrays.toString(A));
                break;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.preCompute();
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