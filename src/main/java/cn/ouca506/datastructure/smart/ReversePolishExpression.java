package cn.ouca506.datastructure.smart;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 计算式的运算
 * 中缀表达式、后缀表达式、前缀表达式
 * @author windmill666
 * @date 2020/4/6 15:56
 */

public class ReversePolishExpression {

    public static String middleConvertSuffix(String middleExp) {
        Stack<String> symbolStack = new Stack<>();
        Stack<String> numStack = new Stack<>();
        List<String> list = expAnalyse(middleExp);
        // 扫描中缀表达式
        for (String me : list) {
            // 如果扫描到操作数
            // 直接压入数栈
            if (me.matches("\\d+")) {
                numStack.push(me);
            }
            // 如果扫描到操作符
            if (me.matches("[+\\-*/()]")) {
                // 如果扫描到(
                // 直接压入符号栈
                if (me.equals("(")) {
                    symbolStack.push(me);
                }
                // 如果扫描到)，此时符号栈不可能为空
                // )不进栈，而是弹出符号栈的元素进数栈，直到遇到(
                if (me.equals(")")) {
                    while (!symbolStack.peek().equals("(")) {
                        numStack.push(symbolStack.pop());
                    }
                    symbolStack.pop();// 弹出(
                }
                // 如果扫描到+-*/
                // 先判断符号栈是否为空
                if (me.matches("[+\\-*/]")) {
                    // 空则直接压入符号栈
                    // 非空则进行优先级比较
                    // 如果比符号栈的栈顶元素优先级大，直接压入符号栈
                    // 如果比符号栈的栈顶元素优先级低或等于，则弹出符号栈栈顶压入数栈
                    // 并重新与符号栈的栈顶元素进行比较
                    if (!symbolStack.empty()) {
                        while (!symbolStack.empty() && calPriority(me) <= calPriority(symbolStack.peek())) {
                            numStack.push(symbolStack.pop());
                        }
                    }
                    symbolStack.push(me);
                }
            }
        }
        // 将符号栈中剩余元素依次压入数栈中
        while (!symbolStack.empty()) {
            numStack.push(symbolStack.pop());
        }
        // 数栈元素依次出栈后反转就是后缀表达式
        StringBuilder res = new StringBuilder();
        while (!numStack.empty()) {
            res.insert(0, numStack.pop() + " ");
        }
        return res.toString();
    }

    public static String middleConvertPrefix(String middleExp) {
        Stack<String> symbolStack = new Stack<>();
        Stack<String> numStack = new Stack<>();
        List<String> list = expAnalyse(middleExp);
        // 扫描中缀表达式
        for (int i = list.size() - 1; i >= 0; i--) {
            String me = list.get(i);
            // 如果扫描到操作数
            // 直接压入数栈
            if (me.matches("\\d+")) {
                numStack.push(me);
            }
            // 如果扫描到操作符
            if (me.matches("[+\\-*/()]")) {
                // 如果扫描到)
                // 直接压入符号栈
                if (me.equals(")")) {
                    symbolStack.push(me);
                }
                // 如果扫描到(，此时符号栈不可能为空
                // (不进栈，而是弹出符号栈的元素进数栈，直到遇到)
                if (me.equals("(")) {
                    while (!symbolStack.peek().equals(")")) {
                        numStack.push(symbolStack.pop());
                    }
                    symbolStack.pop();// 弹出)
                }
                // 如果扫描到+-*/
                // 先判断符号栈是否为空
                if (me.matches("[+\\-*/]")) {
                    // 空则直接压入符号栈
                    // 非空则进行优先级比较
                    // 如果比符号栈的栈顶元素优先级大或等于，直接压入符号栈
                    // 如果比符号栈的栈顶元素优先级低，则弹出符号栈栈顶压入数栈
                    // 并重新与符号栈的栈顶元素进行比较
                    if (!symbolStack.empty()) {
                        while (!symbolStack.empty() && calPriority(me) < calPriority(symbolStack.peek())) {
                            numStack.push(symbolStack.pop());
                        }
                    }
                    symbolStack.push(me);
                }
            }
        }
        // 将符号栈中剩余元素依次压入数栈中
        while (!symbolStack.empty()) {
            numStack.push(symbolStack.pop());
        }
        // 数栈元素依次出栈就是前缀表达式
        StringBuilder res = new StringBuilder();
        while (!numStack.empty()) {
            res.append(numStack.pop()).append(" ");
        }
        return res.toString();
    }

    public static int calculateRPE(String RPE) {
        String[] split = RPE.split(" ");
        Stack<String> numStack = new Stack<>();
        // 扫描逆波兰表达式
        for (String s : split) {
            if (s.matches("\\d+")) {
                numStack.push(s);
            } else {
                String a = numStack.pop();
                String b = numStack.pop();
                calAndPush(numStack, b, a, s);
            }
        }
        return Integer.parseInt(numStack.peek());
    }

    public static int calculatePE(String PE) {
        String[] split = PE.split(" ");
        Stack<String> numStack = new Stack<>();
        // 扫描波兰表达式
        for (int i = split.length - 1; i >= 0; i--) {
            String s = split[i];
            if (s.matches("\\d+")) {
                numStack.push(s);
            } else {
                String a = numStack.pop();
                String b = numStack.pop();
                calAndPush(numStack, a, b, s);
            }
        }
        return Integer.parseInt(numStack.peek());
    }

    public static int calculate(String middleExp) {
        Stack<String> symbolStack = new Stack<>();
        Stack<String> numStack = new Stack<>();
        List<String> list = expAnalyse(middleExp);
        // 扫描中缀表达式
        for (String me : list) {
            // 如果扫描到操作数
            // 直接压入数栈
            if (me.matches("\\d+")) {
                numStack.push(me);
            }
            // 如果是符号
            if (me.matches("[+\\-*/()]")) {
                // 如果是+-*/
                if (me.matches("[+\\-*/]")) {
                    // 空栈直接入栈
                    // 如果优先级大于符号栈栈顶元素，直接入符号栈
                    // 如果优先级小于或等于符号栈栈顶元素，先pop运算再入符号栈
                    while (!symbolStack.empty() && calPriority(me) <= calPriority(symbolStack.peek())) {
                        String a = numStack.pop();
                        String b = numStack.pop();
                        String s = symbolStack.pop();
                        calAndPush(numStack, b, a, s);
                    }
                    symbolStack.push(me);
                }
                // 如果是(直接入栈
                if (me.equals("(")) {
                    symbolStack.push(me);
                }
                // 如果是)需要计算
                if (me.equals(")")) {
                    while (!symbolStack.peek().equals("(")) {
                        String a = numStack.pop();
                        String b = numStack.pop();
                        String s = symbolStack.pop();
                        calAndPush(numStack, b, a, s);
                    }
                    symbolStack.pop();// 弹出(
                }
            }
        }
        while (!symbolStack.empty()) {
            String a = numStack.pop();
            String b = numStack.pop();
            String s = symbolStack.pop();
            calAndPush(numStack, b, a, s);
        }
        return Integer.parseInt(numStack.peek());
    }

    private static List<String> expAnalyse(String middleExp) {
        List<String> res = new ArrayList<>();
        // 使用正则表达式解析中缀表达式(\d+)|[+\-*()]
        // Java里的(\\d+)|[+\\-*/()]
        Pattern pattern = Pattern.compile("(\\d+)|[+\\-*/()]");
        Matcher matcher = pattern.matcher(middleExp);
        while (matcher.find()) {
            res.add(matcher.group());
        }
        return res;
    }

    private static int calPriority(String symbol) {
        switch (symbol) {
            case "(":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return -1;
    }

    private static void calAndPush(Stack<String> numStack, String a, String b, String s) {
        switch (s) {
            case "+": {
                numStack.push(String.valueOf(Integer.parseInt(a) + Integer.parseInt(b)));
                break;
            }
            case "-": {
                numStack.push(String.valueOf(Integer.parseInt(a) - Integer.parseInt(b)));
                break;
            }
            case "*": {
                numStack.push(String.valueOf(Integer.parseInt(a) * Integer.parseInt(b)));
                break;
            }
            case "/": {
                numStack.push(String.valueOf(Integer.parseInt(a) / Integer.parseInt(b)));
                break;
            }
        }
    }
}
