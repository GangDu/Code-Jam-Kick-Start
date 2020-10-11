import java.util.*;
import java.io.*;

public class Solution {
    private int S, RA, PA, RB, PB, C; 
    private int[] R, P;
    private int[][] dir = new int[][] {{1, 1}, {-1, -1}, {0, 1}, {0, -1}};
    private boolean[][] visited;

    private int solutionNaive() {
        if (C == 2) {
            return 0;
        }
        if (RA == 2 && PA == 2 && C != 2) {
            return 1;
        }
        if (RB == 2 && PB == 2 & C != 2) {
            return -1;
        }
        if (C == 1 && R[0] == 2 && P[0] == 2) {
            return 0;
        }
        if (C == 1 && (R[0] != 2 || P[0] != 2) && (RA != 2 || PA != 2) && (RB != 2 || PB != 2)) {
            return 1;
        }
        if (C == 0 && (RA != 2 || PA != 2) && (RB != 2 || PB != 2)) {
            return 2;
        }
        return 0;
    }

    private int solutionFast() {
        visited = new boolean[S*S][S*S];
        for (int i = 0; i < C; i++) {
            visited[R[i]][P[i]] = true;
        }
        visited[RA][PA] = true;
        visited[RB][PB] = true;
        int[] temp = calcScore(RA, PA, RB, PB, true, false);
        // System.out.println(Arrays.toString(temp));
        return temp[0] - temp[1];
    }

    private int[] calcScore(int ra, int pa, int rb, int pb, boolean turn, boolean stop) {
        // System.out.printf("A: (%d, %d), B: (%d, %d), turn: %b, stop: %b\n", ra, pa, rb, pb, turn, stop);
        int x = turn ? ra : rb;
        int y = turn ? pa : pb;
        int[] ans = null;
        for (int i = 0; i < dir.length; i++) {
            if (y % 2 == 0 && i == 0) continue;
            if (y % 2 == 1 && i == 1) continue;
            int newX = x + dir[i][0];
            int newY = y + dir[i][1];
            if (newX < 1 || newY < 1 || newX > S || newY > 2 * newX - 1) continue;
            if (visited[newX][newY]) continue;
            visited[newX][newY] = true;
            if (turn) {
                int[] temp = calcScore(newX, newY, rb, pb, !turn, false);
                if (ans == null) ans = temp;
                if (temp[0] - temp[1] > ans[0] - ans[1]) ans = temp;
            } else {
                int[] temp = calcScore(ra, pa, newX, newY, !turn, false);
                if (ans == null) ans = temp;
                if (temp[0] - temp[1] < ans[0] - ans[1]) ans = temp;
            }
            visited[newX][newY] = false;
        }
        if (ans == null && stop) return new int[] {0, 0};
        if (ans == null) {
            int[] temp = calcScore(ra, pa, rb, pb, !turn, true);
            return new int[] {temp[0], temp[1]};
        }
        return turn ? new int[] {ans[0] + 1, ans[1]} : new int[] {ans[0], ans[1] + 1};
    }

    public void solve(Scanner sc, PrintWriter pw) {
        S = sc.nextInt();
        RA = sc.nextInt();
        PA = sc.nextInt();
        RB = sc.nextInt();
        PB = sc.nextInt();
        C = sc.nextInt();
        R = new int[C];
        P = new int[C];
        for (int i = 0; i < C; i++) {
            R[i] = sc.nextInt();
            P[i] = sc.nextInt();
        }
        // int res = solutionNaive();
        int res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        List<int[]> list = new ArrayList<int[]>();
        list.add(new int[] {1, 1});
        list.add(new int[] {2, 1});
        list.add(new int[] {2, 2});
        list.add(new int[] {2, 3});
        S = 2;
        while (true) {
            Collections.shuffle(list, rand);
            RA = list.get(0)[0];
            PA = list.get(0)[1];
            RB = list.get(1)[0];
            PB = list.get(1)[1];
            C = rand.nextInt(S * S - 2) + 1;
            R = new int[C];
            P = new int[C];
            for (int i = 0; i < C; i++) {
                R[i] = list.get(2 + i)[0];
                P[i] = list.get(2 + i)[1];
            }
            int res1 = solutionNaive();
            int res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n", res1, res2);
                System.out.printf("S: %d, RA: %d, PA: %d, RB: %d, PB: %d, C: %d\n", S, RA, PA, RB, PB, C);
                for (int i = 0; i < C; i++) {
                    System.out.println(R[i] + " " + P[i]);
                }
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