/**
 * Represents a rider in the ride-sharing system
 */
public class Rider extends Person implements IRider {

    private String email;
    private String homeCity;

    /**
     * Creates a rider with personal information
     */
    public Rider(int id, String name, String email,
                 String phoneNumber, String homeCity) {

        super(id, name, phoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }

    /**
     * Returns the rider's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the rider's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the rider's home city
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     * Sets the rider's home city
     */
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    /**
     * Compares riders by their IDs
     */
    public int compareTo(IRider other) {

        if (getId() < other.getId()) {
            return -1;
        } else if (getId() > other.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns the rider's information as a string
     */
    public String toString() {
        return "Rider Info:\n\tID: " + getId()
                + " | NAME: " + getName()
                + " | PHONE NUMBER: " + getPhoneNumber()
                + " | EMAIL: " + email
                + " | HOME CITY: " + homeCity + ".";
    }
}
