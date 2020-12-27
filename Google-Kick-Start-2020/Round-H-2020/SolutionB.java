import java.util.*;
import java.io.*;

public class Solution {
    private long L, R;

    private long solutionNaive() {
        long ans = 0;
        for (long i = L; i <= R; i++) {
            if (isBoring(i)) {
                // System.out.println(i);
                ans++;
            }
        }
        return ans;
    }

    private long solutionFast() {
        long l = compute(L - 1);
        long r = compute(R);
        // System.out.printf("l: %d, r: %d\n", l, r);
        return r - l;
    }

    private boolean isBoring(long num) {
        String s = Long.toString(num, 10);
        // System.out.println(s);
        for (int i = 1; i <= s.length(); i++) {
            int digit = s.charAt(i - 1) - '0';
            if (i % 2 != digit % 2) return false;
        }
        return true;
    }

    private long compute(long num) {
        long ans = 0;
        String s = Long.toString(num, 10);
        for (int i = 1; i < s.length(); i++) {
            ans += Math.pow(5, i);
        }
        for (int i = 1; i <= s.length(); i++) {
            int digit = s.charAt(i - 1) - '0';
            boolean flag = true;
            for (int j = 0; j <= digit; j++) {
                if (i == 1 && j == 0) continue;
                if (j == digit && i % 2 != j % 2) {
                    flag = false;
                    break;
                }
                if (i % 2 != j % 2) continue;
                if (j == digit && i != s.length()) continue;
                ans += Math.pow(5, s.length() - i);
            }
            if (!flag) break;
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        L = sc.nextLong();
        R = sc.nextLong();
        // long res = solutionNaive();
        long res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        while (true) {
            R = rand.nextInt(1000) + 1;
            L = rand.nextInt((int) R) + 1;
            long res1 = solutionNaive();
            long res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("ok");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.printf("L: %d, R: %d\n", L, R);
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