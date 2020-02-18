package com.minhduc.java8.methodreference;

import java.util.ArrayList;
import java.util.List;

/**
 * ethod references help to point to methods by their names. A method reference is described using "::" symbol. A method
 * reference can be used to point the following types of methods −
 * 
 * Static methods;
 * 
 * Instance methods;
 * 
 * Constructors using new operator (TreeSet::new)
 * 
 * @author UE1PHOT
 *
 */
public class Java8MethodReferenceTester {
    public static void main(String args[]) {
        List<String> names = new ArrayList<>();  
        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");   
        names.forEach(System.out::println); // static method
        
        List<Student> students = new ArrayList<>();
        students.add(new Student("Minh"));
        students.add(new Student("QA"));
        students.forEach(Student::sayHello); // instance method
     }
}

class Student {
    private String name;
    
    public Student(String name) {
        this.name = name;
    }
    
    public void sayHello() {
        System.out.printf("Hello %s\n", name);
    }
}
