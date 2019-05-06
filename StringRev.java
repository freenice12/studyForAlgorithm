package jpa.example.demo.algo;

import java.util.Stack;
import java.util.stream.Stream;

public class StringRev {
    public static String reverse(String raw) {
        String result = "";
        for (int i = raw.length(); i > 0; i--) {
            result += raw.substring(i - 1, i);
        }
        return result;
    }

    public static String reverseII(String raw) {
        Stack<Character> stack = new Stack<>();
        for (byte b : raw.getBytes()) {
            stack.push((char)b);
        }
        String result = "";
        while (!stack.isEmpty()) result += stack.pop();
        return result;
    }
}
