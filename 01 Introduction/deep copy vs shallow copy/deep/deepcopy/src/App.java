public class App {
	public static void main(String[] args) throws Exception {
		Name name2 = new Name("Ali", "Bin", "Ahmed");
		Address address2 = new Address("12A", "5", "Model Town", "Lahore", "Pakistan");
		Person original2 = new Person(name2, address2);
		Person deepCopyPerson = new Person(
				new Name(original2.getName().getFirstName(), original2.getName().getMiddleName(),
						original2.getName().getLastName()),
				new Address(
						original2.getAddress().getHouseNo(),
						original2.getAddress().getStreetNo(),
						original2.getAddress().getColony(),
						original2.getAddress().getCity(),
						original2.getAddress().getCountry()));
		original2.getAddress().setCity("Karachi");
		System.out.println("Original Name: " + original2.getName().getFirstName() + " " +
				original2.getName().getMiddleName() + " " + original2.getName().getLastName());
		System.out.println("Original Address: " + original2.getAddress().getHouseNo() + ", " +
				original2.getAddress().getStreetNo() + ", " + original2.getAddress().getColony() + ", "
				+
				original2.getAddress().getCity() + ", " + original2.getAddress().getCountry());
		System.out.println("Deep Copy Name: " + deepCopyPerson.getName().getFirstName() + " " +
				deepCopyPerson.getName().getMiddleName() + " "
				+ deepCopyPerson.getName().getLastName());
		System.out.println("Deep Copy Address: " + deepCopyPerson.getAddress().getHouseNo() + ", "
				+ deepCopyPerson.getAddress().getStreetNo() + ", " + deepCopyPerson.getAddress().getColony() + ", "
				+ deepCopyPerson.getAddress().getCity() + ", " + deepCopyPerson.getAddress().getCountry());
	}
}
