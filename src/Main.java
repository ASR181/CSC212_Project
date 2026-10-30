import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final IRideSharingSystem system = new RideSharingSystem();

    public static void main(String[] args) {

        // Load the data
        system.loadRidersFromCSV("riders_100.csv");
        system.loadDriversFromCSV("drivers_30.csv");
        system.loadRidesFromCSV("rides_40.csv");

        int choice;

        // Show the menu
        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    printList(system.getAllRiders());
                    break;

                case 2:
                    String city = readText("Enter home city: ");
                    printList(system.searchRidersByHomeCity(city));
                    break;

                case 3:
                    String name = readText("Enter rider name: ");
                    printList(system.searchRidersByName(name));
                    break;

                case 4:
                    addRider();
                    break;

                case 5:
                    printList(system.getAllDrivers());
                    break;

                case 6:
                    VehicleType type = readVehicleType();
                    printList(system.searchDriversByVehicleType(type));
                    break;

                case 7:
                    addDriver();
                    break;

                case 8:
                    printList(system.getAllRidesAlphabetically());
                    break;

                case 9:
                    String location = readText("Enter pickup location: ");
                    printList(system.searchRidesByPickupLocation(location));
                    break;

                case 10:
                    String riderName = readText("Enter rider name: ");
                    printList(system.searchRidesByRiderName(riderName));
                    break;

                case 11:
                    String pickup = readText("Enter pickup location: ");
                    printList(system.getSharedRideParticipants(pickup));
                    break;

                case 12:
                    addRide();
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // Print the menu
    private static void printMenu() {
        System.out.println("\nRide-Sharing System");
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city");
        System.out.println("3. Search for riders by name");
        System.out.println("4. Add a rider");
        System.out.println("5. List all drivers");
        System.out.println("6. Search for drivers by vehicle type");
        System.out.println("7. Add a driver");
        System.out.println("8. List all rides alphabetically by pickup location");
        System.out.println("9. Search for rides by pickup location");
        System.out.println("10. Search for rides by rider name");
        System.out.println("11. List participants of a shared ride by pickup location");
        System.out.println("12. Add a ride");
        System.out.println("0. Exit");
    }

    // Print the list
    private static <T> void printList(LinkedList<T> list) {

        if (list == null || list.empty()) {
            System.out.println("No results found.");
            return;
        }

        list.findFirst();

        while (true) {
            System.out.println(list.retrieve());

            if (list.last()) {
                break;
            }

            list.findNext();
        }
    }

    // Add a rider
    private static void addRider() {

        int id = readInt("Enter rider ID: ");
        String name = readText("Enter name: ");
        String email = readText("Enter email: ");
        String phone = readText("Enter phone number: ");
        String city = readText("Enter home city: ");

        try {
            Rider rider = new Rider(id, name, email, phone, city);

            if (system.addRider(rider)) {
                System.out.println("Rider added successfully.");
            } else {
                System.out.println("Failed to add rider.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid rider information.");
        }
    }

    // Add a driver
    private static void addDriver() {

        int id = readInt("Enter driver ID: ");
        String name = readText("Enter name: ");
        String phone = readText("Enter phone number: ");
        String plate = readText("Enter vehicle plate: ");
        VehicleType type = readVehicleType();

        try {
            Driver driver = new Driver(id, name, phone, plate, type);

            if (system.addDriver(driver)) {
                System.out.println("Driver added successfully.");
            } else {
                System.out.println("Failed to add driver.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid driver information.");
        }
    }

    // Read vehicle type
    private static VehicleType readVehicleType() {

        while (true) {
            String type = readText(
                    "Enter vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ")
                    .toUpperCase()
                    .replace(' ', '_');

            try {
                return VehicleType.valueOf(type);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type. Try again.");
            }
        }
    }

    // Add a ride
    private static void addRide() {

        String type;

        while (true) {
            type = readText("Enter ride type (PRIVATE or SHARED): ")
                    .toUpperCase();

            if (type.equals("PRIVATE") || type.equals("SHARED")) {
                break;
            }

            System.out.println("Please enter PRIVATE or SHARED.");
        }

        String pickup = readText("Enter pickup location: ");

        IDateTime pickupTime = readDateTime(
                "Enter pickup date/time (MM/DD/YYYY HH:MM): ");

        IDateTime dropoffTime = readDateTime(
                "Enter drop-off date/time (MM/DD/YYYY HH:MM): ");

        String dropoff = readText("Enter drop-off location: ");
        int driverId = readInt("Enter driver ID: ");

        boolean success;

        if (type.equals("PRIVATE")) {

            int riderId = readInt("Enter rider ID: ");

            success = system.schedulePrivateRide(
                    pickup, pickupTime, dropoffTime,
                    dropoff, riderId, driverId);

        } else {

            int count = readInt("Enter number of riders (at least 2): ");

            while (count < 2) {
                System.out.println("A shared ride needs at least 2 riders.");
                count = readInt("Enter number of riders: ");
            }

            int[] riderIds = new int[count];

            // Read rider IDs
            for (int i = 0; i < count; i++) {
                riderIds[i] = readInt(
                        "Enter rider ID " + (i + 1) + ": ");
            }

            success = system.scheduleSharedRide(
                    pickup, pickupTime, dropoffTime,
                    dropoff, riderIds, driverId);
        }

        if (success) {
            System.out.println("Ride added successfully.");
        } else {
            System.out.println("Failed to add ride.");
        }
    }

    // Read date and time
    private static IDateTime readDateTime(String message) {

        while (true) {
            String value = readText(message);

            try {
                String[] parts = value.split("\\s+");
                String[] date = parts[0].split("/");
                String[] time = parts[1].split(":");

                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);

                return new DateTime(year, month, day, hour, minute);

            } catch (RuntimeException e) {
                System.out.println(
                        "Invalid date/time. Example: 10/30/2026 14:30");
            }
        }
    }

    // Read a number
    private static int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    // Read text
    private static String readText(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }
}
