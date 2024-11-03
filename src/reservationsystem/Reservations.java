package reservationsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Reservations {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void reservationConfig() {
        int option;
        do {
            System.out.println("\n--- Reservation Management ---");
            System.out.println("1. Add Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Edit Reservation");
            System.out.println("4. Delete Reservation");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 
                
                switch (option) {
                    case 1:
                        addReservation();
                        break;
                    case 2:
                        viewReservations();
                        break;
                    case 3:
                        editReservation();
                        break;
                    case 4:
                        deleteReservation();
                        break;
                    case 5:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                option = -1;
            }
        } while (option != 5);
    }

    private void addReservation() {
        System.out.println("Enter Reservation Details:");

        int customerId;
        do {
            System.out.print("\nCustomer ID: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customers", customerId)) {
                System.out.println("Customer ID doesn't exist.");
            }
        } while (!conf.doesIDExist("customers", customerId));

        int roomId;
        do {
            System.out.print("Room ID: ");
            roomId = scan.nextInt();
            if (!conf.doesIDExist("rooms", roomId)) {
                System.out.println("Room ID doesn't exist.");
            }
        } while (!conf.doesIDExist("rooms", roomId));

        scan.nextLine(); 

        System.out.print("Check-in Date (YYYY-MM-DD): ");
        String checkIn = scan.nextLine();
        System.out.print("Check-out Date (YYYY-MM-DD): ");
        String checkOut = scan.nextLine();
        System.out.print("Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO reservations (customer_id, room_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, customerId, roomId, checkIn, checkOut, status);
    }

    public void viewReservations() {
        String query = "SELECT * FROM reservations";
        String[] headers = {"ID", "Customer ID", "Room ID", "Check-in Date", "Check-out Date", "Status"};
        String[] columns = {"id", "customer_id", "room_id", "check_in_date", "check_out_date", "status"};

        conf.viewRecords(query, headers, columns);
    }

    private void editReservation() {
        int id;
        do {
            System.out.print("Enter Reservation ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("reservations", id)) {
                System.out.println("Reservation ID doesn't exist.");
            }
        } while (!conf.doesIDExist("reservations", id));

        scan.nextLine(); 

        System.out.println("Enter New Reservation Details:");

        int customerId;
        do {
            System.out.print("\nNew Customer ID: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customer", customerId)) {
                System.out.println("Customer ID doesn't exist.");
            }
        } while (!conf.doesIDExist("customer", customerId));

        int roomId;
        do {
            System.out.print("New Room ID: ");
            roomId = scan.nextInt();
            if (!conf.doesIDExist("room", roomId)) {
                System.out.println("Room ID doesn't exist.");
            }
        } while (!conf.doesIDExist("room", roomId));

        scan.nextLine(); 

        System.out.print("New Check-in Date (YYYY-MM-DD): ");
        String checkIn = scan.nextLine();
        System.out.print("New Check-out Date (YYYY-MM-DD): ");
        String checkOut = scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE reservations SET customer_id = ?, room_id = ?, check_in_date = ?, check_out_date = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, customerId, roomId, checkIn, checkOut, status, id);
    }

    private void deleteReservation() {
        System.out.print("Enter Reservation ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM reservations WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
