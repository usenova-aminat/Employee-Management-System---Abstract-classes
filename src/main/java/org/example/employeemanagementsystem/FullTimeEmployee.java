package org.example.employeemanagementsystem;

class FullTimeEmployee extends Employee{
    double annualSalary;

    public FullTimeEmployee(String name, double annualSalary) {
        super(name);
        this.annualSalary = annualSalary;
    }
    @Override
    double calculateSalary() {
        return annualSalary;
    }
}

