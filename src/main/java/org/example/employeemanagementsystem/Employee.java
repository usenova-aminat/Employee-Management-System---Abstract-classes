package org.example.employeemanagementsystem;

abstract class Employee {
    String name;
    public Employee(String name) {
        this.name = name;
    }

    abstract double calculateSalary();
}
