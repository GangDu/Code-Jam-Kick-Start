import java.util.*;
import java.io.*;

public class Solution {
    private int N, A, B, C;

    private int[] solution() {
        int[] res = new int[N];
        if (N == 1) { 
            if (A != 1 || B != 1 || C != 1) {
                return null;
            } else {
                res[0] = 1;
                return res;
            }
        }
        if (A + B - C > N) {
            return null;
        }
        if (C > 1) {
            caseOne(res);
        }
        if (C == 1) {
            if (A + B - C == N) {
                caseOne(res);
            } else if (A > 1 || B > 1) {
                caseTwo(res);
            } else if (A == 1 && B == 1) {
                return null;
            }
        }
        return res;
    }

    private void caseOne(int[] arr) {
        Arrays.fill(arr, 1);
        for (int i = A - C; i < A - 1; i++) {
            arr[i] = N;
        }
        arr[N-(B-C)-1] = N;
    }

    private void caseTwo(int[] arr) {
        Arrays.fill(arr, 1);
        for (int i = 0; i < A - 1; i++) {
            arr[i] = (N + 1) / 2;
        }
        for (int i = N - 1; i > N - B; i--) {
            arr[i] = (N + 1) / 2;
        }
        if (A < B) {
            arr[A - 1] = N;
        } else {
            arr[N - B] = N;
        }
    }

    private boolean check(int[] arr) {
        Set<Integer> setA = new HashSet<Integer>();
        Set<Integer> setB = new HashSet<Integer>();        
        int maxA = 0, maxB = 0;
        for (int i = 0; i < arr.length; i++) {
            if (maxA <= arr[i]) {
                setA.add(i);
                maxA = arr[i];
            }
            int idx = arr.length - i - 1;
            if (maxB <= arr[idx]) {
                setB.add(idx);
                maxB = arr[idx];
            }
        }
        int c = 0;
        for (int i: setA) {
            if (setB.contains(i)) c++;
        }
        return setA.size() == A && setB.size() == B && c == C;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        C = sc.nextInt();
        int[] res = solution();
        if (res == null) {
            pw.print("IMPOSSIBLE");
        } else {
            for (int i: res) {
                pw.print(i + " ");
            }
        }
        pw.println();
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 20;
        while (true) {
            N = rand.nextInt(n) + 1;
            C = rand.nextInt(N) + 1;
            A = C + rand.nextInt(N - C + 1);
            B = C + rand.nextInt(N - C + 1);
            int[] res = solution();
            if (res == null) {
                System.out.println("IMPOSSIBLE");
                continue;
            }
            if (check(res)) {
                System.out.println("OK");
            } else {
                System.out.printf("N: %d, A: %d, B: %d, C: %d\n", N, A, B, C);
                System.out.println(Arrays.toString(res));
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