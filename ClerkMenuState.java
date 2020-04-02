import java.util.*;
import java.text.*;
import java.io.*;
public class ClerkMenuState extends WarehouseState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private Context context;
	private static ClerkMenuState instance;
	private static final int EXIT = 0;
	private static final int ADD_CLIENT = 1;
	private static final int LIST_PRODUCTS_WITH_QUANTITIES_AND_PRICES = 2;
	private static final int LIST_CLIENTS = 3;
	private static final int LIST_CLIENTS_WITH_OUTSTANDING_BALANCE = 4;
	private static final int CLIENTMENU = 5;
	private static final int DISPLAY_WAITLIST_FOR_PRODUCT = 6;
	private static final int RECEIVE_SHIPMENT = 7;
	private static final int RECORD_CLIENT_PAYMENT = 8;
	private static final int HELP = 9;
	private ClerkMenuState() {
		super();
		warehouse = Warehouse.instance();
		//context = Context.instance();
	}
  
	public static ClerkMenuState instance() {
		if (instance == null) {
		instance = new ClerkMenuState();
		}
		return instance;
	}

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}
	
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}
	
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer num = Integer.valueOf(item);
				return num.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}
	
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(df.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}
	
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}
	
	public void addClient() {
		System.out.println("need to add");
	}
	
	public void listProductsWithQuantitesAndPrices() {
		System.out.println("need to add");
	}
	
	public void listClients() {
		System.out.println("need to add");
	}
	
	public void listClientsWithOutstandingBalance() {
		System.out.println("need to add");
	}
	
	public void displayWaitlistForProduct() {
		System.out.println("need to add");
	}
	
	public void receiveShipment() {
		System.out.println("need to add");
	}
	
	public void recordClientPayment() {
		System.out.println("need to add");
	}

	public void help() {
		System.out.println("Enter a number between 0 and 9 as explained below:");
		System.out.println(EXIT + " to Exit");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(LIST_PRODUCTS_WITH_QUANTITIES_AND_PRICES + " to show list of products with quantities and sale prices");
		System.out.println(LIST_CLIENTS + " to show list of clients");
		System.out.println(LIST_CLIENTS_WITH_OUTSTANDING_BALANCE + " to show list of clients with outstanding balance");
		System.out.println(CLIENTMENU + " to switch to the client menu");
		System.out.println(DISPLAY_WAITLIST_FOR_PRODUCT + " to display the waitlist for a product");
		System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
		System.out.println(RECORD_CLIENT_PAYMENT + " to record a payment from a cleint");
		System.out.println(HELP + " for help");
	}

	public void clientmenu()
	{
		boolean clientFound = false;
		String clientId = getToken("Please input the user id: ");
		Iterator allClients = Warehouse.instance().getClients();
        Client nextClient;
        while(!clientFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(clientId)) {
				clientFound = true;
				(Context.instance()).setUser(clientId);      
				(Context.instance()).changeState(2);
			}
		}
		if(!clientFound) 
			System.out.println("Invalid client id.");
	}

	public void logout()
	{
		if ((Context.instance()).getLogin() == Context.IsManager)
		{
			(Context.instance()).changeState(0); // exit with a code 0
        }
		else if (Context.instance().getLogin() == Context.IsClerk)
		{  
			(Context.instance()).changeState(3); // exit with a code 3
		}
		else 
		(Context.instance()).changeState(1); // exit code 1, indicates error
	}
 

	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case ADD_CLIENT:        							addClient();
																	break;
				case LIST_PRODUCTS_WITH_QUANTITIES_AND_PRICES:      listProductsWithQuantitesAndPrices();
																	break;
				case LIST_CLIENTS:      							listClients();
																	break;
				case LIST_CLIENTS_WITH_OUTSTANDING_BALANCE:      	listClientsWithOutstandingBalance();
																	break;
				case CLIENTMENU:      								clientmenu();
																	break;
				case DISPLAY_WAITLIST_FOR_PRODUCT:          		displayWaitlistForProduct();
																	break;
				case RECEIVE_SHIPMENT:          					receiveShipment();
																	break;
				case RECORD_CLIENT_PAYMENT:          				recordClientPayment();
																	break;
				case HELP:              							help();
																	break;
			}
		}
		logout();
	}
	
	public void run() {
		process();
	}
}
