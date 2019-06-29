package orderFood;

import java.util.*;

public abstract class Customer {
	private String name;
	private long phone;
	private String address;
	private String surname;
	protected static List<Customer> customerList= new ArrayList<Customer>();

	public Customer(String name, String surname,long phone, String address) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.surname=surname;
		
		customerList.add(this);
	}

    public Customer() {
		
	}
	
    public void addCustomer() {
		//χρησιμοποιείται από τη μέθοδο newVisitorCustomer() της υποκλάσης VisitorCustomer.
		customerList.add(this);
	}
	
	public abstract void getMenu();
	
	public Shop getShopsId() {
		//Χρησιμοποιείται από τις getMenu των υποκλάσεων όταν ο πελάτης θέλει να κάνει νέα παραγγελία.
		//Η μέθοδος τον καθοδηγεί να βάλει το id του καταστήματος που επιθυμεί (έγκυρο id) και επιστρέφει το αντικείμο του καταστήματος με το συγκεκριμένο id.
		//Αν ο χρήστης πατήσει enter key επιστρέφει στο Μενού Εγγεγραμμένου Πελάτη ή Επισκέπτη Πελάτη.

		Scanner input=new Scanner(System.in);
		Shop shopWithFoundId;
		do {
			String id="0";
			int idnumber=0;
			do {
				System.out.println("Εισάγετε το id του καταστήματος: ");
			try {
			    id = input.nextLine();
			    if(id.equals("")) {
			    	System.out.println("Πατήσατε enter key.Επιστροφή στο μενού. ");
			    	return null;	
			    }
			    idnumber=Integer.parseInt(id);
			    break;
				}
				catch(NumberFormatException in) {
					System.err.flush();
					System.out.flush();
					System.err.print("Μη έγκυρη επιλογή.Προσπαθήστε ξανά.");
					System.out.println();
					
				}
			
			}while(true);
			shopWithFoundId= Shop.searchById(idnumber);
			System.out.println((shopWithFoundId !=null) ? " " : "Δε βρέθηκε κατάστημα. Δοκιμάστε ξανά. ");
		} while (!(shopWithFoundId !=null));
		shopWithFoundId.printCatalogue();
			return shopWithFoundId;
		}
	
	public ArrayList <Integer[][]> getidQuat(Shop shopWithFoundId) {
		//Χρησιμοποιείται από τις getMenu των υποκλάσεων όταν ο πελάτης θέλει να κάνει νέα παραγγελία, αμέσως μετά την μέθοδο getShopsId().
		//Ζητείται από το χρήστη να εισάγει το id του προϊόντος που επιθυμεί να παραγγείλει (υπεύθυνη για την είσοδο του id είναι η getId, η οποία καλείται απο την μέθοδο)
		//και μετά την ποσότητα(υπεύθυνη για την εισοδο της ποσότητας ειναι η getQuat, η οποία καλείται απο την μέθοδο).
		//Ο χρήστης μπορεί να εισάγει προϊόντα και ποσότητες επαναληπτικά, μέχρι να πατήσει enter key στην εισαγωγή id προϊόντος.
		//Επιστρέφει την λίστα με τα προϊόντα και τις ποσότητες που θέλει ο χρήστης.
		ArrayList <Integer[][]> productIdQuantity = new ArrayList <Integer[][]>();
		Scanner input=new Scanner(System.in);
		
		do {
			int productId=getId(shopWithFoundId);
		//Αν productId=-1 σημαίνει ότι ο χρήστης πάτησε enter key την εισαγωγή id.
			if(productId==-1) {
				break;
			}
				int position=Customer.foundId(productId,productIdQuantity);
				int quat=getQuat(productId,productIdQuantity,position);
				//Αν quat==-1 σημαίνει ότι ο χρήστης έχει ήδη παραγγείλει 20 κομματια από το συγκεκριμένο προίον, σε προηγούμενη εισαγωγή id-ποσοτήτων.
				if(quat!=-1) {
				Integer[][] temp = {{productId,quat }};
				
				if(position==-1) {
					productIdQuantity.add(temp);
				}
				else {
					productIdQuantity.remove(position);
					productIdQuantity.add(position,temp);
				}
				}
		
		} while (true);
		return productIdQuantity;
	}
	
	public int getId(Shop shopWithFoundId) {
		//Χρησιμοποιέιται από την getidQuat.
		//Ζητείται από το χρήστη να εισάγει το id του προϊόντος που επιθυμεί να παραγγείλει και το επιστρέφει(ελέγχει για την εγκυρότητα του id).
		//Αν ο χρήστης πατήσει enter key, τότε η μέθοδος επιστρέφει -1.
		Scanner input=new Scanner(System.in);
		do {	
		System.out.println("Εισάγετε το id του επιθυμητού προϊόντος: ");
			
			String tempId;
			int productId=0;
			try {
			tempId= input.nextLine();
			if (tempId.equals("")) {
				return -1;
			}
			productId= Integer.parseInt(tempId); 
			if (shopWithFoundId.productIdExists(productId)) {
				return productId;
			}
			else {
				System.out.println("Το συγκεκριμένο id δεν αντιστοιχεί σε κάποιο προϊόν. Δοκιμάστε ξανά.");
			}
			}
			catch (NumberFormatException e) {
				
				System.err.flush();
				System.out.flush();
				
				System.err.print("Μη έγκυρη επιλογή.Προσπαθήστε ξανά.");
				
				System.out.println();
			}
		}while(true);
	}
	
	public int getQuat(int productId,ArrayList<Integer[][]>productIdQuantity,int position) {
		//Χρησιμοποιείται από την getidQuat.
		//Είναι υπεύθυνη για την εισαγωγή της ποσότητας που επιθυμεί ο χρήστης από το συγκεκριμένο προίόν.
		//Αν ο χρήστης έχει ήδη παραγγείλει 20 κομματια από το συγκεκριμένο προίον επιστρέφει -1, 
		//ώστε η getidQuat να το διάχειριστεί κατάλληλα.
		Scanner input=new Scanner(System.in);
		int productQuantity;
		int ceillimit=1;
		int floorlimit=20;
		if(position==-1) {	
		System.out.println("Εισάγετε την επιθυμητή ποσότητα προϊόντος [1-20]: ");
		ceillimit=20;
		floorlimit=1;
		}
		else {
			System.out.printf("Προσοχή το συγκεκριμένο προϊόν το έχετε ήδη στο καλάθι σας σε %d κομμάτια.\n",productIdQuantity.get(position)[0][1] );
			System.out.println("Ο κανονισμός του καταστήματος επιτρέπει το κάθε προϊόν να έρχεται στο σπίτι σας σε ποσότητα ως 20 κομμάτια ανά παραγγελία.");
			if(productIdQuantity.get(position)[0][1]==20) {
			System.out.printf("Από το συγκεκριμένο προϊον δεν μπορείτε να παραγγείλετε κανένα επιπλέον κομμάτι.\n");
			System.out.println("Δοκιμάστε ξανά με διαφορετικό id.");
			return -1;
			}
			else {
			System.out.printf("Από το συγκεκριμένο προϊον μπορείτε να παραγγείλετε ακόμα μέχρι και %d κομμάτια.\n",20-productIdQuantity.get(position)[0][1] );
			ceillimit=20-productIdQuantity.get(position)[0][1];
			floorlimit=0;//καθώς έχει ήδη παραγγείλει το προϊον και η ποσότητα πρέπει να βρίσκεται στο διάδτημα [1-20] μπορεί να δώσει ποσότητα 0. 
			System.out.printf("Εισάγετε την επιθυμητή ποσότητα προϊόντος [%d-%d]: \n",floorlimit,ceillimit);
			}
		}
	
		do {
			productQuantity=0;
			try {
				productQuantity = input.nextInt();
			}
			catch(InputMismatchException in) {
				input.nextLine();
				System.err.flush();
				System.out.flush();
				System.err.print("Μη έγκυρη επιλογή.Προσπαθήστε ξανά.");
				System.out.println();
				productQuantity=-1;
			}
			if ((productQuantity < floorlimit) || (productQuantity > ceillimit)) {
				System.out.printf("H ποσότητα πρέπει να είναι στο διάστημα [%d-%d]. Δοκιμάστε ξανά.\n",floorlimit,ceillimit);
			}
		
		}while ((productQuantity < floorlimit) || (productQuantity > ceillimit));
		 return (position==-1 ? productQuantity : productQuantity + productIdQuantity.get(position)[0][1]);
	
	}

	public static int foundId(int idp,ArrayList<Integer[][]> idsQuantities) {
		//Η μέθοδος αυτή έχει ορίσματα ένα ιδ προϊόντος και την υπό δημιουργία λίστα id-ποσοτήτων
		//Η μέθοδος αυτή χρησιμοποιείται από την getidQuat (καλείται από τις getMenu) για να γίνει έλεγχος αν έχει ξαναδωθεί 
		//το συγκεκριμένο id και επιστρέφει σε ποια θέση της λίστας του ArrayList βρίσκεται ο 
		//δισδιάστατος πίνακας που περιέχει το id (Αν δεν υπάρχει επιστρέφει -1).

		for (int i=0; i<idsQuantities.size() ; i++) {
			if( idp==idsQuantities.get(i)[0][0]) {
				return i;
			}
		}
		return -1;//όταν το δοθέν id εν υπάρχει ήδη στην λίστα.
		
	}
	
	public void setPhone(long phone) {
	this.phone = phone;
}

public String getSurname() {
		return surname;
	}

public void setAddress(String address) {
	this.address = address;
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}
