package org.example.employeemanagementsystem;

class PartTimeEmployee extends Employee {
    double hourlyRate;
    double hoursWorked;
    public PartTimeEmployee(String name, double hourlyRate, double hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

