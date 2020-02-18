package com.minhduc.java8.defaultmethod;

public class Java8Tester {

    public static void main(String args[]) {
        Vehicle vehicle = new Car();
        vehicle.print();
    }
}

/**
 * 
 * @author Minh Duc Ngo
 *
 */
interface Vehicle {
    
    /**
     * default method in interface
     */
    default void print() {
        System.out.println("I am a vehicle!");
    }

    /**
     * An interface can also have static helper methods from Java 8 onwards.
     */
    static void blowHorn() {
        System.out.println("Blowing horn!!!");
    }
}

interface FourWheeler {
    default void print() {
        System.out.println("I am a four wheeler!");
    }
}

class Car implements Vehicle, FourWheeler {

    /**
     * overrides the default implementation.
     */
    public void print() {
        Vehicle.super.print();
        FourWheeler.super.print();
        Vehicle.blowHorn();
        System.out.println("I am a car!");
    }
}
