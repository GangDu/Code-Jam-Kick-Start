import java.util.*;
import java.io.*;

public class Solution {
    private int N, Q;
    private int[] D, S, K;
    private int[] interestingLeft, interestingRight, leftChild, rightChild, size;
    private int[][] memo;
    private int log;

    private int[] solutionNaive() {
        int[] res = new int[Q];
        for (int q = 0; q < Q; q++) {
            res[q] = traverse(S[q], K[q]);
        }
        return res;
    }

    private int traverse(int s, int k) {
        int curr = s;
        int l = curr - 1, r = curr;
        int order = 1;
        while (order != k) {
            if (D[l] < D[r]) {
                l--;
                curr = l + 1;
            } else {
                r++;
                curr = r;
            }
            order++;
        }
        return curr;
    }

    private int[] solutionFast() {
        int[] res = new int[Q];
        compute();
        // System.out.println(Arrays.toString(interestingLeft));
        // System.out.println(Arrays.toString(interestingRight));
        int root = buildTree();
        size = new int[N];
        log = (int) Math.ceil(Math.log(N - 1) / Math.log(2));
        memo = new int[N][log + 1];
        dfs(root, root);
        // System.out.println(Arrays.toString(size));
        // for (int i = 0; i < N-1; i++) {
        //     System.out.println(Arrays.toString(memo[i]));
        // }
        for (int q = 0; q < Q; q++) {
            int s = S[q], k = K[q] - 1;
            int x = findX(s);
            if (size[x] >= k) {
                if (x == s - 1) {
                    res[q] = s - k;
                } else {
                    res[q] = s + k;
                }
            } else {
                int y = findY(x, k);
                // System.out.printf("x: %d, y: %d, size x: %d, size y: %d, k: %d\n", x, y, size[x], size[y], k);
                if (x < y) {
                    res[q] = y + k - size[leftChild[y]];
                } else {
                    res[q] = y + 1 - (k - size[rightChild[y]]);
                }
            }
        }
        return res;
    }

    private void compute() {
        interestingLeft = new int[N];
        Stack<Integer> s = new Stack<Integer>();
        for (int i = 1; i < N; i++) {
            while (!s.empty() && D[s.peek()] <= D[i]) {
                s.pop();
            }
            if (s.empty()) {
                interestingLeft[i] = -1;
            } else {
                interestingLeft[i] = s.peek();
            }
            s.push(i);
        }
        interestingRight = new int[N];
        s.clear();
        for (int i = N - 1; i > 0; i--) {
            while (!s.empty() && D[s.peek()] <= D[i]) {
                s.pop();
            }
            if (s.empty()) {
                interestingRight[i] = -1;
            } else {
                interestingRight[i] = s.peek();
            }
            s.push(i);
        }
    }

    private int buildTree() {
        leftChild = new int[N];
        rightChild = new int[N];
        int root = 0;
        for (int i = 1; i < N; i++) {
            int left = interestingLeft[i];
            int right = interestingRight[i];
            int parent = 0;
            if (left != -1 && right != -1) {    
                if (D[left] < D[right]) {
                    parent = left;
                } else {
                    parent = right;
                }
            } else if (left == -1 && right == -1) {
                root = i;
            } else if (left != -1 && right == -1) {
                parent = left;
            } else {
                parent = right;
            }
            if (parent > i) {
                leftChild[parent] = i;
            } else {
                rightChild[parent] = i;
            }
        }
        return root;
    }

    private void dfs(int u, int p) {
        if (u == 0) return;
        size[u] = 1;
        memo[u][0] = p;
        for (int i = 1; i <= log; i++) {
            memo[u][i] = memo[memo[u][i-1]][i-1];
        }
        dfs(leftChild[u], u);
        dfs(rightChild[u], u);
        if (p != u) {
            size[p] += size[u];
        }
    }

    private int findY(int x, int k) {
        for (int i = log; i >= 0; i--) {
            if (size[memo[x][i]] < k) {
                x = memo[x][i];
            }
        }
        return memo[x][0];
    }

    private int findX(int s) {
        int l = s - 1, r = s;
        if (D[l] < D[r]) {
            return l;
        } else {
            return r;
        }
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        Q = sc.nextInt();
        D = new int[N + 1];
        S = new int[Q];
        K = new int[Q];
        D[0] = D[N] = Integer.MAX_VALUE;
        for (int i = 1; i < N; i++) {
            D[i] = sc.nextInt();
        }
        for (int i = 0; i < Q; i++) {
            S[i] = sc.nextInt();
            K[i] = sc.nextInt();
        }
        // int[] res = solutionNaive();
        int[] res = solutionFast();
        for (int i = 0; i < Q; i++) {
            pw.print(res[i] + " ");
        }
        pw.println();
    }

    private void stressTest() {
        Random rand = new Random(0);
        N = 100;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < N; i++) {
            list.add(i);
        }
        while (true) {
            Collections.shuffle(list, rand);
            Q = rand.nextInt(N) + 1;
            D = new int[N + 1];
            S = new int[Q];
            K = new int[Q];
            D[0] = D[N] = Integer.MAX_VALUE;
            for (int i = 1; i < N; i++) {
                D[i] = list.get(i - 1);
            }
            for (int i = 0; i < Q; i++) {
                S[i] = rand.nextInt(N) + 1;
                K[i] = rand.nextInt(N) + 1;
            }
            int[] res1 = solutionNaive();
            int[] res2 = solutionFast();
            if (Arrays.equals(res1, res2)) {
                // System.out.println("res1: " + Arrays.toString(res1));
                // System.out.println("res2: " + Arrays.toString(res2));
                System.out.println("OK");
            } else {
                System.out.println("res1: " + Arrays.toString(res1));
                System.out.println("res2: " + Arrays.toString(res2));
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