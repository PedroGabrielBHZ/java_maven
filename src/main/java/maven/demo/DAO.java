package maven.demo;

import java.sql.*;

public class DAO {

	private Connection connection;

	public DAO() {
		connection = null;
	}

	/**
	 * Connect to database server.
	 *
	 * @return boolean representing connection status
	 */
	public boolean connect() {

		// Connection data
		int port = 5432;
		String driver = "org.postgresql.Driver";
		String domain = "localhost";
		String database = "teste";
		String connectionURL = "jdbc:postgresql://" + domain + ":" + port + "/" + database;
		String user = "ti2cc";
		String password = "ti@cc";
		boolean connectionSuccesful = false;

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
	 * Close connection.
	 * @return connection closed succesfully.
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

	public static void main(String[] args) {
		System.out.println("FUCK ME");

		DAO dao = new DAO();

		dao.connect();

	}
}
