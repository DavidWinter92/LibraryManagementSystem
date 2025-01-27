package org.example;

/*
The LibraryList class manages the collection of patrons in the library system. It provides functionalities to add, remove, and display patrons, as well as load patron data from a file. The class ensures that the library maintains accurate records of patrons and their overdue fines.

This class also includes methods for generating unique IDs for new patrons and validating the overdue fines during the addition of patrons.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LibraryList {
    private ArrayList<Patron> patrons;
    private HashSet<String> usedIds;
    private Random random;

    public LibraryList() {
        patrons = new ArrayList<>();
        usedIds = new HashSet<>();
        random = new Random();
        loadPatronsFromFile("patrons.txt");
    }

    /*
     * method: generateUniqueId
     * parameters: none
     * return: String
     * purpose: generates a unique ID for a patron by creating a random integer
     *          between 0 and 9999 and ensuring that the ID has not been previously
     *          used. It continues to generate a new ID if the generated ID is
     *          already in the used IDs set or if it is less than or equal to 0.
     */
    String generateUniqueId() {
        String id;
        do {
            id = String.valueOf(random.nextInt(10000));
        } while (usedIds.contains(id) || Integer.parseInt(id) <= 0);
        usedIds.add(id);
        return id;
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
        System.out.println("Patron added to Library List: " + patron);
    }

    public boolean removePatron(String id) {
        for (Patron patron : patrons) {
            if (patron.getId().equals(id)) {
                patrons.remove(patron);
                System.out.println("Patron removed from Library List: " + patron);
                return true;
            }
        }
        System.out.println("Patron not found using ID: " + id);
        return false;
    }

    public void displayPatrons() {
        System.out.println("List of current Patrons:");
        for (Patron patron : patrons) {
            System.out.println(patron);
        }
    }

    /*
     * method: addPatronsFromFile
     * parameters: String filename
     * return: void
     * purpose: reads patron data from a specified text file, where each line contains
     *          patron information (first name, last name, address, and overdue fine).
     *          It validates the data, ensuring the overdue fine does not exceed $250.
     *          Valid patrons are added to the library, and a summary of the number
     *          of patrons loaded is printed. If any errors occur during file reading
     *          or data parsing, appropriate error messages are displayed.
     */
    public void addPatronsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    String firstName = fields[0].trim();
                    String lastName = fields[1].trim();
                    String address = fields[2].trim();
                    double overdueFine = 0.0;

                    try {
                        overdueFine = Double.parseDouble(fields[3].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid fine amount format for entry: " + line);
                        continue;
                    }

                    try {
                        validatePatron(firstName, lastName, address, overdueFine);

                        if (overdueFine < 0 || overdueFine > 250) {
                            System.out.println("Overdue fine for " + firstName + " " + lastName +
                                    " must be between $0 and $250. Patron not added.");
                            continue;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Skipping invalid patron entry: " + line + " - " + e.getMessage());
                        continue;
                    }

                    Patron patron = new Patron(generateUniqueId(), firstName, lastName, address, overdueFine);
                    addPatron(patron);
                    count++;
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
            System.out.println(count + " patrons loaded successfully."); // Summary message
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void validatePatron(String firstName, String lastName, String address, double overdueFine) {
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First and last names can only contain alphabetic characters.");
        }
        if (overdueFine < 0) {
            throw new IllegalArgumentException("Overdue fine must be a non-negative number.");
        }
    }

    private void loadPatronsFromFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            addPatronsFromFile(filename);
        }
    }
}
