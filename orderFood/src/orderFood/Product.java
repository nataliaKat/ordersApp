package orderFood;


public class Product {
	
	private static int counter;
	private String name;
	private double pricePerUnit;
	private final int id;
	
	public Product(String name, double pricePerUnit) {
		this.name = name;
		this.pricePerUnit = pricePerUnit;
		counter++;
		id = counter;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public String toString() {
		String product = "-----------------\n" +
				"Προϊόν " + name + "\n" +
				"ID " + id + "\n" +
				"Τιμή ανά Τεμάχιο " + pricePerUnit + "\n" +
				"-----------------\n";
		return product;
				
	}
	
	
}
