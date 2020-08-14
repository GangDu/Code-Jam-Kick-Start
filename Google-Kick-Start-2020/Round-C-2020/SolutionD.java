import java.util.*;
import java.io.*;

public class Solution {
    private int N, Q;
    private int[] A;
    private int[][] oper;
    private char[] type;
    private long[] S, MS;

    private long solutionNaive() {
        S = new long[N + 1];
        MS = new long[N + 1];
        computPrefixSum(S, MS);
        long res = 0;
        for (int i = 0; i < Q; i++) {
            int a = oper[i][0];
            int b = oper[i][1];
            if (type[i] == 'U') {
                update(a, b);
            } else {
                long temp = query(a, b);
                res += temp;
            }
        }
        return res;
    }

    private void computPrefixSum(long[] S, long[] MS) {
        int sign = 1;
        for (int i = 1; i <= N; i++) {
            S[i] = S[i - 1] + sign * A[i];
            MS[i] = MS[i - 1] + sign * A[i] * i;
            sign = -sign;
        }
    }

    private void update(int x, int v) {
        A[x] = v;
        computPrefixSum(S, MS);
    }

    private long query(int l, int r) {
        long res = 0;
        int sign = (int) Math.pow(-1, l-1);
        res = sign * (MS[r] - MS[l-1] - (l-1) * (S[r] - S[l-1]));
        return res;
    }

    private long solutionFast() {
        long[] st = new long[N];
        long[] mst = new long[N];
        int sign = 1;
        for (int i = 1; i <= N; i++) {
            st[i-1] = sign * A[i];
            mst[i-1] = sign * A[i] * i;
            sign = -sign;
        }
        long res = 0;
        SegmentTree ST = new SegmentTree(st);
        SegmentTree MST = new SegmentTree(mst);
        for (int i = 0; i < Q; i++) {
            int a = oper[i][0];
            int b = oper[i][1];
            sign = (int) Math.pow(-1, a - 1);
            if (type[i] == 'U') {
                ST.modify(a - 1, sign * b);
                MST.modify(a - 1, sign * b * a);
            } else {
                long temp = sign * (MST.query(a-1, b) - (a - 1) * ST.query(a-1, b));
                res += temp;
            }
        }
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        Q = sc.nextInt();
        A = new int[N + 1];
        type = new char[Q];
        oper = new int[Q][2];
        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
        }
        for (int i = 0; i < Q; i++) {
            type[i] = sc.next().charAt(0);
            oper[i][0] = sc.nextInt();
            oper[i][1] = sc.nextInt();
        }
        // long res = solutionNaive();
        long res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 20, val = 100;
        while (true) {
            N = rand.nextInt(n) + 1;
            Q = rand.nextInt(n) + 1;
            A = new int[N + 1];
            type = new char[Q];
            oper = new int[Q][2];
            for (int i = 1; i <= N; i++) {
                A[i] = rand.nextInt(val) + 1;
            }
            for (int i = 0; i < Q; i++) {
                type[i] = rand.nextInt(10) > 3 ? 'Q' : 'U';
                if (type[i] == 'Q') {
                    oper[i][1] = rand.nextInt(N) + 1;
                    oper[i][0] = rand.nextInt(oper[i][1]) + 1;
                } else {
                    oper[i][0] = rand.nextInt(N) + 1;
                    oper[i][1] = rand.nextInt(val) + 1;
                }
            }
            long res2 = solutionFast();
            long res1 = solutionNaive();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                print();
                break;
            }
        }
    }

    public void print() {
        System.out.printf("N: %d, Q: %d\n", N, Q);
        System.out.println(Arrays.toString(A));
        for (int i = 0; i < Q; i++) {
            System.out.printf("%c %d %d\n", type[i], oper[i][0], oper[i][1]);
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

class SegmentTree {
    private int n;
    private long[] t;

    public SegmentTree(long[] arr) {
        n = arr.length;
        t = new long[2 * n];
        build(arr);
    }

    private void build(long[] arr) {
        for (int i = 0; i < n; i++) {
            t[n + i] = arr[i];
        }
        for (int i = n - 1; i > 0; i--) {
            t[i] = t[i<<1] + t[i<<1|1];
        }
    }

    public void modify(int p, int value) {
        for (t[p += n] = value; p > 1; p >>= 1) {
            t[p>>1] = t[p] + t[p^1];
        }
    }

    public long query(int l, int r) {
        long res = 0;
        for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) == 1) res += t[l++];
            if ((r & 1) == 1) res += t[--r];
        }
        return res;
    }

    public String toString() {
        return Arrays.toString(t);
    }
}