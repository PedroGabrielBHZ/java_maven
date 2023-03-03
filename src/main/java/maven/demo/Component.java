package maven.demo;

/**
 * Component model class.
 */
public class Component {

	// Data model attributes
	private int id;
	private String name;
	private float price;
	private int stock;

	// Default constructor
	public Component() {
		this.id = -1;
		this.name = "";
		this.price = 0;
		this.stock = 0;
	}

	// Constructor
	public Component(int id, String name, float price, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	/*
	 * Getters and Setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	// String representation of the object
	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + "]";
	}
}
