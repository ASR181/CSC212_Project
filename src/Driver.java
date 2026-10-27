public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || vehiclePlate.length() != 7)
            throw new IllegalArgumentException("Invalid vehicle plate!");

        for (int i = 0; i < 3; i++) {
            if (vehiclePlate.charAt(i) < 'A' || vehiclePlate.charAt(i) > 'Z')
                throw new IllegalArgumentException("Invalid vehicle plate!");
        }

        for (int i = 3; i < 7; i++) {
            if (vehiclePlate.charAt(i) < '0' || vehiclePlate.charAt(i) > '9')
                throw new IllegalArgumentException("Invalid vehicle plate!");
        }

        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int compareTo(IDriver other) {
        if (getId() < other.getId())
            return -1;
        else if (getId() > other.getId())
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "Driver Info:\n\tID: " + getId() + " | NAME: " + getName()
                + " | PHONE NUMBER: " + getPhoneNumber()
                + " | VEHICLE PLATE: " + vehiclePlate + " | VEHICLE TYPE: " + vehicleType + ".";
    }
}
```
