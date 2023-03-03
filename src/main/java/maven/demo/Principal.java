package maven.demo;

/**
 * This class is the main class of the application.
 * It is used to test the DAO class.
 *
 * @author Pedro Gabriel Amorim Soares
 * @version 1.0
 */
public class Principal {
	public static void main(String[] args) {
		// Connect to the database
		DAO dao = new DAO();
		dao.connect();

		// Insert a component
		Component c1 = new Component(2, "CX8412", 3.12f, 823);
		if (dao.insertComponent(c1) == true) {
			System.out.println("Component inserted to DB -> " + c1.toString());
		} else {
			System.out.println("Component not inserted to DB -> " + c1.toString());
		}

		// Insert another component
		Component c2 = new Component(3, "CX8413", 3.13f, 824);
		if (dao.insertComponent(c2) == true) {
			System.out.println("Component inserted to DB -> " + c2.toString());
		} else {
			System.out.println("Component not inserted to DB -> " + c2.toString());
		}

		// Set the name of the component
		c1.setName("CX8732");

		// Update the component
		dao.updateComponent(c1);

		// Delete component
		dao.deleteComponent(c2.getId());

		// Display list of components.
		Component[] components = dao.getComponents();
		System.out.println("==== Display components === ");
		for (int i = 0; i < components.length; i++) {
			System.out.println(components[i].toString());
		}

		// Close connection
		dao.close();
	}
}
