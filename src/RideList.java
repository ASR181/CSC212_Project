public class RideList implements IRideList {
    private LinkedList<IRide> rides;
    private int nbRides;
    // constructor
    public RideList() { //O(1)
        nbRides = 0;
        rides = new LinkedList<>();
    }
    // returns the number of rides
    public int size() {  // O(1)
        return nbRides;
    }
    // returns a new list of all the rides in alphabetical order based on their pickup location
    public LinkedList<IRide> getAllAlphabetically(){ // O(n)
        LinkedList<IRide> all = new LinkedList<IRide>();
        if(rides.empty())
            return all;

        rides.findFirst();

        while (!rides.last()) {
            all.insert(rides.retrieve());
            rides.findNext();
        }
        all.insert(rides.retrieve());
        return all;
    }
    // returns all rides with this pickup location
    public LinkedList<IRide> findByPickupLocation(String pickupLocation){ // O(n)
        LinkedList<IRide> matches = new LinkedList<IRide>();

        if(rides.empty() || pickupLocation == null)
            return matches;

        rides.findFirst();
        while(!rides.last()){
            if(rides.retrieve().getPickupLocation().equals(pickupLocation)){
                matches.insert(rides.retrieve());
            }
            rides.findNext();
        } if(rides.retrieve().getPickupLocation().equals(pickupLocation)){
            matches.insert(rides.retrieve());
        } return matches;
    }
    // returns all rides for this rider
    public LinkedList<IRide> findByRiderName(String fullName){ // O(n + p) , p is shared ride participants testes
        LinkedList<IRide> matches = new LinkedList<IRide>();

        if(rides.empty() || fullName == null)
            return matches;

        rides.findFirst();
        while(true){
            IRide ride = rides.retrieve();
            if(ride instanceof IPrivateRide){
                IPrivateRide privateRide = (IPrivateRide) ride;
                if(privateRide.getRider().getName().equals(fullName)){
                    matches.insert(ride);
                }
            }
            if(ride instanceof ISharedRide) {
                ISharedRide sharedRide = (ISharedRide) ride;
                LinkedList<IRider> participants = sharedRide.getParticipants();

                if(!participants.empty()){
                    participants.findFirst();
                    while(true){
                        if(participants.retrieve().getName().equals(fullName)){
                            matches.insert(ride);
                            break;
                        }
                        if(participants.last())
                            break;
                        participants.findNext();
                    }
                }
            }
            if(rides.last())
                break;
            rides.findNext();

        }
        return matches;
    }
    // removes the rides with the given id
    public boolean removeRideById(int rideId) { // O(n)
        if (rides.empty())
            return false;
        rides.findFirst();
        while (!rides.last()) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                nbRides--;
                return true;
            }
            rides.findNext();
        }
        if(rides.retrieve().getRideId() == rideId) {
            rides.remove();
            nbRides--;
            return true;
        }
        return false;
    }
    // adds a ride to the list in the alphabetical order by their pickup location
    public boolean addRide(IRide ride){ // O(n)
        if(ride == null)
            return false;

        if(rides.empty()) {
            rides.insert(ride);
            nbRides++;
            return true;
        }
        rides.findFirst();
        IRide temp = rides.retrieve();

        while (!rides.last()) {
            if (rides.retrieve().compareTo(ride) > 0) {
                temp = rides.retrieve();
                rides.update(ride);
                rides.insert(temp);
                nbRides++;
                return true;
            }
            rides.findNext();
        }
        if (rides.retrieve().compareTo(ride) > 0) {
            temp = rides.retrieve();
            rides.update(ride);
            rides.insert(temp);
            nbRides++;
            return true;
        } else {
            rides.insert(ride);
            nbRides++;
            return true;
        }
    }
}
