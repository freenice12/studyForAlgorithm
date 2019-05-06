package jpa.example.demo.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: true
 * Example 2:
 *
 * Input: "()[]{}"
 * Output: true
 * Example 3:
 *
 * Input: "(]"
 * Output: false
 * Example 4:
 *
 * Input: "([)]"
 * Output: false
 * Example 5:
 *
 * Input: "{[]}"
 * Output: true
 */
public class ValidParentheses {
    static class Parenth {
        private static Map<String, String> map = new HashMap<>();
        static {
            map.put("(", ")");
            map.put("[", "]");
            map.put("{", "}");
        }
        public boolean isValid(String s) {
            if (s.isEmpty()) return true;
            if (s.length() < 2) return false;
            Stack<String> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                String o = s.charAt(i) + "";
                if (map.keySet().contains(o)) {
                    stack.push(o);
                } else {
                    if (stack.size() == 0) return false;
                    String pop = stack.pop();
                    String closed = map.get(pop);
                    if (!closed.equals(o)) return false;
                }
            }
            return stack.isEmpty();
        }
    }

    public static void main(String[] args) {
        Parenth parenth = new Parenth();
        System.out.println(parenth.isValid("()") + " / true");
        System.out.println(parenth.isValid("()[]{}") + " / true");
        System.out.println(parenth.isValid("(]") + " / false");
        System.out.println(parenth.isValid("([)]") + " / false");
        System.out.println(parenth.isValid("{[]}") + " / true");
        System.out.println(parenth.isValid("]") + " / false");
        System.out.println(parenth.isValid("(") + " / false");
        System.out.println(parenth.isValid("}(") + " / false");
        System.out.println(parenth.isValid("") + " / true");
        System.out.println(parenth.isValid("((") + " / false");
    }
}
