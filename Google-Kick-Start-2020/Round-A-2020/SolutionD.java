import java.util.*;
import java.io.*;

public class Solution {
    private int N, K;
    private String[] strs;
    private TrieNode trie;

    private int solutionNaive() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s: strs) {
            for (int j = 1; j <= s.length(); j++) {
                String pre = s.substring(0, j);
                if (map.containsKey(pre)) {
                    map.put(pre, map.get(pre) + 1);
                } else {
                    map.put(pre, 1);
                }
            }
        }
        int res = 0;
        for (int count: map.values()) {
            res += count / K;
        }
        return res;
    }

    private int solutionFast() {
        trie = new TrieNode();
        for (String s: strs) {
            // System.out.println(s);
            insert(s);
        }
        int res = traverse();
        return res;
    }

    private void insert(String s) {
        TrieNode node = trie;
        for (char c: s.toCharArray()) {
            if (node.next.containsKey(c)) {
                node = node.next.get(c);
                node.count++;
            } else {
                TrieNode child = new TrieNode();
                child.count++;
                node.next.put(c, child);
                node = child;
            }
        }
    }

    private int traverse() {
        int res = 0;
        Queue<TrieNode> queue = new LinkedList<TrieNode>();
        queue.add(trie);
        while (queue.size() != 0) {
            TrieNode curr = queue.poll();
            res += curr.count / K;
            // System.out.print(curr.count + " ");
            for (char c: curr.next.keySet()) {
                // System.out.print(c + " ");
                TrieNode node = curr.next.get(c);
                queue.add(node);
            }
            // System.out.println();
        }
        return res;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        N = sc.nextInt();
        K = sc.nextInt();
        strs = new String[N];
        for (int i = 0; i < N; i++) {
            strs[i] = sc.next();
        }
        // int res = solutionNaive();
        int res = solutionFast();
        pw.println(res);
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 10;
        while (true) {
            K = rand.nextInt(n) + 2;
            N = K * (rand.nextInt(n) + 1);
            strs = new String[N];
            for (int i = 0; i < N; i++) {
                int len = rand.nextInt(n) + 2;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    char c = (char) (rand.nextInt(26) + 'A');
                    sb.append(c);
                }
                strs[i] = sb.toString();
            }
            int res1 = solutionNaive();
            int res2 = solutionFast();
            if (res1 == res2) {
                System.out.println("OK");
            } else {
                System.out.printf("res1: %d, res2: %d\n");
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

class TrieNode {
    Map<Character, TrieNode> next; 
    int count;

    public TrieNode() {
        next = new HashMap<Character, TrieNode>();
        count = 0;
    }
}