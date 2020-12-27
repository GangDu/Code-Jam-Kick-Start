import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] X, Y;

    private long solutionNaive() {
        long ans = 0;
        Arrays.sort(X);
        int start = X[0] - N + 1;
        int end = X[N - 1];
        long minHorizon = Long.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            long temp = 0;
            for (int j = 0; j < N; j++) {
                temp += Math.abs((long) X[j] - (i + j));
            }
            minHorizon = Math.min(minHorizon, temp);
        }
        long minVertical = Long.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            long line = Y[i];
            long temp = 0;
            for (int j = 0; j < N; j++) {
                temp += Math.abs(Y[j] - line);
            }
            minVertical = Math.min(minVertical, temp);
        }
        // System.out.printf("hor: %d, ver: %d\n", minHorizon, minVertical);
        return minHorizon + minVertical;
    }

    private long solutionFast() {
        long ans = 0;
        Arrays.sort(X);
        Arrays.sort(Y);
        int[] optX = new int[N];
        for (int i = 0; i < N; i++) {
            optX[i] = X[i] - i;
        }
        Arrays.sort(optX);
        long minHorizon = 0;
        for (int i = 0; i < N; i++) {
            minHorizon += Math.abs((long) X[i] - (optX[N / 2] + i));
        }
        long minVertical = 0;
        for (int i = 0; i < N; i++) {
            minVertical += Math.abs(Y[i] - Y[N / 2]);
        }
        // System.out.printf("hor: %d, ver: %d\n", minHorizon, minVertical);
        return minHorizon + minVertical;
    }

    public void solve(Scanner sc, PrintWriter pw) { 
        N = sc.nextInt();
        X = new int[N];
        Y = new int[N];
        for (int i = 0; i < N; i++) {
            X[i] = sc.nextInt();
            Y[i] = sc.nextInt();
        }
        // long res = solutionNaive();
        long res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        X = new int[N];
        Y = new int[N];
        int n = 3;
        while (true) {
            for (int i = 0; i < N; i++) {
                X[i] = rand.nextInt(n) - n / 2;
                Y[i] = rand.nextInt(n) - n / 2;
            }
            long res1 = solutionNaive();
            long res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.println(N);
                System.out.println(Arrays.toString(X));
                System.out.println(Arrays.toString(Y));
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