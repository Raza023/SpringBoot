public class Address {
    private String houseNo;
    private String streetNo;
    private String colony;
    private String city;
    private String country;

    // No-arg constructor
    public Address() {
    }

    // All-args constructor
    public Address(String houseNo, String streetNo, String colony, String city, String country) {
        this.houseNo = houseNo;
        this.streetNo = streetNo;
        this.colony = colony;
        this.city = city;
        this.country = country;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
