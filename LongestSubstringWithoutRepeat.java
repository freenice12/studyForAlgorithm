package jpa.example.demo.algo;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LongestSubstringWithoutRepeat {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s1 = "abcabcbb";
        String s2 = "bbbbb";
        String s3 = "pwwkew";
        String s4 = "aab";
        String s5 = "dvdf";
        System.out.println("sol: " + 3);
        System.out.println(solution.lengthOfLongestSubstring(s1));
        System.out.println("sol: " + 1);
        System.out.println(solution.lengthOfLongestSubstring(s2));
        System.out.println("sol: " + 3);
        System.out.println(solution.lengthOfLongestSubstring(s3));
        System.out.println("sol: " + 2);
        System.out.println(solution.lengthOfLongestSubstring(s4));
        System.out.println("sol: " + 3);
        System.out.println(solution.lengthOfLongestSubstring(s5));
    }
}
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int index = 0;
        String max = "";
        String preMax = "";
        while (index < s.length()) {
            for (int i = index; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!max.contains(String.valueOf(c))) {
                    max += c;
                    continue;
                }
                break;
            }
            if (max.length() > preMax.length()) {
                preMax = max;
            }
            max = "";
            index++;
        }

        return Math.max(max.length(), preMax.length());
    }
//    public int lengthOfLongestSubstring(String s) {
//        String preMax = "";
//        String max = "";
//
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (!max.contains(String.valueOf(c))) {
//                max += c;
//                continue;
//            }
//            if (preMax.length() < max.length()) {
//                preMax = max;
//            }
//            max = "" + c;
//        }
//        System.out.println(preMax + " / " + max);
//        return Math.max(max.length(), preMax.length());
//    }
}
