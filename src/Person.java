public abstract class Person implements IPerson {

    private final int id;       // once initialized, never changed
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber) {
    this.id = id;
    rideHistory = new LinkedList<IRide>();
    setName(name);
    setPhoneNumber(phoneNumber);
}

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name =name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.length() != 10)       // check for null or wrong input size
            throw new IllegalArgumentException("Phone number must consist of 10 digits!");

        for(int i = 0; i < phoneNumber.length(); i++){
            char letter = phoneNumber.charAt(i);        // going through each character in the string
            if(letter > '9' || letter < '0')          // checking for range (for english character or any other symbol, ASCII code is never in this range)
                throw new IllegalArgumentException("Phone number must consist of only numeric characters!");
        }
        this.phoneNumber = phoneNumber;
    }

    public LinkedList<IRide> getRideHistory() {
    return rideHistory;
    }

    public String toString(){
        return "Person Info:\n\tID: " + id + " | NAME: " + name + " | PHONE NUMBER: " + phoneNumber + ".";
    }
}
