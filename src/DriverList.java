public class DriverList implements IDriverList {
    private LinkedList<IDriver> drivers;
    private int nbDrivers;
    // constructor
    public DriverList() { //O(1)
        nbDrivers = 0;
        drivers = new LinkedList<>();
    }
    // returns tge nubmer of rides, O(1)
    public int size() {
        return nbDrivers;
    }
    // returns the driver with the given id
    public IDriver findById(int driverId) { // O(n)
        if (drivers.empty())
            return null;

        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getId() == driverId) {
                return drivers.retrieve();
            } else
                drivers.findNext();
        }
        if (drivers.retrieve().getId() == driverId) {
            return drivers.retrieve();
        }
        return null;
    }
    // returns the driver with the given plate
    public IDriver findByVehiclePlate(String vehiclePlate) { // O(n)
        if (drivers.empty() || vehiclePlate == null)
            return null;

        drivers.findFirst();
        while (!drivers.last()) {
            if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate))
                return drivers.retrieve();

            drivers.findNext();
        }
        if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate))
            return drivers.retrieve();

        return null;
    }
    // returns the driver with the given name
    public LinkedList<IDriver> findByName(String fullName){ // O(n)
        LinkedList<IDriver> matches = new LinkedList<IDriver>();

        if(drivers.empty() || fullName == null)
            return matches;

        drivers.findFirst();
        while(!drivers.last()){
            if(drivers.retrieve().getName().equals(fullName)){
                matches.insert(drivers.retrieve());
            }
            drivers.findNext();
        } if(drivers.retrieve().getName().equals(fullName)){
            matches.insert(drivers.retrieve());
        } return matches;
    }
    // returns the driver with the given vehicle type
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) { // O(n)
        LinkedList<IDriver> matches = new LinkedList<IDriver>();

        if (drivers.empty() || vehicleType == null)
            return matches;

        drivers.findFirst();
        while (!drivers.last()) {
            if (drivers.retrieve().getVehicleType() == vehicleType) { //we used == instead of .equals to handle null vehicle type
                matches.insert(drivers.retrieve());
            }
            drivers.findNext();
        }
        if (drivers.retrieve().getVehicleType() == vehicleType) {
            matches.insert(drivers.retrieve());
        }
        return matches;
    }
    // returns a new list of all the drivers in an ascending id order
    public LinkedList<IDriver> getAll(){ // O(n)
        LinkedList<IDriver> all = new LinkedList<IDriver>();
        if(drivers.empty())
            return all;

        drivers.findFirst();

        while (!drivers.last()) {
            all.insert(drivers.retrieve());
            drivers.findNext();
        }
        all.insert(drivers.retrieve());
        return all;
    }
    // removes the driver with the given id
    public boolean removeById(int driverId) { // O(n)
        if (drivers.empty())
            return false;
        drivers.findFirst();
        while (!drivers.last()) {
            if (drivers.retrieve().getId() == driverId) {
                drivers.remove();
                nbDrivers--;
                return true;
            }
            drivers.findNext();
        }
        if(drivers.retrieve().getId() == driverId) {
            drivers.remove();
            nbDrivers--;
            return true;
        }
        return false;
    }
    // removes the driver with the given plate
    public boolean removeByVehiclePlate(String vehiclePlate) { // O(n)
        if(drivers.empty() || vehiclePlate == null) return false;
        drivers.findFirst();
        while (!drivers.last()) {
            if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate)){
                drivers.remove();
                nbDrivers--;
                return true;
            } drivers.findNext();
        }
        if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate)){
            drivers.remove();
            nbDrivers--;
            return true;
        }
        return false;
    }
    // removes the driver with the given name
    public int removeByName(String fullname){ // O(n^2)
        if(drivers.empty() || fullname == null)
            return 0;
        int numberDrivers = nbDrivers;
        int count = 0;
        drivers.findFirst();

        for(int i=0 ; i<numberDrivers ; i++){
            if(drivers.retrieve().getName().equals(fullname)){
                drivers.remove();
                count++;
                nbDrivers--;
            }else drivers.findNext();
        }
        return count;
    }
    // removes the driver with the given type
    public int removeByVehicleType(VehicleType vehicleType) { // O(n^2)
        if(drivers.empty() || vehicleType == null)
            return 0;
        int numberDrivers = nbDrivers;
        int count = 0;
        drivers.findFirst();

        for(int i=0 ; i<numberDrivers ; i++){
            if(drivers.retrieve().getVehicleType() == vehicleType){
                drivers.remove();
                count++;
                nbDrivers--;
            }else drivers.findNext();
        }
        return count;
    }
    // adds a driver but it must reject duplicate ids,plate numbers and then insert in id order
    public boolean add(IDriver driver){ // O(n)
        if(driver == null)
            return false;
        if(findById(driver.getId()) != null)
            return false;
        if(findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false;

        if(drivers.empty()) {
            drivers.insert(driver);
            nbDrivers++;
            return true;
        }

        drivers.findFirst();
        IDriver temp = drivers.retrieve();

        while (!drivers.last()) {
            if (drivers.retrieve().getId() > driver.getId()) {
                temp = drivers.retrieve();
                drivers.update(driver);
                drivers.insert(temp);
                nbDrivers++;
                return true;
            }
            drivers.findNext();
        }
        if (drivers.retrieve().getId() > driver.getId()) {
            temp = drivers.retrieve();
            drivers.update(driver);
            drivers.insert(temp);
            nbDrivers++;
            return true;
        } else {
            drivers.insert(driver);
            nbDrivers++;
            return true;
        }
    }

}
