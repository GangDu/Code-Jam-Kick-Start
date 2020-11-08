import java.util.*;
import java.io.*;

public class Solution {
    private String S;

    private long solutionNaive() {
        long ans = 0;
        String kick = "KICK", start = "START";
        List<Integer> kickList = new ArrayList<Integer>();
        List<Integer> startList = new ArrayList<Integer>();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'K') {
                boolean flag = true;
                for (int j = 1; j < kick.length(); j++) {
                    if (i + j >= S.length() || S.charAt(i + j) != kick.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) kickList.add(i);
            }
            if (S.charAt(i) == 'S') {
                boolean flag = true;
                for (int j = 1; j < start.length(); j++) {
                    if (i + j >= S.length() || S.charAt(i + j) != start.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) startList.add(i);
            }
        }
        // System.out.println(kickList);
        // System.out.println(startList);
        for (int i: kickList) {
            for (int j: startList) {
                if (i < j) ans++;
            }
        }
        return ans;
    }

    private long solutionFast() {
        long ans = 0;
        String kick = "KICK", start = "START";
        List<Integer> kickList = new ArrayList<Integer>();
        List<Integer> startList = new ArrayList<Integer>();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'K') {
                boolean flag = true;
                for (int j = 1; j < kick.length(); j++) {
                    if (i + j >= S.length() || S.charAt(i + j) != kick.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) kickList.add(i);
            }
            if (S.charAt(i) == 'S') {
                boolean flag = true;
                for (int j = 1; j < start.length(); j++) {
                    if (i + j >= S.length() && S.charAt(i + j) != start.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) startList.add(i);
            }
        }
        // System.out.println(kickList);
        // System.out.println(startList);
        for (int i = 0; i < kickList.size(); i++) {
            int key = kickList.get(i);
            int l = 0, r = startList.size();
            while (l < r) {
                int mid = (l + r) / 2;
                if (key <= startList.get(mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            ans += startList.size() - r;
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        S = sc.next();
        long res = solutionNaive();
        // long res = solutionFast();
        if (res == Long.MIN_VALUE) {
            pw.println("IMPOSSIBLE");
        } else {
            pw.println(res);
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