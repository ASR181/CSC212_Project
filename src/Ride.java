public abstract class Ride implements IRide {
    private final int rideId;
    private static int nextId = 0;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;
    private String pickupLocation;
    private String dropoffLocation;
    private IDriver driver;
    //constructor
    protected Ride(IDateTime pickupTime, IDateTime dropoffTime,String pickupLocation, String dropoffLocation,IDriver driver){
        this.rideId = ++nextId; // return the right id number and adds to the next
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.driver = driver;
    }
    //getters
    //returns ride id, O(1)
    public int getRideId(){
        return rideId;
    } //returns the pickup date + time, O(1)
    public IDateTime getPickupTime(){
        return pickupTime;
    } // returns the drop off date + time, O(1)
    public IDateTime getDropoffTime(){
        return dropoffTime;
    } // updates the pickup location, O(1)
    public String getPickupLocation(){
        return pickupLocation;
    } // updates the drop off location, O(1)
    public String getDropoffLocation(){
        return dropoffLocation;
    } // return the assigned drive, O(1)
    public IDriver getDriver(){
        return driver;
    }
    //setters
    // updates the drop off location, O(1)
    public void setDropoffLocation(String dropoffLocation){
        this.dropoffLocation = dropoffLocation;
    } // updates the pickup location, O(1)
    public void setPickupLocation(String pickupLocation){
        this.pickupLocation = pickupLocation;
    } // updates the assigned driver, O(1)
    public void setDriver(IDriver driver){
    this.driver = driver;
    }
    // compares rides alphabetically by their pickup location, O(1)
    public int compareTo(IRide other){
       return this.pickupLocation.compareTo(other.getPickupLocation());
    }
    // subclasses implement the rider check
    public abstract boolean hasRider(int riderId);
    // returns a description of the ride, O(1)
    public String toString(){
        return ("Ride ID: "+rideId +"\n"+
                "Pickup Location: "+pickupLocation+", Drop off Location: "+dropoffLocation+"\n" +
                "Pickup Time: "+pickupTime.format()+", Drop off Time: "+dropoffTime.format()+"\n" +
                "Driver: "+ driver );
    }

}

