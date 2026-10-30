
// Represents a private ride with one rider.
public class PrivateRide extends Ride implements IPrivateRide {

    private IRider rider;

    // Constructor, O(1)
    public PrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
                       String dropoffLocation, IDriver driver, IRider rider) {
        super(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        this.rider = rider;
    }

    // Returns the rider of this ride, O(1)
    public IRider getRider() {
        return rider;
    }

    // Sets the rider of this ride, O(1)
    public void setRider(IRider rider) {
        this.rider = rider;
    }

    // Checks if the rider has the given ID, O(1)
    public boolean hasRider(int riderId) {
        return rider != null && rider.getId() == riderId;
    }

    // Returns the ride information, O(1)
    public String toString() {
        return "PRIVATE " + super.toString() + "\n" +
                "Rider: " + rider;
    }
}
