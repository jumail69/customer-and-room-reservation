package reservationsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customers {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void customerConfig() {
        int option;
        do {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Edit Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");
            
            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1: addCustomer(); break;
                    case 2: viewCustomers(); break;
                    case 3: editCustomer(); break;
                    case 4: deleteCustomer(); break;
                    case 5: System.out.println("Returning to main menu."); break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void addCustomer() {
        System.out.print("Customer Name: ");
        String name = scan.nextLine();
        System.out.print("Email: ");
        String email = scan.nextLine();
        System.out.print("Contact Info: ");
        String contact = scan.nextLine();
        
        String sql = "INSERT INTO customers (name, email, contact_info) VALUES (?, ?, ?)";
        conf.addRecord(sql, name, email, contact);
    }

    public void viewCustomers() {
        String query = "SELECT * FROM customers";
        String[] headers = {"ID", "Name", "Email", "Contact Info"};
        String[] columns = {"id", "name", "email", "contact_info"};
        
        conf.viewRecords(query, headers, columns);
    }

    private void editCustomer() {
        System.out.print("Enter Customer ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        if (!conf.doesIDExist("customers", id)) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = scan.nextLine();
        System.out.print("New Email: ");
        String email = scan.nextLine();
        System.out.print("New Contact Info: ");
        String contact = scan.nextLine();
        
        String sql = "UPDATE customers SET name = ?, email = ?, contact_info = ? WHERE id = ?";
        conf.updateRecord(sql, name, email, contact, id);
    }

    private void deleteCustomer() {
        System.out.print("Enter Customer ID to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM customers WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}

