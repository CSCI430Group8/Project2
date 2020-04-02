import java.util.*;
import java.text.*;
import java.io.*;
public class ClientMenuState extends WarehouseState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private Context context;
	private static ClientMenuState instance;
	private static final int EXIT = 0;
	private static final int CLIENT_DETAILS = 1;
	private static final int LIST_PRODUCTS_WITH_SALE_PRICES = 2;
	private static final int LIST_CLIENT_TRANSACTIONS = 3;
	private static final int EDIT_CLIENT_CART = 4;
	private static final int ADD_TO_CLIENT_CART = 5;
	private static final int DISPLAY_CLIENT_WAITLIST = 6;
	private static final int HELP = 7;
	private ClientMenuState() {
		super();
		warehouse = Warehouse.instance();
		//context = Context.instance();
	}
  
	public static ClientMenuState instance() {
		if (instance == null) {
			instance = new ClientMenuState();
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
	
	public void clientDetails() {
		Iterator clients = warehouse.getClients();
		boolean clientFound = false;
		while (!clientFound && clients.hasNext()) {
			Client nextClient = (Client)clients.next();
			if (nextClient.getId().contentEquals(context.getUser())) {
				clientFound = true;
				System.out.println(nextClient);
			}
		}
	}
	
	public void listProductsWithSalePrices() {
		System.out.println("need to add");
	}
	
	public void listClientTransactions() {
		Iterator orders = warehouse.getOrders();
		while (orders.hasNext()) {
			Order nextOrder = (Order) orders.next();
			if (nextOrder.getId().contentEquals(context.getUser())) {
				System.out.println(nextOrder);
			}
		}
	}
	
	public void editClientCart() {
		System.out.println("need to add");
	}
	
	public void addToClientCart() {
		System.out.println("need to add");
	}
	
	public void displayClientWaitlist() {
		Iterator orders = warehouse.getBackorders();
		while (orders.hasNext()) {
			Order nextBackOrder = (Order) orders.next();
			if (nextBackOrder.getId().contentEquals(context.getUser())) {
				System.out.println(nextBackOrder);
			}
		}
	}
	}

	public void help() {
		System.out.println("Enter a number between 0 and 7 as explained below:");
		System.out.println(EXIT + " to Exit");
		System.out.println(CLIENT_DETAILS + " to show client details");
		System.out.println(LIST_PRODUCTS_WITH_SALE_PRICES + " to show list of products with sale prices");
		System.out.println(LIST_CLIENT_TRANSACTIONS + " to show client transactions");
		System.out.println(EDIT_CLIENT_CART + " to edit client's shopping cart");
		System.out.println(ADD_TO_CLIENT_CART + " to add to client's shopping cart");
		System.out.println(DISPLAY_CLIENT_WAITLIST + " to display client's shopping cart");
		System.out.println(HELP + " for help");
	}

	public void logout()
	{
		if ((Context.instance()).getLogin() == Context.IsManager || (Context.instance()).getLogin() == Context.IsClerk)
		{
			(Context.instance()).changeState(1); // exit with a code 1
        }
		else if (Context.instance().getLogin() == Context.IsClient)
		{  
			(Context.instance()).changeState(3); // exit with a code 3
		}
		else 
		(Context.instance()).changeState(2); // exit code 2, indicates error
	}
 

	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case CLIENT_DETAILS:        			clientDetails();
														break;
				case LIST_PRODUCTS_WITH_SALE_PRICES:    listProductsWithSalePrices();
														break;
				case LIST_CLIENT_TRANSACTIONS:      	listClientTransactions();
														break;
				case EDIT_CLIENT_CART:      			editClientCart();
														break;
				case ADD_TO_CLIENT_CART:      			addToClientCart();
														break;
				case DISPLAY_CLIENT_WAITLIST:          	displayClientWaitlist();
														break;
				case HELP:              				help();
														break;
			}
		}
		logout();
	}
	
	public void run() {
		process();
	}
}
