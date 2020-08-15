import java.util.*;
import java.io.*;

public class Solution {
    private String s;
    private static final int MOD = (int) 1e9;


    private int[] solutionNaive() {
        int[] pos = {0, 0};
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                i = expand(i, pos);
            } else {
                move(c, pos);
            }
        }
        pos[0]++;
        pos[1]++;
        return pos;
    }

    private int expand(int start, int[] pos) {
        int n = s.charAt(start) - '0';
        int end = 0;
        for (int i = 0; i < n; i++) {
            int j = start + 2;
            while (s.charAt(j) != ')') {
                char c = s.charAt(j);
                if (Character.isDigit(c)) {
                    j = expand(j, pos);
                } else {
                    move(c, pos);
                }
                j++;
            }
            end = j;
        }
        return end;
    }

    private void move(char c, int[] pos) {
        switch (c) {
            case 'N': pos[1] = pos[1] - 1; break;
            case 'S': pos[1] = pos[1] + 1; break;
            case 'E': pos[0] = pos[0] + 1; break;
            default: pos[0] = pos[0] - 1;
        }
        pos[0] = (pos[0] + MOD) % MOD;
        pos[1] = (pos[1] + MOD) % MOD;
    }

    private int[] solutionFast() {
        int[] pos = {0, 0};
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                long[] d = {0, 0};
                i = evaluate(i, d);
                updatePos(pos, d);
            } else {
                move(c, pos);
            }
        }
        pos[0]++;
        pos[1]++;
        return pos;
    }

    private int evaluate(int start, long[] displace) {
        int n = s.charAt(start) - '0';
        int end = start + 2;
        while (s.charAt(end) != ')') {
            char c = s.charAt(end);
            if (Character.isDigit(c)) {
                long[] d = {0, 0};
                end = evaluate(end, d);
                displace[0] = displace[0] + n * d[0];
                displace[1] = displace[1] + n * d[1];
            }
            switch (c) {
                case 'N': displace[1] = displace[1] - n; break;
                case 'S': displace[1] = displace[1] + n; break;
                case 'E': displace[0] = displace[0] + n; break;
                case 'W': displace[0] = displace[0] - n; break;
            }
            displace[0] = displace[0] % MOD;
            displace[1] = displace[1] % MOD;
            end++;
        }
        return end;
    }

    private void updatePos(int[] pos, long[] displace) {
        pos[0] = (pos[0] + (int) displace[0]) % MOD;
        pos[1] = (pos[1] + (int) displace[1]) % MOD;
        pos[0] = (pos[0] + MOD) % MOD;
        pos[1] = (pos[1] + MOD) % MOD;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        s = sc.next();
        // int[] res = solutionNaive();
        int[] res = solutionFast();
        pw.println(res[0] + " " + res[1]);
    }

    private void generateBracket(StringBuilder sb, Random rand, char[] direct) {
        sb.append('(');
        int n = rand.nextInt(3) + 1;
        for (int i = 0; i < n; i++) {
            if (rand.nextInt(sb.length()) < 2) {
                generateBracket(sb, rand, direct);
            } else {
                sb.append(direct[rand.nextInt(4)]);
            }
        }
        sb.append(')');
    }

    private void stressTest() {
        Random rand = new Random(0);
        char[] direct = {'N', 'S', 'W', 'E'};
        int n = 10;
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (rand.nextInt(n) < 7) {
                    sb.append(direct[rand.nextInt(4)]);
                } else {
                    generateBracket(sb, rand, direct);
                }
            }
            s = sb.toString();
            int[] res1 = solutionNaive();
            int[] res2 = solutionFast();
            if (res1[0] == res2[0] && res1[1] == res2[1]) {
                System.out.println("OK");
            } else {
                System.out.println("Wrong Answer");
                System.out.println(s);
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