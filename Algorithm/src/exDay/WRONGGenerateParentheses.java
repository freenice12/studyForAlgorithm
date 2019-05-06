package jpa.example.demo.algo;

import java.util.ArrayList;
import java.util.List;

public class WRONGGenerateParentheses {

    static class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            backtract(result, "", 0, 0, n);
            return result;
        }

        private void backtract(List<String> result, String s, int open, int close, int max) {
            if (s.length() == max * 2) {
                result.add(s);
                return;
            }

            if (open < max) backtract(result, s + "(", open + 1, close, max);
            if (close < open) backtract(result, s + ")", open, close + 1, max);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.generateParenthesis(3));
        System.out.println(List.of("((()))",
                "(()())",
                "(())()",
                "()(())",
                "()()()"));

    }
}
