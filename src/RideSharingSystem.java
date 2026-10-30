import java.io.*;
import java.util.Scanner;

public class RideSharingSystem implements IRideSharingSystem {

    private RiderList riders;
    private DriverList drivers;
    private RideList rides;

    public RideSharingSystem(){
        riders = new RiderList();
        drivers = new DriverList();
        rides = new RideList();
    }

    public boolean loadRidersFromCSV(String ridersFilePath){

        Scanner input = null;
        try{
            input = new Scanner(new File(ridersFilePath));
            while(input.hasNextLine()){
                String riderData = input.nextLine().trim();
                if(riderData.isEmpty())
                    continue;
                try{
                    String[] data = riderData.split(",");
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String phoneNumber = data[3].trim();
                    String city = data[4].trim();

                    Rider rider = new Rider(id, name, email, phoneNumber, city);
                    addRider(rider);
                } catch(IllegalArgumentException e){

                } catch(ArrayIndexOutOfBoundsException e){

                }
            }
            return true;
        } catch(FileNotFoundException e){
            return false;
        } finally {
            if(input != null)
                input.close();
        }
    }

    public boolean loadDriversFromCSV(String driversFilePath){

        Scanner input = null;
        try{
            input = new Scanner(new File(driversFilePath));
            while(input.hasNextLine()){
                String driverData = input.nextLine().trim();
                if(driverData.isEmpty())
                    continue;
                try{
                    String[] data = driverData.split(",");
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String phoneNumber = data[2].trim();
                    String vehiclePlate = data[3].trim();
                    VehicleType vehicleType = VehicleType.valueOf(data[4].trim());

                    Driver driver = new Driver(id, name, phoneNumber, vehiclePlate, vehicleType);
                    addDriver(driver);
                } catch(IllegalArgumentException e){

                } catch(ArrayIndexOutOfBoundsException e){

                }
            }
            return true;
        } catch(FileNotFoundException e){
            return false;
        } finally {
            if(input != null)
                input.close();
        }
    }

    public boolean loadRidesFromCSV(String ridesFilePath){

        Scanner input = null;
        try{
            input = new Scanner(new File(ridesFilePath));
            while(input.hasNextLine()){
                String rideData = input.nextLine().trim();
                if(rideData.isEmpty())
                    continue;
                try{
                    String[] data = rideData.split(",");
                    String rideType = data[0].trim();
                    String pickupLocation = data[1].trim();
                    IDateTime pickupTime = formatDateTime(data[2]);
                    IDateTime dropoffTime = formatDateTime(data[3]);
                    String dropoffLocation = data[4].trim();
                    int driverID = Integer.parseInt(data[5].trim());
                    int[] riderIDs = formatRiderIds(data[6]);

                    if(rideType.equals("PRIVATE"))
                        schedulePrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIDs[0], driverID);
                    else if(rideType.equals("SHARED"))
                        scheduleSharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIDs, driverID);

                } catch(IllegalArgumentException e){

                } catch(ArrayIndexOutOfBoundsException e){

                }
            }
            return true;
        } catch(FileNotFoundException e){
            return false;
        } finally {
            if(input != null)
                input.close();
        }
    }

    private IDateTime formatDateTime(String text){
        String[] dateAndTime = text.trim().split(" ");
        String[] date = dateAndTime[0].split("/");
        String[] time = dateAndTime[1].split(":");

        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        IDateTime result = new DateTime(year, month, day, hour, minute);

        return result;
    }

    private int[] formatRiderIds(String data){
        String[] records = data.trim().split(";");
        int[] ids = new int[records.length];
        for(int i = 0; i < records.length; i++)
            ids[i] = Integer.parseInt(records[i].trim());
        return ids;
    }

    public boolean addRider(IRider rider){

        if(rider == null)
            return false;

        if(riders.findByEmail(rider.getEmail()) != null)
            return false;

        boolean result = riders.add(rider);
        return result;
    }

    public boolean addDriver(IDriver driver){

        if(driver == null)
            return false;

        if(drivers.findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false;

        boolean result = drivers.add(driver);
        return result;
    }

    public IRider searchRiderById(int riderId){
        return riders.findById(riderId);
    }

    public IRider searchRiderByEmail(String email){
        return riders.findByEmail(email);
    }

    public LinkedList<IRider> searchRidersByName(String fullName){
        return riders.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity){
        return riders.findByHomeCity(homeCity);
    }

    public LinkedList<IRider> getAllRiders(){
        return riders.getAll();
    }

    public IDriver searchDriverById(int driverId){
        return drivers.findById(driverId);
    }

    public IDriver searchDriverByVehiclePlate(String vehiclePlate){
        return drivers.findByVehiclePlate(vehiclePlate);
    }

    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType){
        return drivers.findByVehicleType(vehicleType);
    }

    public LinkedList<IDriver> getAllDrivers(){
        return drivers.getAll();
    }

    public boolean removeRider(int riderId){
        IRider rider = riders.findById(riderId);
        if(rider == null)
            return false;

        LinkedList<IRide> history = rider.getRideHistory();
        if(!history.empty()){
            history.findFirst();
            while(true){
                IRide ride = history.retrieve();

                if(ride instanceof IPrivateRide){

                    rides.removeRideById(ride.getRideId());
                    removeFromHistory(ride.getDriver().getRideHistory(), ride.getRideId());
                }
                else if(ride instanceof ISharedRide){
                    ISharedRide shared = (ISharedRide) ride;
                    shared.removeParticipantById(riderId);
                    if(shared.isEmpty()){
                        rides.removeRideById(ride.getRideId());
                        removeFromHistory(ride.getDriver().getRideHistory(), ride.getRideId());
                    }
                }

                if(history.last())
                    break;
                history.findNext();
            }
        }

        riders.removeById(riderId);
        return true;
    }

    private void removeFromHistory(LinkedList<IRide> history, int rideId){
        if(history.empty())
            return;
        history.findFirst();
        while(true){
            if(history.retrieve().getRideId() == rideId){
                history.remove();
                return;
            }
            if(history.last())
                return;
            history.findNext();
        }
    }

    public boolean removeDriver(int driverId){

        IDriver driver = drivers.findById(driverId);
        if(driver == null)
            return false;

        LinkedList<IRide> history = driver.getRideHistory();
        if(!history.empty()){
            history.findFirst();
            while(true){
                IRide ride = history.retrieve();
                rides.removeRideById(ride.getRideId());

                if(ride instanceof IPrivateRide){
                    IRider rider = ((IPrivateRide) ride).getRider();
                    removeFromHistory(rider.getRideHistory(), ride.getRideId());
                }
                else if(ride instanceof ISharedRide){
                    LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
                    if(!participants.empty()){
                        participants.findFirst();
                        while(true){
                            removeFromHistory(participants.retrieve().getRideHistory(), ride.getRideId());
                            if(participants.last())
                                break;
                            participants.findNext();
                        }
                    }
                }

                if(history.last())
                    break;
                history.findNext();
            }
        }

        drivers.removeById(driverId);
        return true;
    }

    private boolean timeIsValid(IDateTime pickup, IDateTime dropoff){
        return pickup != null && dropoff != null && pickup.compareTo(dropoff) < 0;
    }

    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){

        if(!timeIsValid(pickupTime, dropoffTime))
            return false;

        IRider rider = riders.findById(riderId);
        IDriver driver = drivers.findById(driverId);
        if(rider == null || driver == null)
            return false;

        if(hasConflict(rider, pickupTime, dropoffTime) || hasConflict(driver, pickupTime, dropoffTime))
            return false;

        IRide ride = new PrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver, rider);
        rides.addRide(ride);
        addToHistory(rider, ride);
        addToHistory(driver, ride);
        return true;
    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId){

        if(riderIds == null || riderIds.length == 0 || !timeIsValid(pickupTime, dropoffTime))
            return false;

        IDriver driver = drivers.findById(driverId);
        if(driver == null || hasConflict(driver, pickupTime, dropoffTime))
            return false;


        IRider[] rideRiders = new IRider[riderIds.length];
        for(int i = 0; i < riderIds.length; i++){
            for(int j = 0; j < i; j++)
                if(riderIds[j] == riderIds[i])
                    return false;

            rideRiders[i] = riders.findById(riderIds[i]);
            if(rideRiders[i] == null || hasConflict(rideRiders[i], pickupTime, dropoffTime))
                return false;
        }

        ISharedRide ride = new SharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        for(int i = 0; i < rideRiders.length; i++){
            ride.addParticipant(rideRiders[i]);
            addToHistory(rideRiders[i], ride);
        }
        rides.addRide(ride);
        addToHistory(driver, ride);
        return true;
    }

    private boolean hasConflict(IPerson person, IDateTime pickup, IDateTime dropoff){
        LinkedList<IRide> history = person.getRideHistory();
        if(history.empty())
            return false;
        history.findFirst();
        while(true){
            IRide ride = history.retrieve();
            if(pickup.compareTo(ride.getDropoffTime()) < 0 && ride.getPickupTime().compareTo(dropoff) < 0)
                return true;
            if(history.last())
                return false;
            history.findNext();
        }
    }

    private void addToHistory(IPerson person, IRide ride){
        LinkedList<IRide> history = person.getRideHistory();
        if(!history.empty()){
            history.findFirst();
            while(!history.last())
                history.findNext();
        }
        history.insert(ride);
    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation){
        return rides.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName){
        return rides.findByRiderName(riderName);
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation){

        LinkedList<IRider> result = new LinkedList<IRider>();
        LinkedList<IRide> ridesAtPickup = rides.findByPickupLocation(pickupLocation);
        if(ridesAtPickup.empty())
            return result;

        ridesAtPickup.findFirst();
        while(true){
            IRide ride = ridesAtPickup.retrieve();
            if(ride instanceof ISharedRide){

                LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
                if(!participants.empty()){
                    participants.findFirst();
                    while(true){
                        result.insert(participants.retrieve());
                        if(participants.last())
                            break;
                        participants.findNext();
                    }
                }
                return result;
            }
            if(ridesAtPickup.last())
                return result;
            ridesAtPickup.findNext();
        }
    }

    public LinkedList<IRide> getAllRidesAlphabetically(){
        return rides.getAllAlphabetically();
    }

}
