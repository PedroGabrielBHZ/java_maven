package maven.demo;

public class Principal {
	public static void main(String[] args) {
		DAO dao = new DAO();
		dao.connect();

		// Insert a component
		Component c1 = new Component(2, "CX8412", 3.12f, 823);
		if(dao.insertComponent(c1) == true) {
			System.out.println("Component inserted to DB -> " + c1.toString());
		}

		// Insert a component
		Component c2 = new Component(3, "ZYD231", 4.91f, 421);
		if(dao.insertComponent(c2) == true) {
			System.out.println("Component inserted to DB -> " + c2.toString());
		}

		// Update component
		c1.setName("CX8732");
		dao.updateComponent(c1);

		// Delete component
		dao.deleteComponent(c2.getId());

		// Display list of components
		Component[] components = dao.getComponents();
		System.out.println("==== Display components. === ");
		for(int i = 0; i < components.length; i++) {
			System.out.println(components[i].toString());
		}

		// Close connection
		dao.close();
	}
}
