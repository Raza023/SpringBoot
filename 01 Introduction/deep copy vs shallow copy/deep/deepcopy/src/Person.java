public class Person {

    private Name name;
    private Address address;

    public Person(Person originalPerson) {
        this.name = originalPerson.name;
        this.address = originalPerson.address;
    }

    // No-arg constructor
    public Person() {
    }

    // Parameterized constructor
    public Person(Name name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
