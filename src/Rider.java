public class Rider extends Person implements IRider {

    private String email;
    private String homeCity;

    public Rider(int id, String name, String email, String phoneNumber, String homeCity) {
        super(id, name, phoneNumber);
        setEmail(email);
        setHomeCity(homeCity);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public int compareTo(IRider other) {
        if (getId() < other.getId())
            return -1;
        else if (getId() > other.getId())
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "Rider Info:\n\tID: " + getId() + " | NAME: " + getName()
                + " | PHONE NUMBER: " + getPhoneNumber()
                + " | EMAIL: " + email + " | HOME CITY: " + homeCity + ".";
    }
}
```
