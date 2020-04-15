package com.sunsekey.practise.io;

public class CalculatorForDemo {

    public static Integer calculate(String expression) {
        char opr = expression.charAt(1);
        int v1 = Integer.parseInt(expression.substring(0, 1));
        int v2 = Integer.parseInt(expression.substring(2));
        switch (opr) {
            case '+':
                return v1 + v2;
            case '-':
                return v1 - v2;
            case '*':
                return v1 * v2;
            case '/':
                return v1 / v2;
            default:
                return 0;
        }

    }
}
