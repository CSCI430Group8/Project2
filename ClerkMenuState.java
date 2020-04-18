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
	
	/*
     * Function:	ClerkMenuState
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for ClerkMenuState class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				ClerkMenuState can be initialized at a time
     */
	private ClerkMenuState() {
		super();
		warehouse = Warehouse.instance();
	}
  
	/*
     * Function:	instance
     * Type:		ClerkMenuState
     * Privacy:		public
     * Description:	This is the singleton for ClerkMenuState, which, if were to
     * 				try an initialize a second ClientMenuState class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current ClerkMenuState class.
     */
	public static ClerkMenuState instance() {
		if (instance == null) {
		instance = new ClerkMenuState();
		}
		return instance;
	}

	/*
     * Function:	getToken
     * Type:		void
     * Privacy:		public
     * Description:	Gets a string from the user input.
     */
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
	
	/*
     * Function:	yesOrNo
     * Type:		void
     * Privacy:		public
     * Description:	Gets input on a yes or no question.
     */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}
	
	/*
     * Function:	getNumber
     * Type:		void
     * Privacy:		public
     * Description:	Gets an integer from input.
     */
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
	
	/*
     * Function:	getDate
     * Type:		void
     * Privacy:		public
     * Description:	Gets the current date.
     */
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
	
	/*
     * Function:	getPrice
     * Type:		void
     * Privacy:		public
     * Description:	Gets a double from input.
     */
	public double getPrice(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double num = Double.valueOf(item);
				return num;
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter the deposit");
			}
		} while (true);
	}
	
	/*
     * Function:	getCommand
     * Type:		void
     * Privacy:		public
     * Description:	Gets a command for the menu from input.
     */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command: Enter " + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}
	
	/*
     * Function:	addClient
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user to enter a client name, phone number, and address.
					Creates client from the inputs.
     */
	public void addClient() {
 		String name = getToken("\nClient Name: ");
  		String phone = getToken("\nPhone Number:");
		String address = getToken("\nAddress: ");
		Client dummyClient;

		dummyClient = warehouse.addClient(name, phone, address);
		if (dummyClient == null) {
			System.out.println("Could not add client, try again.");
		}
	}
	
	/*
     * Function:	listProductsWithQuantitesAndPrices
     * Type:		void
     * Privacy:		public
     * Description:	Lists all of the products with quantities and prices.
     */
	public void listProductsWithQuantitesAndPrices() {
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext()){
			Product nextProduct = (Product)(allProducts.next());
            System.out.println("Product ID: " + nextProduct.getId() + " Product Name: " + nextProduct.getName() + " Price Per Item: $" + nextProduct.getPrice() + " Quantity: " + nextProduct.getQuantity());
            System.out.println();
		}
	}
	
	/*
     * Function:	listClients
     * Type:		void
     * Privacy:		public
     * Description:	Lists all of the clients in the system.
     */
	public void listClients() {
		Iterator allClients = warehouse.getClients();
		while (allClients.hasNext()){
		Client nextClient = (Client)(allClients.next());
        System.out.println(nextClient.toString());
        System.out.println();
        }
	}
	
	/*
     * Function:	listClientsWithOutstandingBalance
     * Type:		void
     * Privacy:		public
     * Description:	List all clients with a balance that is less than zero.
     */
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

	/*
     * Function:	displayWaitlistForProduct
     * Type:		void
     * Privacy:		public
     * Description:	Displays all of the backorders for a product.
     */
	public void displayWaitlistForProduct() {
		Iterator allBackorders = warehouse.getBackorders();
        while (allBackorders.hasNext()) {
        	Order nextBackorder = (Order)(allBackorders.next());
			System.out.println(nextBackorder.toString());
		}
	}
	
	/*
     * Function:	receiveShipment
     * Type:		void
     * Privacy:		public
     * Description:	Receives shipment for a product on inventory.
					May choose to fill a backorder with the shipment.
     */
	public void receiveShipment() {
		System.out.println("Method in progress.");
        Boolean verification = true,
				input = false;
        int productQuantity;
        Iterator currentStock = warehouse.getProducts(),
        		currentBackorder = warehouse.getBackorders(),
        		nextOrder;
        Product nextProduct;
        ShoppingCartItem nextBackorderProduct;
        Order nextBackorder;
        
        /*Catalog all items from shipment, add to stock*/
        while(verification) {//while there are still products in shipment
			String productId = getToken("\nInput product ID: ");
			productQuantity = getNumber("\nInput product quantity: ");

        	/*Add shipment's inventory to stock*/
        	while(currentStock.hasNext()) {
        		nextProduct = (Product)currentStock.next();
        		if(nextProduct.getId().contentEquals(productId)) {
        			warehouse.addProductQuantity(productId, productQuantity);
        		}//end if
        	}//end while
        	
			input = yesOrNo("\nWould you like to add another product from the shipment?");
        	if(!input) {
                verification = false;
            }//end if
        }//end while
        
        /*Try to fill backorders from newly updated stock*/
        while(currentBackorder.hasNext()) {
        	nextBackorder = (Order)currentBackorder.next();//get next backorder
        	nextOrder = nextBackorder.getProducts();
        	System.out.println("\nShould this backorder be filled? (y/n)");
        	System.out.println(nextBackorder);
        	/*If current backorder is to be filled*/
        	if(getToken("\nShould this backorder be filled? (y/n)") == "y") {//current backorder should be filled
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
	
	/*
     * Function:	recordClientPayment
     * Type:		void
     * Privacy:		public
     * Description:	Records a payment given by a client.
     */
	public void recordClientPayment() {
    	String id = getToken("\nInput Client ID: ");
    	boolean entryFound = false,
				result = false,
    			inputVerification = false,
				input = false;
		double inputCredit = 0.0;
		double clientBalance = 0.0;

		while (!inputVerification) {
			inputCredit = Math.round(getPrice("\nInput Amount to Deposit: ") * 100.0) / 100.0;
			input = yesOrNo("\nDid you want to deposit $" + inputCredit + " into account with client ID: " + id + "?");
			if (input)// we have broken the verification cycle
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

	/*
     * Function:	help
     * Type:		void
     * Privacy:		public
     * Description:	Displays the menu from the user.
     */
	public void help() {
		System.out.println("Enter a number between 0 and 9 as explained below:");
		System.out.println(EXIT + ".) Exit");
		System.out.println(ADD_CLIENT + ".) Add a client");
		System.out.println(LIST_PRODUCTS_WITH_QUANTITIES_AND_PRICES + ".) Show list of products with quantities and sale prices");
		System.out.println(LIST_CLIENTS + ".) Show list of clients");
		System.out.println(LIST_CLIENTS_WITH_OUTSTANDING_BALANCE + ".) Show list of clients with outstanding balance");
		System.out.println(CLIENTMENU + ".) Become a client");
		System.out.println(DISPLAY_WAITLIST_FOR_PRODUCT + ".) Display the waitlist for a product");
		System.out.println(RECEIVE_SHIPMENT + ".) Receive a shipment");
		System.out.println(RECORD_CLIENT_PAYMENT + ".) Record a payment from a client");
		System.out.println(HELP + ".) Help");
	}

	/*
     * Function:	clientmenu
     * Type:		void
     * Privacy:		public
     * Description:	Switches the user to the client menu.
     */
	public void clientmenu()
	{
		boolean clientFound = false;
		String clientId = getToken("Input Client ID: ");
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

	/*
     * Function:	logout
     * Type:		void
     * Privacy:		public
     * Description:	Logs out the user.
     */
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
 
	/*
     * Function:	process
     * Type:		void
     * Privacy:		public
     * Description:	processes through the menu options.
     */
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
	
	/*
     * Function:	run
     * Type:		void
     * Privacy:		public
     * Description:	Runs the process of the menu.
     */
	public void run() {
		process();
	}
}
