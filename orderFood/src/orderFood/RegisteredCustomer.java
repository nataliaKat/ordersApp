package orderFood;


import java.util.*;

public class RegisteredCustomer extends Customer {
	private String email;
	private String psswd;
	private int id;
	private static int counter;

	public RegisteredCustomer() {
		super();
	}

	public RegisteredCustomer(String name, String surname, long phone, String address, String email, String psswd) {
		super(name, surname, phone, address);
		this.email = email;
		this.psswd = psswd;
		this.id = ++counter;
	}

	public static RegisteredCustomer LogIn(String givenEmail, String givenPassword) {
		for (Customer obj : customerList) {
			if (obj instanceof RegisteredCustomer) {
				RegisteredCustomer r = (RegisteredCustomer) obj;
				if (r.email.equals(givenEmail)) {
					if (r.psswd.contentEquals(givenPassword)) {
						return r;
					}
					else
						System.out.println("");
				}
			
		}
		}
		System.out.println("O δοθής συνδυασμός email και password δεν αντιστοιχεί με "
				+ "κανέναν από την βάση δεδομένων μας.Προσπαθήστε ξανά.");
		return null;
		}
		

	@Override
	public void getMenu() {
		int choice;
		Scanner input = new Scanner(System.in);
		do {
			while (true) {
				System.out.println("\t*** Μενού Εγγεγραμμένου Πελάτη  ***");
				System.out.println("Εισάγετε τον αντίστοιχο αριθμό");
				System.out.println("1. Εμφάνιση προηγούμενων παραγγελιών");
				System.out.println("2. Εμφάνιση διαθέσιμων Καταστημάτων ");
				System.out.println("3. Προβολή Καταλόγου και Παραγγελία από κατάστημα");
				System.out.println("0. Επιστροφή στο μενού σύνδεσης");

				try {
					choice = input.nextInt();
					break;
				} catch (InputMismatchException in) {
					input.nextLine();

					System.err.flush();

					System.out.flush();
					System.err.print("Μή έγκυρη επιλογή.Προσπαθήστε ξανά.");
					System.out.println();
				}
			}
			input.nextLine();
			switch (choice) {
			case 0:
				return;
			case 1:
				//Εμφάνιση προηγούμενων παραγγελιών
				Order.printOrder(this);
				break;
			case 2:
				//Εμφάνιση διαθέσιμων Καταστημάτων 
				System.out.println("Εισάγετε όνομα ή τμήμα ονόματος καταστήματος προς αναζήτηση: ");
				String x = input.nextLine();
				Shop.filterAndPrintShops(x);//Η μέθοδος διαχειρίζεται και την περίπτωση που ο χρήστης πατήσει enter key
				break;
			case 3:
				//Προβολή Καταλόγου και Παραγγελία από κατάστημα
				ArrayList<Integer[][]> productIdQuantity = new ArrayList<Integer[][]>();
				Shop shopWithFoundId = getShopsId();//Μέθοδος κλάσης Customer
				if(shopWithFoundId==null) {
					break;
				}
				productIdQuantity.addAll(getidQuat(shopWithFoundId));//Μέθοδος κλάσης Customer
				
				String[][] str = new String[productIdQuantity.size()][2];
				//Στην μέθοδο αυτή μετατρέπουμε το ArrayList productIdQuantity σε δυσδιάστατο.
				//Αυτό το κάναμε καθώς είναι πιο εύκολη η εκτέλεση της μεθόδου.
				//Όταν δεν τον χρειαζόμαστε πια τον κάνουμε null, ώστε να μην πιάνει χώρο στην μνήμη.
				for (int i = 0; i < productIdQuantity.size(); i++) {
					str[i][0] = shopWithFoundId.printProductName(productIdQuantity.get(i)[0][0]);
					str[i][1] = String.valueOf(productIdQuantity.get(i)[0][1]);
				}
				System.out.println("[προϊόν,ποσότητα]: " + Arrays.deepToString(str));
				str=null;
				System.out.println("Θέλετε να συνεχίσετε με την υποβολή της παραγγελίας;(Ν/Ο)");
				String ans = input.next();
				if (ans.toLowerCase().charAt(0) == 'ν' || ans.toLowerCase().charAt(0) == 'n') {
					new Order(this, shopWithFoundId, productIdQuantity);
					System.out.println("Η παραγγελία σας μόλις ολοκληρώθηκε με επιτυχία."
							+ "Σας ευχαριστούμε για την προτίμησή σας");
				}
				break;
			default:
				System.err.flush();
				System.out.flush();
				System.err.print("Μη έγκυρη επιλογή.Προσπαθήστε ξανά.");
				System.out.println();
				break;
			}
		} while (true);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsswd() {
		return psswd;
	}

	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}

}
