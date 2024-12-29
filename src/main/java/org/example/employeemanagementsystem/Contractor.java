package org.example.employeemanagementsystem;

class Contractor extends Employee {
    double hourRate;
    double maxHours;

    public Contractor(String name, double hourRate, double maxHours) {
        super(name);
        this.hourRate = hourRate;
        this.maxHours = maxHours;
    }

    @Override
    double calculateSalary() {
        return hourRate * maxHours;
    }
}
