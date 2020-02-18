package com.minhduc.java8.functionalinterface;

public class MyFunctionalInterfaceTester {

    public static void main(String[] args) {
        MyOperationInterface addition = (a , b) -> a+b;
        MyOperationInterface complexOperation = (a , b) -> a*2+b;
        
        int x = 10;
        int y = 15;
        
        System.out.println(addition.calculate(x, y));
        System.out.println(complexOperation.calculate(x, y));
    }
}
