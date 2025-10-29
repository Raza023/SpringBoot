public class App {
	public static void main(String[] args) throws Exception {
		Name name = new Name("Ali", "Bin", "Ahmed");
		Address address = new Address("12A", "5", "Model Town", "Lahore", "Pakistan");
		Person original = new Person(name, address);
		Person shallowCopyPerson = new Person(original);

		original.getAddress().setCity("Karachi");

		System.out.println("Original Name: " + original.getName().getFirstName() + " " +
				original.getName().getMiddleName() + " " + original.getName().getLastName());
		System.out.println("Original Address: " + original.getAddress().getHouseNo() + ", " +
				original.getAddress().getStreetNo() + ", " + original.getAddress().getColony() + ", " +
				original.getAddress().getCity() + ", " + original.getAddress().getCountry());
		System.out.println("Shallow Copy Name: " + shallowCopyPerson.getName().getFirstName() + " " +
				shallowCopyPerson.getName().getMiddleName() + " "
				+ shallowCopyPerson.getName().getLastName());
		System.out.println("Shallow Copy Address: " + shallowCopyPerson.getAddress().getHouseNo() + ", " +
				shallowCopyPerson.getAddress().getStreetNo() + ", "
				+ shallowCopyPerson.getAddress().getColony() + ", " +
				shallowCopyPerson.getAddress().getCity() + ", "
				+ shallowCopyPerson.getAddress().getCountry());
	}
}
