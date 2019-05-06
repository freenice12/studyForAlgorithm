package jpa.example.demo.algo;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 *
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * Example 2:
 *
 * Input: "cbbd"
 * Output: "bb"
 *
 * ???
 * "abcda" -> "a"
 */
public class LongestPalindromicSubstring {
    static class Solution {


        /**
         * brute-force
         */
        public String longestPalindrome(String s) {
            if (s.length() == 1) return s;
            String result = "";
            for (int i = 0; i < s.length(); i++) {
                for (int j = i; j <= s.length(); j++) {
                    if (i == j) continue;
                    String substring = s.substring(i, j);
                    if (isPalindromic(substring)) {
                        if (result.length() < substring.length()) {
                            result = substring;
                        }
                    }
                }
            }
            return result;
        }

        private boolean isPalindromic(String str) {
            int left = 0, right = str.length() - 1;
            while (left <= right) {
                if (str.charAt(left++) != str.charAt(right--)) return false;
            }
            return true;
        }
    }
    public static void main(String[] args) {
        String s1 = "babad";
        String s2 = "cbbd";
        String s3 = "a";
        String s4 = "";

        Arrays.asList(s1, s2, s3, s4)
                .forEach(s -> {
                    String result = new Solution().longestPalindrome(s);
                    System.out.println(result);
                });
    }
}
