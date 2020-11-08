import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[][] C;

    private long solutionNaive() {
        long ans = 0;
        int i = 0, j = 0;
        for (j = 0; j < N; j++) {
            i = 0;
            int newJ = j;
            long temp = C[i][j];
            while (i + 1 < N && newJ + 1 < N) {
                i++;
                newJ++;
                temp += C[i][newJ];
            }
            // System.out.printf("i: %d, j: %d, temp: %d\n", i, j, temp);
            ans = Math.max(ans, temp);
        }
        for (i = 0; i < N; i++) {
            j = 0;
            int newI = i;
            long temp = C[i][j];
            while (newI + 1 < N && j + 1 < N) {
                newI++;
                j++;
                temp += C[newI][j];
            }
            // System.out.println(temp);
            ans = Math.max(ans, temp);
            
        }
        return ans;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        N = sc.nextInt();
        C = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                C[i][j] = sc.nextInt();
            }
        }
        long res = solutionNaive();
        // long res = solutionFast();
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