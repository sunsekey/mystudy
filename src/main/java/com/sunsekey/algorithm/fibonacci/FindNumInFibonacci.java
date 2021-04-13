package com.sunsekey.algorithm.fibonacci;
/**
 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55
 */
public class FindNumInFibonacci {

    private static int findRecursive(int n){
        if(n < 0){
            return -1;
        }
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        return findRecursive(n - 1) + findRecursive(n - 2);
    }

    private static int findRecursive2(int a, int b, int n){
        if(n == 3){
            return a + b;
        }
        return findRecursive2(b, a + b, n - 1);
    }

    public static int findIterative(int n){
        int result = 0;
        int preOne = 1;
        int preTwo = 0;
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        for(int i = 2; i <= n; i ++){
            result = preOne + preTwo;
            preTwo = preOne;
            preOne = result;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(findIterative(10));
    }
}
