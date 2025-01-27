package org.example;

/*
David Winter
Software Development 1
CEN-3024C
1/24/2025

The application is designed to manage a library system, allowing users to add, remove, and display patrons while keeping track of their overdue fines.

The LibraryManagementSystem class serves as the entry point for the application, providing user interactions through a menu using the CLI. It initializes the library, facilitates the loading of patron data from a file, and provides options for users to manually add patrons or manage existing ones.
*/

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryList library = new LibraryList();

        // purpose: set default look of file explorer to system os
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to set look and feel: " + e.getMessage());
        }

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Load Patrons from a File (using JFileChooser)");
            System.out.println("2. Load Patrons by Entering File Path");
            System.out.println("3. Manually Add Patrons to the Library");
            System.out.println("4. Remove Patron by ID");
            System.out.println("5. Display Patrons");
            System.out.println("6. Exit");

            int choice = -1;
            while (choice < 1 || choice > 6) {
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 1 || choice > 6) {
                        System.out.println("Error: Please enter a number between 1 and 6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number.");
                }
            }

            /*
             * purpose: This switch statement handles user interactions in the Library Management System.
             *          It processes the user's choice from the menu and executes the corresponding
             *          functionality for managing library patrons. The options include:
             *
             *          1. Load patrons from a file using a file chooser dialog.
             *          2. Load patrons by manually entering a file path.
             *          3. Manually add a new patron, including input validation for names and overdue fines.
             *          4. Remove a patron from the library by their ID.
             *          5. Display the list of current patrons in the library.
             *          6. Exit the application, ensuring a clean shutdown.
             *
             *          Each case includes input validation and appropriate error handling.
             */
            switch (choice) {
                case 1:
                    // purpose: load patrons from .txt file using JFileChooser
                    JFileChooser loadFileChooser = new JFileChooser();
                    int loadReturnValue = loadFileChooser.showOpenDialog(null);
                    if (loadReturnValue == JFileChooser.APPROVE_OPTION) {
                        String loadFilename = loadFileChooser.getSelectedFile().getAbsolutePath();
                        library.addPatronsFromFile(loadFilename);
                    } else {
                        System.out.println("File selection cancelled.");
                    }
                    break;

                case 2:
                    // purpose: load patrons by entering the file path
                    System.out.print("Enter the file path to load patrons: ");
                    String filePath = scanner.nextLine();
                    library.addPatronsFromFile(filePath);
                    break;

                case 3:
                    String firstName = "";
                    String lastName = "";
                    String address = "";
                    double overdueFine = -1;

                    while (true) {
                        System.out.print("Enter a Patron First Name: ");
                        firstName = scanner.nextLine();
                        if (firstName.matches("[a-zA-Z]+")) {
                            break;
                        } else {
                            System.out.println("Error: First name can only contain alphabetic characters.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter a Patron Last Name: ");
                        lastName = scanner.nextLine();
                        if (lastName.matches("[a-zA-Z]+")) {
                            break;
                        } else {
                            System.out.println("Error: Last name can only contain alphabetic characters.");
                        }
                    }

                    System.out.print("Enter a Patron Address: ");
                    address = scanner.nextLine();

                    while (true) {
                        System.out.print("Enter an Overdue Fine: ");
                        try {
                            overdueFine = Double.parseDouble(scanner.nextLine());
                            if (overdueFine < 0 || overdueFine > 250) {
                                System.out.println("Error: Overdue fine must be between $0 and $250.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Please enter a valid number.");
                        }
                    }

                    Patron newPatron = new Patron(library.generateUniqueId(), firstName, lastName, address, overdueFine);
                    library.addPatron(newPatron);
                    break;

                case 4:
                    System.out.print("Please enter a Patron ID to remove patron: ");
                    String removeId = scanner.nextLine();
                    library.removePatron(removeId);
                    break;

                case 5:
                    library.displayPatrons();
                    break;

                case 6:
                    System.out.println("Now exiting the Library Management System.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Error: Please try again.");
            }
        }
    }
}