package reservationsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReservationSystem {
    static Config conf = new Config();
    
    static Scanner scan = new Scanner(System.in);
    static Customers customers = new Customers();
    static Rooms rooms = new Rooms();
    static Reservations reservations = new Reservations();
    
    public static void main(String[] args) {
        int choice;

        do {
            try {
                System.out.println("\n   + Customer and Room Reservation System +\n");
                System.out.println("1. Manage Customers");
                System.out.println("2. Manage Rooms");
                System.out.println("3. Manage Reservations");
                System.out.println("4. Generate Reports");
                System.out.println("5. Exit");
                
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        customers.customerConfig();
                        break;
                    case 2:
                        rooms.roomConfig();
                        break;
                    case 3:
                        reservations.reservationConfig();
                        break;
                    case 4:
                        generateReports();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 5);
    }
    
    static void generateReports() {
        System.out.println("\n--- Reservation Report ---");
        customers.viewCustomers();
        int customerId;
        do {
            System.out.print("\nEnter Customer ID for report: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customers", customerId)) {
                System.out.println("Customer ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("customers", customerId));

        System.out.printf("Customer ID   : %d%n", customerId);
        System.out.printf("Customer Name : %s%n", conf.getDataFromID("customers", customerId, "name"));
        System.out.printf("Email         : %s%n", conf.getDataFromID("customers", customerId, "email"));
        System.out.println("-------------------------------------");

        if (conf.isTableEmpty("reservations WHERE customer_id = " + customerId)) {
            System.out.println("No reservation history available.");
        } else {
            System.out.println("Reservation History:");
            String sql = "SELECT reservations.id, rooms.room_number, reservations.check_in_date, reservations.check_out_date, reservations.status " +
                         "FROM reservations " +
                         "JOIN rooms ON reservations.room_id = rooms.id " +
                         "WHERE reservations.customer_id = " + customerId;

            String[] Headers = {"Reservation ID", "Room Number", "Check-In Date", "Check-Out Date", "Status"};
            String[] Columns = {"id", "room_number", "check_in_date", "check_out_date", "status"};

            conf.viewRecords(sql, Headers, Columns);
        }
    }
}

