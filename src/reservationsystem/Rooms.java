package reservationsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Rooms {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void roomConfig() {
        int option;
        do {
            System.out.println("\n--- Room Management ---");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Edit Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1: addRoom(); break;
                    case 2: viewRooms(); break;
                    case 3: editRoom(); break;
                    case 4: deleteRoom(); break;
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

    private void addRoom() {
        System.out.print("Enter Room Number: ");
        String roomNumber = scan.nextLine();
        System.out.print("Enter Room Type: ");
        String type = scan.nextLine();
        System.out.print("Enter Price Per Night: ");
        double price = scan.nextDouble();
        scan.nextLine();
        System.out.print("Enter Room Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, roomNumber, type, price, status);
    }

    public void viewRooms() {
        String query = "SELECT * FROM rooms";
        String[] headers = {"ID", "Room Number", "Type", "Price Per Night", "Status"};
        String[] columns = {"id", "room_number", "type", "price_per_night", "status"};

        conf.viewRecords(query, headers, columns);
    }

    private void editRoom() {
        System.out.print("Enter Room ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();
        if (!conf.doesIDExist("rooms", id)) {
            System.out.println("Room ID doesn't exist.");
            return;
        }

        System.out.print("Enter New Room Number: ");
        String roomNumber = scan.nextLine();
        System.out.print("Enter New Type: ");
        String type = scan.nextLine();
        System.out.print("Enter New Price Per Night: ");
        double price = scan.nextDouble();
        scan.nextLine();
        System.out.print("Enter New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE rooms SET room_number = ?, type = ?, price_per_night = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, roomNumber, type, price, status, id);
    }

    private void deleteRoom() {
        System.out.print("Enter Room ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM rooms WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
