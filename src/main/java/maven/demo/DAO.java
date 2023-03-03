package maven.demo;

import java.sql.*;

public class DAO {

	/**
	 * Connection to the database.
	 */
	private Connection connection;

	/**
	 * Constructor.
	 */
	public DAO() {
		connection = null;
	}

	/**
	 * Connect to the database.
	 *
	 * @return boolean representing connection status
	 */
	public boolean connect() {

		// Connection data
		int port = 15432;
		String driver = "org.postgresql.Driver";
		String domain = "localhost";
		String database = "teste";
		String connectionURL = "jdbc:postgresql://" + domain + ":" + port + "/" + database;
		String user = "ti2cc";
		String password = "ti@cc";
		boolean connectionSuccesful = false;

		// Connect to the database
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionURL, user, password);
			connectionSuccesful = (connection == null);
			System.out.println("Connection established.");
		} catch (ClassNotFoundException e) {
			System.err.println("Connection failed -- Driver not found -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conection failed -- " + e.getMessage());
		}

		return connectionSuccesful;
	}

	/**
	 * Close connection to database server.
	 *
	 * @return boolean representing connection status
	 */
	public boolean close() {
		boolean status = false;

		try {
			connection.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	/**
	 * Insert a component in the database.
	 *
	 * @param component to be inserted.
	 * @return true if the component was inserted, false otherwise.
	 */
	public boolean insertComponent(Component component) {
		boolean status = false;
		try {
			Statement st = connection.createStatement();
			st.executeUpdate("INSERT INTO component (id, name, price, stock) "
					+ "VALUES (" + component.getId() + ", '" + component.getName() + "', '"
					+ component.getPrice() + "', '" + component.getStock() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	/**
	 * Update a component in the database.
	 *
	 * @param component to be updated.
	 * @return true if the component was updated, false otherwise.
	 */
	public boolean updateComponent(Component component) {
		boolean status = false;
		try {
			Statement st = connection.createStatement();
			String sql = "UPDATE component SET name = '" + component.getName() + "', price = '"
					+ component.getPrice() + "', stock = '" + component.getStock() + "'"
					+ " WHERE id = " + component.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	/**
	 * Delete a component from the database.
	 *
	 * @param id of the component to be deleted.
	 * @return true if the component was deleted, false otherwise.
	 */
	public boolean deleteComponent(int id) {
		boolean status = false;
		try {
			Statement st = connection.createStatement();
			st.executeUpdate("DELETE FROM component WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	/**
	 * Returns an array of all components in the database.
	 *
	 * @return an array of all components in the database.
	 */
	public Component[] getComponents() {
		Component[] components = null;

		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM component");
			if (rs.next()) {
				rs.last();
				components = new Component[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					components[i] = new Component(rs.getInt("id"), rs.getString("name"),
							rs.getFloat("price"), rs.getInt("stock"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return components;
	}

	/**
	 * Returns a component with the given id. Returns null if no component with the
	 * given id exists.
	 *
	 * @param id the id of the component to return
	 * @return the component with the given id, or null if no component with the
	 *         given id exists.
	 */
	public Component getComponent(int id) {
		Component component = null;

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM component WHERE id = " + id);
			if (rs.next()) {
				component = new Component(rs.getInt("id"), rs.getString("name"),
						rs.getFloat("price"), rs.getInt("stock"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return component;
	}
}
