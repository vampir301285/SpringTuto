package com.minhduc.java8.lamda;

/**
 * 
 * @author Minh Duc Ngo
 *
 */
public class Java8LamdaTester {

    /**
     * 
     * @author Minh Duc Ngo this can be functional interface
     */
    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String msg);
    }

    private int operate(int a, int b, MathOperation myOperation) {
        return myOperation.operation(a, b);
    }

    public static void main(String[] args) {
        // Lambda without declaration
        MathOperation addition = (a, b) -> a + b;

        // Lambda with declaration
        MathOperation subtraction = (int a, int b) -> a - b;

        // with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;
        Java8LamdaTester tester = new Java8LamdaTester();
        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // without parenthesis
        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        // with parenthesis
        GreetingService greetService2 = (message) -> System.out.println("Hello " + message);
        greetService1.sayMessage("Minh Duc");
        greetService2.sayMessage("Quynh Anh");
    }
}
