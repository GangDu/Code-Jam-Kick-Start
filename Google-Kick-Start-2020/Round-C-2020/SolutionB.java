import java.util.*;
import java.io.*;

public class Solution {
    private int R, C;
    private char[][] wall;
    private Map<Character, Set<Character>> adj;

    private String solutionNaive() {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map.put(wall[i][j], 0);
            }
        }
        boolean flag = traverse(1, map);
        StringBuilder ans = new StringBuilder();
        map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .forEachOrdered(x -> ans.append(x.getKey()));
        if (flag) {
            return ans.toString();
        } else {
            return "-1";
        }
    }

    private boolean traverse(int step, Map<Character, Integer> map) {
        if (step > map.size()) {
            return check(map);
        }
        for (char c: map.keySet()) {
            if (map.get(c) != 0) continue;
            map.put(c, step);
            boolean flag = traverse(step + 1, map);
            if (flag) return true;
            map.put(c, 0);
        }
        return false;
    }

    private boolean check(Map<Character, Integer> map) {
        for (int i = 0; i < R - 1; i++) {
            for (int j = 0; j < C; j++) {
                if (map.get(wall[i][j]) < map.get(wall[i+1][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private String solutionFast() {
        adj = new HashMap<Character, Set<Character>>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (!adj.containsKey(wall[i][j])) {
                    adj.put(wall[i][j], new HashSet<Character>());
                }
                if (i == R-1 || wall[i][j] == wall[i+1][j]) continue;
                if (adj.containsKey(wall[i+1][j])) {
                    Set<Character> set = adj.get(wall[i+1][j]);
                    set.add(wall[i][j]);
                } else {
                    Set<Character> set = new HashSet<Character>();
                    set.add(wall[i][j]);
                    adj.put(wall[i+1][j], set);
                }
            }
        }
        LinkedList<Character> list = new LinkedList<Character>();
        boolean flag = dfs(list);
        StringBuilder ans = new StringBuilder();
        list.stream().forEachOrdered(x -> ans.append(x));
        if (flag) {
            return ans.toString();
        } else {
            return "-1";
        }
    }

    private boolean dfs(LinkedList<Character> list) {
        Set<Character> visited = new HashSet<Character>();
        for (char u: adj.keySet()) {
            if (list.contains(u)) continue;
            boolean flag = visit(u, visited, list);
            if (!flag) return false;
        }
        return true;
    }

    private boolean visit(char u, Set<Character> visited, LinkedList<Character> list) {
        if (visited.contains(u)) return false;
        if (list.contains(u)) return true;
        visited.add(u);
        for (char v: adj.get(u)) {
            if (!visit(v, visited, list)) {
                return false;
            }
        }
        visited.remove(u);
        list.addFirst(u);
        return true;
    }

    public void solve(Scanner sc, PrintWriter pw) {
        R = sc.nextInt();
        C = sc.nextInt();
        wall = new char[R][C];
        for (int i = 0; i < R; i++) {
            String s = sc.next();
            for (int j = 0; j < C; j++) {
                wall[i][j] = s.charAt(j);
            }
        }
        // String res = solutionNaive();
        String res = solutionFast();
        pw.println(res);
    }

    private Map<Character, Integer> convertString(String res) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < res.length(); i++) {
            map.put(res.charAt(i), i);
        }
        return map;
    }

    private void stressTest() {
        Random rand = new Random(0);
        int n = 5;
        while (true) {
            R = rand.nextInt(5) + 1;
            C = rand.nextInt(5) + 1;
            wall = new char[R][C];
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    wall[i][j] = (char) ((int) 'A' + rand.nextInt(n));
                }
            }
            String res1 = solutionNaive();
            String res2 = solutionFast();
            if (res1.equals(res2) || 
                (res1.length() == res2.length() && check(convertString(res1)) && check(convertString(res2)))) {
                System.out.println("OK");
            } else {
                System.out.println("Wrong Answer");
                System.out.printf("res1: %s, res2: %s\n", res1, res2);
                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        System.out.print(wall[i][j] + " ");
                    }
                    System.out.println();
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