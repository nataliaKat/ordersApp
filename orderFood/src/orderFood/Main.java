package orderFood;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {

	public static void main(String[] args) {
		loadObjects();
		Scanner input = new Scanner(System.in);
		
		for( ; ; ) {	
			int choice;
			for(;;) {
			try {
			System.out.println("\t***Μενού Σύνδεσης***");
			System.out.println("1. Εγγραφή");
			System.out.println("2. Login ως Εγγεγραμμένος Πελάτης");
			System.out.println("3. Συνέχεια ως Επισκέπτης Πελάτης");
			System.out.println("0. Έξοδος από την εφαρμογή");
		    System.out.println("Εισάγετε τον αντίστοιχο αριθμό για την επιλογή σας: ");
				choice = input.nextInt();
				break;
			} 
			catch (InputMismatchException inputMismatchException) {
				input.nextLine();
				System.err.println("Λανθασμένη είσοδος. Προσπαθήστε ξανά.");
			}
			}
			
			switch (choice) {
				case 0 :
					return;
				case 1:
					//Εγγραφή
					//εισαγωγή ονόματος
					input.nextLine();
					String name;
					do {
					System.out.println("Εισάγετε το όνομά σας: ");
					name = input.nextLine();
					if(!isStringOnlyAlphabet(name)) {
						System.out.println("Μη έγκυρη εισαγωγή ονόματος. Δοκιμάστε ξανά.");
					}
					else
						break;
					}while(true);
					String surname;
					//εισαγωγή επιθέτου
					do {
					System.out.println("Εισάγετε το επίθετό σας: ");
					surname = input.nextLine();
					if(!isStringOnlyAlphabet(surname)) {
						System.out.println("Μη έγκυρη εισαγωγή επιθέτου. Δοκιμάστε ξανά.");
					}
					else
						break;
					}while(true);
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
					input.nextLine();
					//εισαγωγή διεύθυνσης
					System.out.println("Εισάγετε τη διεύθυνσή σας: ");
					String address = input.nextLine();
					//εισαγωγή email
					String email;
					do {
						System.out.println("Εισάγετε τη διεύθυνση email σας: ");
						email = input.nextLine();
						if(!isValidEmailAddress(email)) {
							System.out.println("Μη έγκυρη εισαγωγή email. Δοκιμάστε ξανά.");
						}
						else
							break;
						}while(true);
					//εισαγωγή κωδικού πρόσβασης
					String password;
					do {
				    System.out.println("Δημιουργήστε κωδικό πρόσβασης (ελάχιστος αριθμός χαρακτήρων: 6): ");
					password = input.nextLine();
					if (password.length()>=6)
						break;
					else
						System.out.println("Μη έγκυρος κωδικός πρόσβασης .Προσπαθήστε ξανά.");
					}while(true);
					//καταχώρηση του πελάτη στη βάση δεδομένων της εφαρμογής
					new RegisteredCustomer(name, surname, phone, address, email, password);
					System.out.println("Είστε και επίσημα εγγεγραμμένος πελάτης της "
							+ "εφαρμογής. Σας ευχαριστούμε!");
					break;
					
				case 2:
					//Login ως Εγγεγραμμένος Πελάτης
					RegisteredCustomer c1 = null; 
					input.nextLine();
					while (c1 == null) {
						System.out.println("Εισάγετε το email σας: ");
						email = input.nextLine();
						if (email.equals("")) {
							break;
						}
						System.out.println("Εισάγετε τον κωδικό πρόσβασής σας: ");
						password = input.nextLine();
						c1 = RegisteredCustomer.LogIn(email, password);
						// η LogIn τυπώνει μήνυμα λάθους άμα ο συνδυασμός email-password δεν υπάρχει στην βάση δεδομένων
					
					}  
					if (c1 != null) {	
						System.out.println("Καλωσήρθατε " + c1.getName() +" "+c1.getSurname());
						c1.getMenu();
					}
					break;
				case 3:
					//Συνέχεια ως Επισκέπτης Πελάτης
					for(;;)	{
						if (VisitorCustomer.exit == true) {
							VisitorCustomer.exit = false;
							break;
						}
						(new VisitorCustomer()).getMenu(); /*δημιουργία καινούριου αντικειμένου κάθε φορά για την κλήση της μεθόδου getMenu()
			                                            προκειμένου να μην εισάγονται τα στοιχεία του νέου πελάτη στις ίδιες θέσεις με αυτά
			                                           του προηγούμενου*/
						
					}
					break;
			    default:
			    	
			    	System.err.flush();
					System.out.flush();
					System.err.print("Μή έγκυρη επιλογή.Προσπαθήστε ξανά.");
					System.out.println();
					break;
			}
		}	
	}
	public static boolean isStringOnlyAlphabet(String str) 
	{ //Έλεγχος αν το String περιέχει μόνο γράμματα.
		//Δέχεται και τόνους.
		 return ((!str.equals("")) 
		            && (str != null) 
		            && (str.chars().allMatch(Character::isLetter)));
	} 
	
	 public static boolean isValidEmailAddress(String email) 
	    { //Έλεγχος αν το String είναι μία έγκυρη διεύθυνση email.
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    } 
	
	public static void loadObjects() {
		Product souvlaki = new Product("σουβλάκι", 2.8);
		Product pizza = new Product("πίτσα", 9.9);
		Product iceCream = new Product("παγωτό", 3.2);
		Product frappeForTrop = new Product("φραππέ", 1.2);
		Product pizzaForTrop = new Product("πίτσα", 10);
		
		ArrayList<Product> souvl = new ArrayList<Product>();
		souvl.add(souvlaki);
		souvl.add(pizza);
		souvl.add(iceCream);
		
		Shop souvlistas = new Shop("Σουβλίστας", "Δημοφώντος", 2343555124l, souvl);
		ArrayList<Product> trop = new ArrayList<Product>(); 
		trop.add(pizzaForTrop);
		trop.add(frappeForTrop);
		Shop tropical = new Shop("Tropical", "Τριών Ιεραρχών", 6565365532l, trop);
		// Shop: Waffle Shop
		ArrayList<Product> waffleShopProds= new ArrayList<Product>();
		waffleShopProds.add(new Product(" Ice Cream", 5.25));
		waffleShopProds.add(new Product("Waffle with ice cream", 7.25));
		new Shop("Coffee Shop", "Dimokratias Avenue 26", 2104142697l, waffleShopProds);
		// Shop Coffee Lab
		ArrayList<Product> coffeeLabProds= new ArrayList<Product>();
		coffeeLabProds.add(new Product("Greek Coffee", 1.20));	
		coffeeLabProds.add(new Product("Espresso Coffee", 1.50));
		new Shop("Coffee Lab", "Kifissias Str", 2109206932l, coffeeLabProds);
	}
	
}
