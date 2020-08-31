import java.util.*;
import java.io.*;

public class Solution {
    private int N;
    private int[] E, R;

    private String solutionNaive() {
        int y1 = N, y2 = N;
        long z = 0;
        boolean[] removed = new boolean[N];
        boolean flag = false;
        for (int i = 0; i < Math.pow(2, N) - 1; i++) {
            int count = 0;
            for (int j = 0; j < N; j++) {
                removed[j] = (i & (1 << j)) != 0;
                if (removed[j]) count++;
            }
            String t = check(removed);
            if (t.equals("INDEFINITELY")) {
                y1 = Math.min(y1, count);
                flag = true;
            } else {
                long temp = Long.valueOf(t);
                if (temp > z) {
                    z = temp;
                    y2 = count;
                } else if (temp == z) {
                    y2 = Math.min(y2, count);
                }
            }
        }
        return flag ? y1 + " INDEFINITELY": y2 + " " + z;
    }

    private String check(boolean[] removed) {
        long sumE = 0;
        for (int i = 0; i < N; i++) {
            if (removed[i]) continue;
            sumE += E[i];
        }
        long[] forget = new long[N];
        boolean flag = true;
        for (int i = 0; i < N; i++) {
            if (removed[i]) continue;
            forget[i] = R[i] - (sumE - E[i]);
            if (forget[i] > 0) flag = false;
        }
        if (flag) return "INDEFINITELY";
        long res = sumE;
        for (int i = 0; i < N; i++) {
            if (removed[i]) continue;
            if (forget[i] > 0) break;
            res += E[i];
        }
        return "" + res;
    }

    private String solutionFast() {
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += E[i];
        }
        long curTime = sum, maxTime = sum;
        int count = 0, remCount = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((x, y) -> Integer.compare(E[y]+R[y], E[x]+R[x]));
        for (int i = 0; i < N; i++) {
            curTime += E[i];
            pq.add(i);
            int j = pq.peek();
            while (E[j] + R[j] > sum) {
                pq.poll();
                count++;
                curTime -= 2 * E[j];
                sum -= E[j];
                if (pq.isEmpty()) break;
                j = pq.peek();
            }
            if (curTime > maxTime) {
                maxTime = curTime;
                remCount = count;
            }
        }
        return pq.isEmpty() ? remCount + " " + maxTime : count + " INDEFINITELY" ;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        E = new int[N];
        R = new int[N];
        for (int i = 0; i < N; i++) {
            E[i] = sc.nextInt();
            R[i] = sc.nextInt();
        }
        // String res = solutionNaive();
        String res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        while (true) {
            N = rand.nextInt(10) + 1;
            E = new int[N];
            R = new int[N];
            for (int i = 0; i < N; i++) {
                E[i] = rand.nextInt(1000) + 1;
                R[i] = rand.nextInt(1000) + 1;
            }
            String res1 = solutionNaive();
            String res2 = solutionFast();
            if (res1.equals(res2)) {
                System.out.println("OK");
            } else {
                System.out.println(res1 + ", " + res2);
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