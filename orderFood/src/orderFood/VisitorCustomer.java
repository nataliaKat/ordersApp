package orderFood;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner ;

public class VisitorCustomer extends Customer{

	
	public static boolean exit = false;
	//Δεν υπάρχει κονστράκτορας. Χρησιμοποιείται ο default.
	
	public void getMenu() {
		Scanner input = new Scanner(System.in);
		int choice;

		while (true) {

			System.out.println("\t*** Μενού Επισκέπτη Πελάτη ***");
			System.out.println("Εισάγετε τον αντίστοιχο αριθμό");
			System.out.println("1. Εμφάνιση διαθέσιμων Καταστημάτων ");
			System.out.println("2. Αναζήτηση Καταλόγου Προϊόντων Συγκεκριμένου Καταστήματος");
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
			exit = true;
			System.out.println("Επιστροφή στο μενού σύνδεσης...");
			return;
		case 1:
			// Εμφάνιση διαθέσιμων Καταστημάτων.
			System.out.println("Παρακαλώ εισάγετε ένα ονόμα καταστήματος" + " ή ενα τμήμα ονόματος");
			String k = input.nextLine();
			Shop.filterAndPrintShops(k);// Η μέθοδος διαχειρίζεται και την περίπτωση που ο χρήστης πατήσει enter key
			break;
		case 2:
			// Αναζήτηση Καταλόγου Προϊόντων Συγκεκριμένου Καταστήματος
			ArrayList<Integer[][]> productIdQuantity = new ArrayList<Integer[][]>();
			Shop shopWithFoundId = getShopsId();// Μέθοδος κλάσης Customer
			if (shopWithFoundId == null) {
				break;
			}
			productIdQuantity.addAll(getidQuat(shopWithFoundId));// Μέθοδος κλάσης Customer
			
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
				newVisitorCustomer();
				new Order(this, shopWithFoundId, productIdQuantity);
				System.out.println(
						"Η παραγγελία σας μόλις ολοκληρώθηκε με επιτυχία." + "Σας ευχαριστούμε για την προτίμησή σας");
			}
			break;
		default:
			System.out.println("wrong");
			System.err.flush();
			System.out.flush();
			System.err.print("Μη έγκυρη επιλογή. Προσπαθήστε ξανά.");
			System.out.println();
			break;	
		
		
		}
		// η μέθοδος θα επιστρέψει στο case 3 της main, όπoυ εξαιτίας της for loop 
		//θα δημιουργηθεί ένα νέο αντικείμενο, πάνω στο οποίο θα κληθεί ξανά η getMenu()
	}
		
	public void newVisitorCustomer() {
		//Αν ο πελάτης(επισκέπτης μόνο) επιλέξει ότι θέλει να συνεχίσει με την υποβολή της παραγγελίας του,
		//η μέθοδος τον καθοδηγεί ώστε να δώσει τα απαραίτητα στοιχεία(ελέγχει αν είναι έγκυρα,
		//αλλιώς του τα ξαναζητάει) και μετά καταχωρεί τον πελάτη στην λίστα (customers, που βρίσκεται στην κλάση Customer).
		Scanner input=new Scanner(System.in);
		//εισαγωγή ονόματος.
		String name;
		do {
		System.out.println("Εισάγετε το όνομά σας: ");
		name = input.nextLine();
		if(!Main.isStringOnlyAlphabet(name)) {
			System.out.println("Μη έγκυρη εισαγωγή ονόματος. Δοκιμάστε ξανά.");
		}
		else
			break;
		}while(true);
	 	setName(name);
		//εισαγωγή επιθέτου
		String surname;
		do {
		System.out.println("Εισάγετε το επίθετό σας: ");
		surname = input.nextLine();
		if(!Main.isStringOnlyAlphabet(surname)) {
			System.out.println("Μη έγκυρη εισαγωγή επιθέτου. Δοκιμάστε ξανά.");
		}
		else
			break;
		}while(true);
		setSurname(surname);
        //εισαγωγή τηλεφώνου
		long phone = 0l;
		boolean c=true;
		do {
	    System.out.println("Εισάγετε το τηλέφωνό σας: ");
		try {
		phone = input.nextLong();
		String p = phone+"";
		if(p.length()==10) {//ελέγχει αν ο αριθμός αποτελείται από 10 ψηφία.
			c=false;
		}
		else {
		System.out.println("Μη έγκυρος αριθμός τηλεφώνου.Προσπαθήστε ξανά.");
		}
		}
		catch(InputMismatchException in) {
			input.nextLine();
			System.out.println("Μη έγκυρος αριθμός τηλεφώνου.Προσπαθήστε ξανά.");
		}
		}while(c==true);
		setPhone(phone);
		//εισαγωγή διεύθυνσης
		System.out.println("Εισάγετε τη διεύθυνσή σας: ");
		setAddress(input.next());
		super.addCustomer();
	}
}
