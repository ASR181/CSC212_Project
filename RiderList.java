public class RiderList implements IRiderList{

    private LinkedList<IRider> riders;
    private int nbRiders;

    public RiderList(){
        riders = new LinkedList<IRider>();
        nbRiders = 0;
    }

    public boolean add(IRider rider){

        if(rider == null)
            return false;

        if(riders.empty()){
            riders.insert(rider);
            nbRiders++;
            return true;
        }
        riders.findFirst();

        while(true){
            IRider listRider = riders.retrieve();
            if(rider.getId() == listRider.getId())
                return false;

            if(rider.getId() < listRider.getId()){
                riders.update(rider);
                riders.insert(listRider);
                nbRiders++;
                return true;
            }

            if(riders.last()){
                riders.insert(rider);
                nbRiders++;
                return true;
            }
            riders.findNext();
        }
    }

    public IRider findById(int riderId){
                                    // any adjustments to current pointer in this method shall be followed
        if(riders.empty())          // by a check on removeById() method
            return null;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            IRider listRider = riders.retrieve();
            if(riderId == listRider.getId())
                return listRider;
            riders.findNext();
        }
        return null;
    }

    public LinkedList<IRider> findByName(String fullName){

        LinkedList<IRider> listOfRiders = new LinkedList<IRider>();
        if(riders.empty())
            return listOfRiders;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            if(fullName.equals(riders.retrieve().getName()))
                listOfRiders.insert(riders.retrieve());
            riders.findNext();
        }
        return listOfRiders;
    }

    public IRider findByEmail(String email){
        if(riders.empty() || email == null)
            return null;
                                            // any adjustments to current pointer in this method shall be followed
        riders.findFirst();                 // by a check on removeById() method
        for(int i = 0; i < nbRiders; i++){
            IRider listRider = riders.retrieve();
            if(email.equals(listRider.getEmail()))
                return listRider;
            riders.findNext();
        }
        return null;
    }

    public LinkedList<IRider> findByHomeCity(String homeCity){

        LinkedList<IRider> listOfRiders = new LinkedList<IRider>();

        if(riders.empty())
            return listOfRiders;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            if(homeCity.equals(riders.retrieve().getHomeCity()))
                listOfRiders.insert(riders.retrieve());
            riders.findNext();
        }
        return listOfRiders;
    }

    public LinkedList<IRider> getAll(){
        LinkedList<IRider> listOfRiders = new LinkedList<IRider>();
        if(riders.empty())
            return listOfRiders;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            listOfRiders.insert(riders.retrieve());
            riders.findNext();
        }
        return listOfRiders;
    }

    public boolean removeById(int riderId){

        if(findById(riderId) != null){      // current is set on the rider we wish to remove bt findById() method
            riders.remove();
            nbRiders--;
            return true;
        }
        return false;
    }

    public boolean removeByEmail(String email){

        if(findByEmail(email) != null){      // current is set on the rider we wish to remove bt findByEmail() method
            riders.remove();
            nbRiders--;
            return true;
        }
        return false;
    }

    public int removeByName(String fullName){

        int count = 0;
        if(riders.empty() || fullName == null)
            return count;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            if(fullName.equals(riders.retrieve().getName())){
                riders.remove();
                count++;
            }
            else
                riders.findNext();
        }
        nbRiders = nbRiders - count;
        return count;
    }

    public int removeByHomeCity(String homeCity){

        int count = 0;
        if(riders.empty() || homeCity == null)
            return count;

        riders.findFirst();
        for(int i = 0; i < nbRiders; i++){
            if(homeCity.equals(riders.retrieve().getHomeCity())){
                riders.remove();
                count++;
            }
            else
                riders.findNext();
        }
        nbRiders = nbRiders - count;
        return count;
    }


    public int size(){
        return nbRiders;
    }
}
