package orderFood;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Order {
	
	private Customer customer;
	private Shop shop;
	private String datetime;

		 
	private ArrayList <Integer[][]> idsQuantities = new ArrayList <Integer[][]>();
    private static ArrayList<Order> orders=new ArrayList<Order>();
	
	
    public Customer getCustomer() {
		return customer;
	}

	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	@Override
	public String toString() {
		//Στην μέθοδο αυτή μετατρέπουμε το ArrayList που δεχόμαστε ως όρισμα σε δυσδιάστατο.
		//Αυτό το κάναμε καθώς είναι πιο εύκολη η εκτέλεση της μεθόδου και
		//επειδή ο πίνακας είναι τοπική μεταβλητή, μετά τέλος της μεθόδου απομακρύνεται από την μνήμη. 
		//Έτσι δεν μας δεσμεύει τη μνήμη, παρά μόνο την στιγμή της εκτέλεσης της μεθόδου.
		String[][] str=new String [idsQuantities.size()][2];
	
		for (int i=0; i<idsQuantities.size() ; i++) {
			str[i][0] = shop.printProductName(idsQuantities.get(i)[0][0]);;
			str [i][1] = String.valueOf(idsQuantities.get(i)[0][1]);;
			}
		
		return "πελάτης: " + customer.getName() +" "+customer.getSurname()+
				"\nκατάστημα: "+ shop.getName() + " "+ 
		" \n[προϊόν,ποσότητα]: " + Arrays.deepToString(str) +" "+
		"\nημερομηνία και ώρα παραγγελίας:" + datetime +"\nσυνολικό κόστος:"+ getTotal() +"\n";
	}
		
	public double getTotal() {
		//επιστρέφει το συνολικό κόστος της παραγγελίας. 
		double total=0;
		for(int i=0;i<idsQuantities.size();i++) {
			total+=idsQuantities.get(i)[0][1]*shop.printProductPrice(idsQuantities.get(i)[0][0]);
		}
		return total;
		}
	
	public static double getTotal(ArrayList <Integer[][]> idsQuantities,Shop shop) {
		//επιστρέφει το συνολικό κόστος της παραγγελίας σε εξέλιξη, δεν έχει ολοκληρωθεί. 
		double total=0;
		for(int i=0;i<idsQuantities.size();i++) {
			total+=idsQuantities.get(i)[0][1]*shop.printProductPrice(idsQuantities.get(i)[0][0]);
		}
		return total;
		}
	
	public static void printOrder(Customer c) {
		System.out.println("Το ιστορικό παραγγελιών σας:");
		int x=1;
		int counter=0;
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i)!=null) {
				if(c.equals(orders.get(i).getCustomer())){
				System.out.printf("%d. \n%s ", x++ ,orders.get(i).toString());	
				counter++;
				}
			}
		}
		if(counter==0) {
			System.out.println("Δεν έχετε πραγματοποιήσει προηγούμενες παραγγελίες "
					+ "μέσω της εφαρμογής μας.");
		}
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public ArrayList<Integer[][]> getIdsQuantities() {
		return idsQuantities;
	}

	public void setIdsQuantities(ArrayList<Integer[][]> idsQuantities) {
		this.idsQuantities = idsQuantities;
	}

	public static ArrayList<Order> getOrders() {
		return orders;
	}

	public static void setOrders(ArrayList<Order> orders) {
		Order.orders = orders;
	}

	public Order(Customer customer , Shop shop ,ArrayList <Integer[][]> idsQuantities) {
		this.customer=customer;
		this.shop=shop;
		this.idsQuantities=idsQuantities;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		datetime = dateFormat.format(date);
		orders.add(this);
		
	}

}
