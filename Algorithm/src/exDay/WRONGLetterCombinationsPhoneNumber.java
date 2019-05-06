package jpa.example.demo.algo;

import java.util.*;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * Note:
 * Although the above answer is in lexicographical order, your answer could be in any order you want.
 */
public class WRONGLetterCombinationsPhoneNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.letterCombinations("23"));
    }
    /*
    static class Solution {
        Map<String, String> phone = new HashMap<>() {{
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }};

        List<String> output = new ArrayList<>();

        public void backtrack(String combination, String nextDigits) {
            if (nextDigits.length() == 0) {
                output.add(combination);
            }
            else {
                String digit = nextDigits.substring(0, 1);
                String letters = phone.get(digit);
                for (int i = 0; i < letters.length(); i++) {
                    String letter = letters.substring(i, i + 1);
                    backtrack(combination + letter, nextDigits.substring(1));
                }
            }
        }

        public List<String> letterCombinations(String digits) {
            if (digits.length() != 0)
                backtrack("", digits);
            return output;
        }
    }
    */

    static class Solution {
        Map<String, String> nums = new HashMap<>();
        public void init() {
            nums.put("2", "abc");
            nums.put("3", "def");
            nums.put("4", "ghi");
            nums.put("5", "jkl");
            nums.put("6", "mno");
            nums.put("7", "pqrs");
            nums.put("8", "tuv");
            nums.put("9", "wxyz");
        }
        int size;
        public List<String> letterCombinations(String digits) {
            if (digits.isEmpty()) return new ArrayList<>();
            init();
            size = digits.length();
            List<String> result = new ArrayList<>();
            find(result, "", digits);
            return result;
        }

        public void find(List<String> result, String acc, String digits) {
            if (acc.length() == size) {
                result.add(acc);
                return;
            }
            if (digits.length() == 0) return;
            String curr = digits.substring(0, 1);
            String sub = nums.get(curr);
            for (int i = 0; i < sub.length(); i++) {
                acc += sub.substring(i, i+1);
                find(result, acc, digits.substring(1));
                acc = acc.substring(0, acc.length() - 1);
            }
        }
    }
}
