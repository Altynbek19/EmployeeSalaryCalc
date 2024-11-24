package org.example.employeecalculatesalaryapp;

public class Contractor extends Employee {
    private double hourlyRate;
    private int maxHours;
    private int hoursWorked;

    public Contractor(String name, double hourlyRate, int maxHours, int hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.maxHours = maxHours;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        // If hours worked exceeds max hours, use max hours
        int effectiveHours = Math.min(hoursWorked, maxHours);
        return hourlyRate * effectiveHours;
    }
}
