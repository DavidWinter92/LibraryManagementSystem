package org.example;

/*
The Patron class represents a library patron, storing their personal information and managing their overdue fines. Each patron has a unique ID, first name, last name, address, and an overdue fine amount.

This class includes methods to get and set patron details, allowing the library system to manage patron data.
 */

public class Patron {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private double overdueFine;

    public Patron(String id, String firstName, String lastName, String address, double overdueFine) {
        if (!isValidName(firstName) || !isValidName(lastName)) {
            throw new IllegalArgumentException("First name and last name must be alphabetic characters only.");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.overdueFine = overdueFine;
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public double getOverdueFine() {
        return overdueFine;
    }

    @Override
    public String toString() {
        return "Patron ID: " + id + ", Name: " + firstName + " " + lastName + ", Address: " + address + ", Overdue Fine: $" + overdueFine;
    }
}