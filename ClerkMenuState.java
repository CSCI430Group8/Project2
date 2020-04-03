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

	public double getAmount(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double num = Double.valueOf(item);
				System.out.println(num);
				return num;
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter the deposit");
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
 		String name = getToken("Enter member name");
  		String address = getToken("Enter address");
  		String phone = getToken("Enter phone");
		Client dummyClient;

		dummyClient = warehouse.addClient(name, phone, address);
		if (dummyClient == null) {
			System.out.println("Could not add client, try again.");
		}
	}
	
	public void listProductsWithQuantitesAndPrices() {
		System.out.println("need to add");
	}
	
	public void listClients() {
		Iterator allClients = warehouse.getClients();
		while (allClients.hasNext()){
		Client nextClient = (Client)(allClients.next());
        System.out.println(nextClient.toString());
        System.out.println();
        }
	}
	
	public void listClientsWithOutstandingBalance() {
		Iterator allClients = warehouse.getClients();
		while (allClients.hasNext()){//search for item by id
			Client nextClient = (Client)(allClients.next());
        	if(nextClient.getBalance() < 0)
        		System.out.println("\nID: " + nextClient.getId()
        							+"\nName: " + nextClient.getName()
        							+"\nBalance: $" + nextClient.getBalance());
        	
        }
	}
	


	public void displayWaitlistForProduct() {
		Iterator allBackorders = warehouse.getBackorders();
        while (allBackorders.hasNext()) {
        	Order nextBackorder = (Order)(allBackorders.next());
			System.out.println(nextBackorder.toString());
		}
	}
	
	public void receiveShipment() {
		System.out.println("Method in progress.");
        Boolean verification = true;
        int productQuantity;
        Iterator currentStock = warehouse.getProducts(),
        		currentBackorder = warehouse.getBackorders(),
        		nextOrder;
        Product nextProduct;
        ShoppingCartItem nextBackorderProduct;
        Order nextBackorder;
        
        /*Catalog all items from shipment, add to stock*/
        while(verification) {//while there are still products in shipment
			String productId = getToken("Input product ID: ");
			productQuantity = getNumber("Input product quantity: ");

        	
        	
        	/*Add shipment's inventory to stock*/
        	while(currentStock.hasNext()) {
        		nextProduct = (Product)currentStock.next();
        		if(nextProduct.getId().contentEquals(productId)) {
        			warehouse.addProductQuantity(productId, productQuantity);
        		}//end if
        	}//end while
        	
        	if(getToken("Would you like to add another product from the shipment? (y/n)") != "y") {
                verification = false;
            }//end if
        	
        }//end while
        
        /*Try to fill backorders from newly updated stock*/
        while(currentBackorder.hasNext()) {
        	nextBackorder = (Order)currentBackorder.next();//get next backorder
        	nextOrder = nextBackorder.getProducts();
        	System.out.println("Should this backorder be filled? (y/n)");
        	System.out.println(nextBackorder);
        	/*If current backorder is to be filled*/
        	if(getToken("Should this backorder be filled? (y/n)") == "y") {//current backorder should be filled
        		while(nextOrder.hasNext()) {//iterate through current backorder
        			nextBackorderProduct = (ShoppingCartItem)nextOrder.next();
        			String backorderId = nextBackorderProduct.getItem().getId();
        			int backorderQuantity = nextBackorderProduct.getQuantity();
        			if(backorderQuantity > warehouse.getProductQuantity(backorderId)) {//backOrder quantity > inventory
        				int remainder = nextBackorderProduct.getQuantity() - warehouse.getProductQuantity(backorderId);
        				warehouse.addProductBackorderQuantity(backorderId, -(backorderQuantity - remainder));
        				warehouse.setProductQuantity(backorderId, 0);//stock removed from inventory
        				nextBackorderProduct.setQuantity(remainder);
        			} else {//backOrder quantity <= inventory stock
        				warehouse.addProductQuantity(backorderId, -backorderQuantity);//subtract inventory by backorder quantity
        				warehouse.addProductBackorderQuantity(backorderId, -backorderQuantity);
        				nextOrder.remove();
        			}//end if
        			
        		}//end while
        	}//end if
        	
        	/*if backorder has been completely fulfilled delete from list*/
        	nextOrder = nextBackorder.getProducts();
        	if(!nextOrder.hasNext()){//remove backorders with no product contents
        	 currentBackorder.remove();
        	}//end if
        	
        }
	}
	
	public void recordClientPayment() {
    	String id = getToken("\nInput Client ID: ");
    	boolean entryFound = false,
				result = false,
    			inputVerification = false;
		String input;
		double inputCredit = 0.0;
		double clientBalance = 0.0;

		while (!inputVerification) {
			inputCredit = Math.round(getAmount("\nInput Amount to Deposit: ") * 100.0) / 100.0;
            input = getToken("\nDid you want to deposit $" + inputCredit + " into account with client ID: " + id + " (Y/N)");
            

            
			if (input == ("y") || input == ("Y"))// we have broken the verification cycle
                inputVerification = true;

        }//end while

        /*find client's current balance*/
        Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!entryFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(id)) {
                entryFound = true;
                clientBalance = nextClient.getBalance();
            }
        }


        if(entryFound){
            if(warehouse.setClientBalance(id, clientBalance + inputCredit))
                System.out.println("Balance has been added to account ID: " + id);
        } else {
            System.out.println("ID " + id + " not found. Balance has not been deposited.");
        }

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
