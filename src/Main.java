import java.util.Scanner;

// Main class of the Ride-Sharing System
public class Main {

    // CSV files
    private static final String RIDERS_FILE = "riders_100.csv";
    private static final String DRIVERS_FILE = "drivers_30.csv";
    private static final String RIDES_FILE = "rides_40.csv";

    private static final Scanner input = new Scanner(System.in);
    private static final IRideSharingSystem system = new RideSharingSystem();

    public static void main(String[] args) {

        // Load data from CSV files
        if (!system.loadRidersFromCSV(RIDERS_FILE)) {
            System.out.println("Warning: could not load " + RIDERS_FILE);
        }

        if (!system.loadDriversFromCSV(DRIVERS_FILE)) {
            System.out.println("Warning: could not load " + DRIVERS_FILE);
        }

        if (!system.loadRidesFromCSV(RIDES_FILE)) {
            System.out.println("Warning: could not load " + RIDES_FILE);
        }

        int choice;

        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    printList(system.getAllRiders(), "No riders found");
                    break;

                case 2:
                    String city = readText("Enter home city: ");
                    printList(system.searchRidersByHomeCity(city),"No riders found for this city");
                    break;

                case 3:
                    String riderName = readText("Enter full name: ");
                    printList(system.searchRidersByName(riderName),"No riders found with this name");
                    break;

                case 4:
                    addRider();
                    break;

                case 5:
                    printList(system.getAllDrivers(), "No drivers found");
                    break;

                case 6:
                    VehicleType vehicleType = readVehicleType();
                    printList(system.searchDriversByVehicleType(vehicleType),"No drivers found for this vehicle type");
                    break;

                case 7:
                    addDriver();
                    break;

                case 8:
                    printList(system.getAllRidesAlphabetically(),"No rides found");
                    break;

                case 9:
                    String pickupLocation =
                            readText("Enter pickup location: ");
                    printList(
                            system.searchRidesByPickupLocation(pickupLocation),"No rides found at this location");
                    break;

                case 10:
                    String searchName =
                            readText("Enter rider's full name: ");
                    printList(system.searchRidesByRiderName(searchName),"No rides found for this rider");
                    break;

                case 11:
                    String sharedPickup = readText(
                            "Enter pickup location of the shared ride: ");
                    printList(
                            system.getSharedRideParticipants(sharedPickup),"No participants found");
                    break;

                case 12:
                    addRide();
                    break;

                case 0:
                    System.out.println("Exiting the program");
                    break;

                default:
                    System.out.println(
                            "Invalid choice, Please enter a number from 0 to 12");
            }

            System.out.println();

        } while (choice != 0);
    }

    // Displays the menu
    private static void printMenu() {
        System.out.println("========== Ride-Sharing System ==========");
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city");
        System.out.println("3. Search for riders by name");
        System.out.println("4. Add a rider");
        System.out.println("5. List all drivers");
        System.out.println("6. Search for drivers by vehicle type");
        System.out.println("7. Add a driver");
        System.out.println(
                "8. List all rides alphabetically by pickup location");
        System.out.println("9. Search for rides by pickup location");
        System.out.println("10. Search for rides by rider name");
        System.out.println(
                "11. List participants of a shared ride by pickup location");
        System.out.println("12. Add a ride");
        System.out.println("0. Exit");
    }

    // Prints the list
    private static <T> void printList(
            LinkedList<T> list, String emptyMessage) {

        if (list == null || list.empty()) {
            System.out.println(emptyMessage);
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

    // Adds a rider
    private static void addRider() {
        int id = readInt("Enter rider ID: ");
        String name = readText("Enter full name: ");
        String email = readText("Enter email: ");
        String phone = readText("Enter phone number: ");
        String city = readText("Enter home city: ");

        try {
            Rider rider = new Rider(id, name, email, phone, city);

            if (system.addRider(rider)) {
                System.out.println("Rider added successfully");
            } else {
                System.out.println(
                        "Failed to add rider. Check the ID and email");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Invalid rider information: " + e.getMessage());
        }
    }

    // Adds a driver
    private static void addDriver() {
        int id = readInt("Enter driver ID: ");
        String name = readText("Enter full name: ");
        String phone = readText("Enter phone number: ");
        String plate = readText("Enter vehicle plate: ");
        VehicleType type = readVehicleType();

        try {
            Driver driver = new Driver(id, name, phone, plate, type);

            if (system.addDriver(driver)) {
                System.out.println("Driver added successfully");
            } else {
                System.out.println("Failed to add driver, Check the ID and plate");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Invalid driver information: " + e.getMessage());
        }
    }

    // Reads the vehicle type
    private static VehicleType readVehicleType() {
        while (true) {
            System.out.println(
                    "Vehicle types: SEDAN, LUXURY_SEDAN, SUV, VAN");

            String value = readText("Enter vehicle type: ").toUpperCase().replace(' ', '_');

            try {
                return VehicleType.valueOf(value);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type,Please try again");
            }
        }
    }

    // Adds a ride
    private static void addRide() {
        String type;

        while (true) {
            type = readText(
                    "Enter ride type (PRIVATE or SHARED): ").toUpperCase();

            if (type.equals("PRIVATE") || type.equals("SHARED")) {
                break;
            }

            System.out.println("Enter PRIVATE or SHARED");
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
                    pickup,
                    pickupTime,
                    dropoffTime,
                    dropoff,
                    riderId,
                    driverId);

        } else {
            int count = readInt(
                    "Enter number of riders (at least 2): ");

            while (count < 2) {
                System.out.println("A shared ride needs at least 2 riders");
                count = readInt("Enter number of riders: ");
            }

            int[] riderIds = new int[count];

            for (int i = 0; i < count; i++) {
                riderIds[i] = readInt(
                        "Enter rider ID " + (i + 1) + ": ");
            }

            success = system.scheduleSharedRide(
                    pickup,
                    pickupTime,
                    dropoffTime,
                    dropoff,
                    riderIds,
                    driverId);
        }

        if (success) {
            System.out.println("Ride added successfully");
        } else {
            System.out.println("Failed to add ride, Check the IDs and schedule conflicts");
        }
    }

    // Reads the date and time
    private static IDateTime readDateTime(String message) {
        while (true) {
            String value = readText(message);

            try {
                String[] dateTime = value.trim().split("\\s+");

                if (dateTime.length != 2) {
                    throw new IllegalArgumentException();
                }

                String[] date = dateTime[0].split("/");
                String[] time = dateTime[1].split(":");

                if (date.length != 3 || time.length != 2) {
                    throw new IllegalArgumentException();
                }

                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);

                return new DateTime(year, month, day, hour, minute);

            } catch (RuntimeException e) {
                System.out.println("Invalid date/time, Example: 10/30/2026 14:30");
            }
        }
    }

    // Reads an integer
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number");
            }
        }
    }

    // Reads text
    private static String readText(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }
}
