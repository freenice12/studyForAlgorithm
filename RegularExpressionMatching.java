package jpa.example.demo.algo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 *
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like . or *.
 * Example 1:
 *
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 * Example 2:
 *
 * Input:
 * s = "aa"
 * p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 * Example 3:
 *
 * Input:
 * s = "ab"
 * p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 * Example 4:
 *
 * Input:
 * s = "aab"
 * p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
 * Example 5:
 *
 * Input:
 * s = "mississippi"
 * p = "mis*is*p*."
 * Output: false
 */
public class RegularExpressionMatching {
    public static void main(String[] args) {
        Map<String, String> problems = new LinkedHashMap<>();
//        problems.put("aa", "a");
//        problems.put("aa", "a*");
//        problems.put("ab", ".*");
//        problems.put("aab", "c*a*b");
//        problems.put("mississippi", "mis*is*p*."); // false
//        problems.put("mississippi", "mis*is*ip*."); // true
//        problems.put("aaba", "ab*a*c*a");
//        problems.put("aaa", "a*a");
//        problems.put("aaaa", "a*");
//        problems.put("ab", ".*c");
//        problems.put("", ".");
//        problems.put("aaaa", "aaaaa");
        problems.put("aaa", "ab*a*c*a");
        problems.forEach((s, p) -> System.out.println(new Solution().isMatch(s, p)));
    }

    static class Solution {
        public boolean isMatch(String s, String p) {
            int sr = s.length() - 1, pr = p.length() - 1;
            if (s.isEmpty() && !p.isEmpty()) return false;
            boolean isStar = false;

            while (sr >= 0 && pr >= 0) {
                if (p.charAt(pr) == "*".charAt(0)) {
                    isStar = true;
                    pr--;
                }
                if (p.charAt(pr) == ".".charAt(0)) {
                    pr--;
                    sr--;
                    if (isStar) {
                        sr = -1;
                    }
                    continue;
                }
                if (p.charAt(pr) == s.charAt(sr)) {
                    if (!isStar || sr == 0) {
                        pr--;
                    }
                    sr--;
                } else {
                    if (isStar) {
                        isStar = false;
                        pr--;
                        continue;
                    }
                    return false;
                }
            }

            while (pr > 0) {
                if (p.charAt(pr) == "*".charAt(0)) {
                    pr -= 2;
                } else {
                    if (sr == -1 && pr == 0 && s.charAt(sr + 1) == p.charAt(pr)) {
                        pr = -1;
                        break;
                    }
                    return false;
                }

            }

            return sr < 0 && pr < 0;
        }
//        public boolean isMatch(String s, String p) {
//            int minLength = getMinLenght(p);
//            if (s.length() < minLength) return false;
//            int sIndex = 0, pIndex = 0;
//            while (sIndex < s.length() && pIndex < p.length()) {
//                if (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == ".".charAt(0)) {
//                    sIndex++;
//                    pIndex++;
//                    if (pIndex < p.length() && p.charAt(pIndex) == "*".charAt(0)) pIndex--;
//                } else if (s.charAt(sIndex) != p.charAt(pIndex)) {
//                    if (pIndex + 1 >= p.length()) return false;
//                    if (p.charAt(pIndex + 1) == "*".charAt(0)) pIndex += 2;
//                }
//
//            }
//            if (pIndex + 1 < p.length() && (p.charAt(pIndex + 1) == ".".charAt(0) || p.charAt(pIndex + 1) == "*".charAt(0))) pIndex += 2;
////            return sIndex == s.length() && pIndex == p.length();
//            return sIndex == s.length() && (pIndex == p.length() || p.charAt(pIndex) == s.charAt(sIndex - 1));
//        }

        private int getMinLenght(String p) {
            int result = 0;
            for (int i = 0; i < p.length(); i++) {
                if (p.charAt(i) == ".".charAt(0)) result++;
                if (p.charAt(i) == "*".charAt(0)) result--;
            }
            return result;
        }
    }

}
