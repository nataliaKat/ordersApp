package orderFood;

import java.util.ArrayList;

public class Shop {
	private static ArrayList<Shop> shops = new ArrayList<Shop>();
	
	private static int counter;
	private String name;
	private String address;
	private final int id;
	private long phoneNumber;
	
	private ArrayList<Product> items = new ArrayList<Product>();

	public Shop(String name, String address, long l, ArrayList<Product> items) {
		this.name = name;
		this.address = address;
		this.phoneNumber = l;
		this.items = items;
		counter++;
		id = counter;
		shops.add(this);
	}
	
	public boolean productIdExists(int id) {
		for (Product something : items) {
			if (id == something.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public String printProductName(int id) {
		for (Product something : items) {
			if (id == something.getId()) {
				return something.getName();
			}
		} 
		return "δεν υπάρχει το id"; //μπαίνει για τυπικούς λόγους, δε θα γίνει αν πραγματοποιηθεί ο έλεγχος από πάνω  
	}
	
	public double printProductPrice(int id) {
		for (Product something : items) {
			if (id == something.getId()) {
				return something.getPricePerUnit();
			} 
		}
		return -8;//μπαίνει για τυπικούς λόγους, δε θα γίνει αν πραγματοποιηθεί ο έλεγχος από πάνω  
	}
	
	public void printCatalogue() {
		for (Product something : items) {
			System.out.println("--------------------");
			System.out.println("ID: " + something.getId());
			System.out.println("Όνομα προϊόντος: " + printProductName(something.getId()));
			System.out.println("Τιμή ανά τεμάχιο: " + printProductPrice(something.getId()));
		}
	}
	
	public static Shop searchById(int x) {
		for(Shop shop : shops) {
			if (shop.id == x) {
				return shop;
			}
		}
		return null;
	}
	
	public static void printAllShops() {
		for (Shop shop : shops) {
			System.out.println("--------------------");
			System.out.println("ID: " + shop.id);
			System.out.println("Επωνυμία καταστήματος: " +  shop.name);
			System.out.println("Διεύθυνση: " + shop.address);
			System.out.println("Τηλέφωνο: " + shop.phoneNumber);
		}
	}
	
	public static void filterAndPrintShops(String x) {
		//Η μέθοδος διαχειρίζεται και την περίπτωση που ο χρήστης πατήσει enter key
		String up = x.toUpperCase();
		String low = x.toLowerCase();
		boolean flag = false;
		for (Shop shop : shops) {
			if (shop.name.toUpperCase().contains(up) || shop.name.toLowerCase().contains(low)) {
				flag = true;
				System.out.println("--------------------");
				System.out.println("ID: " + shop.id);
				System.out.println("Επωνυμία καταστήματος: " +  shop.name);
				System.out.println("Διεύθυνση: " + shop.address);
				System.out.println("Τηλέφωνο: " + shop.phoneNumber);
			} else if ( x.equals(" ")) {
				printAllShops();
			}
		}
		if (flag == false) {
			System.out.println("Δε βρέθηκε κατάστημα.");
		}
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getId() {
		return id;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	//δεν τη ζητάει 
	@Override
	public String toString() {
		return "Shop [name=" + name + ", address=" + address + ", id=" + id + ", phoneNumber=" + phoneNumber
				+ ", items=" + items + "]";
	}
	
}
