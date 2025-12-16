package com;

import java.util.Arrays;
import java.util.Comparator;

public class SortExample2 {

	public static void main(String[] args) {

        Person[] peoples = {
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
            };
        
        peoples = sort(peoples);
        System.out.println("Sorting elements according to order by name: " + Arrays.toString(peoples));

	}
	
	private static Person[] sort(Person[] persons) {
		
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.age, p2.age);
            }
        });
        
        	return persons;
	}
}

class Person {
    String name;
    int age;

      // Constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

      // Overriding toString Method for the Class
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
