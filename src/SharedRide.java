// Represents a shared ride with one or more riders
public class SharedRide extends Ride implements ISharedRide {

    private LinkedList<IRider> participants;
    private int nbParticipants;

    // Constructor, O(1)
    public SharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,String dropoffLocation, IDriver driver) {
        super(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        participants = new LinkedList<IRider>();
        nbParticipants = 0;
    }

    // Returns the list of riders, O(1)
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    // Adds a rider to the list, O(n)
    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId()))
            return false;

        if (!participants.empty()) {
            participants.findFirst();
            while (!participants.last())
                participants.findNext();
        }

        participants.insert(rider);
        nbParticipants++;
        return true;
    }

    // Removes a rider by ID, O(n)
    public boolean removeParticipantById(int riderId) {
        if (participants.empty())
            return false;

        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                nbParticipants--;
                return true;
            }
            participants.findNext();
        }

        if (participants.retrieve().getId() == riderId) {
            participants.remove();
            nbParticipants--;
            return true;
        }

        return false;
    }

    // Checks if the list is empty, O(1)
    public boolean isEmpty() {
        return participants.empty();
    }

    // Checks if a rider with the given ID exists, O(n)
    public boolean hasRider(int riderId) {
        if (participants.empty())
            return false;

        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId)
                return true;
            participants.findNext();
        }

        return participants.retrieve().getId() == riderId;
    }

    // Returns the ride information, O(1)
    public String toString() {
        return "SHARED " + super.toString() + "\n" +
                "Number of riders: " + nbParticipants;
    }
}
