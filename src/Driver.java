// Represents a driver
public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

    // Creates a driver
    public Driver(int id, String name, String phoneNumber,
                  String vehiclePlate, VehicleType vehicleType) {

        super(id, name, phoneNumber);
        this.setVehiclePlate(vehiclePlate);
        this.setVehicleType(vehicleType);
    }

    // Returns the vehicle plate
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    // Sets the vehicle plate
    public void setVehiclePlate(String vehiclePlate) {

        if (vehiclePlate == null || vehiclePlate.length() != 7) {
            throw new IllegalArgumentException("Invalid vehicle plate!");
        }

        for (int i = 0; i < 3; i++) {
            if (vehiclePlate.charAt(i) < 'A'
                    || vehiclePlate.charAt(i) > 'Z') {
                throw new IllegalArgumentException("Invalid vehicle plate!");
            }
        }

        for (int i = 3; i < 7; i++) {
            if (vehiclePlate.charAt(i) < '0'
                    || vehiclePlate.charAt(i) > '9') {
                throw new IllegalArgumentException("Invalid vehicle plate!");
            }
        }

        this.vehiclePlate = vehiclePlate;
    }

    // Returns the vehicle type
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    // Sets the vehicle type
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Compares drivers by ID
    public int compareTo(IDriver other) {

        if (getId() < other.getId()) {
            return -1;
        } else if (getId() > other.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    // Returns driver information
    @Override
    public String toString() {
        return super.toString()
                + "\n\tVEHICLE PLATE: " + vehiclePlate
                + " | VEHICLE TYPE: " + vehicleType + ".";
    }
}
