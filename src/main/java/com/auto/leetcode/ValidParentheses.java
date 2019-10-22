package com.auto.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-10-04
 * @since JDK 1.8
 */
public class ValidParentheses {

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '{':
                    stack.push(ch);
                    continue;
                case '[':
                    stack.push(ch);
                    continue;
                case '(':
                    stack.push(ch);
                    continue;
                case '}':
                    if (stack.isEmpty() || '{' != stack.pop()) {
                        return false;
                    }
                    continue;
                case ']':
                    if (stack.isEmpty() || '[' != stack.pop()) {
                        return false;
                    }
                    continue;
                case ')':
                    if (stack.isEmpty() || '(' != stack.pop()) {
                        return false;
                    }
                    continue;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("{"));
    }
}
    
    