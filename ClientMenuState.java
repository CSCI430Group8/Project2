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
	private static final int DISPLAY_CLIENT_CART = 6;
	private static final int DISPLAY_CLIENT_WAITLIST = 7;
	private static final int PROCESS_CLIENT_ORDER = 8;
	private static final int HELP = 9;
	
	/*
     * Function:	ClientMenuState
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for ClientMenuState class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				ClientMenuState can be initialized at a time
     */
	private ClientMenuState() {
		super();
		warehouse = Warehouse.instance();
	}
	
	/*
     * Function:	instance
     * Type:		ClientMenuState
     * Privacy:		public
     * Description:	This is the singleton for ClientMenuState, which, if were to
     * 				try an initialize a second ClientMenuState class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current ClientMenuState class.
     */
	public static ClientMenuState instance() {
		if (instance == null) {
			instance = new ClientMenuState();
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
     * Function:	clientDetails
     * Type:		void
     * Privacy:		public
     * Description:	Displays all of the details of the client.
     */
	public void clientDetails() {
		Iterator clients = warehouse.getClients();
		boolean clientFound = false;
		while (!clientFound && clients.hasNext()) {
			Client nextClient = (Client)clients.next();
			if (nextClient.getId().contentEquals(context.instance().getUser())) {
				clientFound = true;
				System.out.println(nextClient);
			}
		}
	}
	
	/*
     * Function:	listProductsWithSalePrices
     * Type:		void
     * Privacy:		public
     * Description:	Lists all of the products on inventory along with their sales prices.
     */
	public void listProductsWithSalePrices() {
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext()){
			Product nextProduct = (Product)(allProducts.next());
            System.out.println(nextProduct.toString());
            System.out.println();
        }//end while
	}
	
	/*
     * Function:	listClientTransactions
     * Type:		void
     * Privacy:		public
     * Description:	list client transactions based on date
     */
	public void listClientTransactions() {
		Iterator orders = warehouse.getOrders();
		while (orders.hasNext()) {
			Order nextOrder = (Order) orders.next();
			if (nextOrder.getId().contentEquals(context.instance().getUser())) {
				System.out.println(nextOrder);
			}
		}
	}
	
	/*
     * Function:	editClientCart
     * Type:		void
     * Privacy:		public
     * Description:	Edits items in a client's cart. There is the choice to either
     * 				change the quantity of, remove, or doing to each item in the cart.
     */
	public void editClientCart() {
		boolean entryFound = false,
				result = false;
		String quantity,
		input = String.valueOf(EXIT - 1);
		
		LinkedList<String> removalItems = new LinkedList<String>();
		
        Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!entryFound & allClients.hasNext() & Integer.parseInt(input) != EXIT){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(context.instance().getUser())) {
                entryFound = true;
				Iterator allCartItems = warehouse.getCartItems(nextClient.getId());
				while (allCartItems.hasNext() & Integer.parseInt(input) != EXIT){
					ShoppingCartItem nextCartItem = (ShoppingCartItem)(allCartItems.next());
					System.out.println("Product Name: " + nextCartItem.getItem().getName() + " Product Quantity: " + nextCartItem.getQuantity());
					
					input = getToken("\nWhat would you like to do to this item?\n" + 
							EXIT + ".) Exit\n" +
							"1" + ".) Change Quantity of Item\n" +
							"2" + ".) Remove Item From Shopping Cart\n"+
							"3" + ".) Do Nothing To The Item\n");
					
					switch(Integer.parseInt(input)){
						case EXIT:
							break;
						case 1:
							quantity = getToken("\nEnter New Item Quantity: ");
							result = warehouse.setCartItemQuant(context.instance().getUser(), nextCartItem.getItem().getId(), Integer.parseInt(quantity));
							if(result) {
								System.out.println("\nQuantity successfully changed in cart");
							}
							else {
								System.out.println("\nFailed to change quantity, try again");
							}
							break;
						case 2:
							removalItems.add(nextCartItem.getItem().getId());
							break;
						case 3:
							break;
						default:
							System.out.println("Not a valid input.\n");
							break;
					}//end switch
				}
            }
        }
		
		/* Remove Items from Cart*/
		Iterator itemsToRemove = removalItems.iterator();
		while (itemsToRemove.hasNext()){
			String nextItemToRemove = (String)(itemsToRemove.next());
			result = warehouse.removeCartItem(context.instance().getUser(), nextItemToRemove);
			if(result) {
				System.out.println("\nItem successfully removed from cart");
			}
			else {
				System.out.println("\nFailed toremove item, try again");
			}
		}
		
		System.out.println("\nAll items edited.");
	}
	
	/*
     * Function:	addToClientCart
     * Type:		void
     * Privacy:		public
     * Description:	Adds Products from inventory into a client's cart.
     * 				The client will be specified using their ID.
     */
	public void addToClientCart() {
		String clientId,
				productId,
				quantity;
				
		boolean clientFound = false,
				productFound = false,
				result = false;
		
        Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!clientFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(context.instance().getUser())) {
                clientFound = true;
				productId = getToken("\nInput the ID of the product to add to cart: ");
		
				Iterator allProducts = warehouse.getProducts();
				Product nextProduct;
				while(!productFound & allProducts.hasNext()){
					nextProduct = (Product)allProducts.next();
					if(nextProduct.getId().contentEquals(productId)) {
						productFound = true;
						quantity = getToken("\nInput the quantity of the product to add to cart: ");
						result = warehouse.addToClientCart(nextClient, nextProduct, Integer.parseInt(quantity));
						if(result) {
							System.out.println("\nItem successfully added to cart");
						}
						else {
							System.out.println("\nFailed to add item to cart, try again");
						}
					}
				}
            }
        }
	}
	
	/*
     * Function:	displayClientCart
     * Type:		void
     * Privacy:		public
     * Description:	Displays the products and assicated quantities in client's shopping cart.
     */
	public void displayClientCart() {
		boolean entryFound = false;
        Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!entryFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(context.instance().getUser())) {
                entryFound = true;
				Iterator allCartItems = warehouse.getCartItems(nextClient.getId());
				while (allCartItems.hasNext()){
					ShoppingCartItem nextCartItem = (ShoppingCartItem)(allCartItems.next());
					System.out.println("Product Name: " + nextCartItem.getItem().getName() + " Product Quantity: " + nextCartItem.getQuantity());
					System.out.println();
				}
            }
        }
	
		if(!entryFound) {
			System.out.println("\nClient not found, try again");
		}
	}
	
	/*
     * Function:	displayClientWaitlist
     * Type:		void
     * Privacy:		public
     * Description:	Displays the Backorders associated with the client.
     */ 
	public void displayClientWaitlist() {
		Iterator orders = warehouse.getBackorders();
		while (orders.hasNext()) {
			Order nextBackOrder = (Order) orders.next();
			if (nextBackOrder.getId().contentEquals(context.instance().getUser())) {
				System.out.println(nextBackOrder);
			}
		}
	}
	
	/*
     * Function:	processClientOrder
     * Type:		void
     * Privacy:		public
     * Description:	Client would like to purchase the items in their cart.
     * 				Takes the items in their cart and removes them from inventory
     * 				then charges the price of the items to their stock.
     */ 
	public void processClientOrder() {
		System.out.println("Processing clients orders.");
		boolean orderFound = false,
                backorderFound = false;
		LinkedList<ShoppingCartItem> dummyOrder = new LinkedList<ShoppingCartItem>();
		LinkedList<ShoppingCartItem> dummyBackorder = new LinkedList<ShoppingCartItem>();
		ShoppingCartItem dummyShoppingCartItem;
		int orderRemainder = 0;

		Iterator clientCart = warehouse.getCartItems(context.instance().getUser());
		if (clientCart == null){
		    System.out.println("ID does not exist.");
		    return;
        }
		while (clientCart.hasNext()){//while client's cart is not empty
		    orderRemainder = 0;
            dummyShoppingCartItem = (ShoppingCartItem)clientCart.next();
            if (dummyShoppingCartItem.getQuantity() > warehouse.getProductQuantity(dummyShoppingCartItem.getItem().getId())){//if quantity in cart is greater than inventory (backorder)
                orderRemainder = dummyShoppingCartItem.getQuantity() - warehouse.getProductQuantity(dummyShoppingCartItem.getItem().getId());//cart quantity - warehouse quantity
                warehouse.addProductBackorderQuantity(dummyShoppingCartItem.getItem().getId(), orderRemainder);//update backorders
                dummyShoppingCartItem.setQuantity(orderRemainder);//update orders
                dummyBackorder.add(dummyShoppingCartItem);
                clientCart.remove();
                backorderFound = true;

                warehouse.setProductQuantity(dummyShoppingCartItem.getItem().getId(), 0);//set warehouse quantity to 0
            } else {//client ordered less product than was available
                /*Subtract order quantity from warehouse quantity*/
                warehouse.addProductQuantity(dummyShoppingCartItem.getItem().getId(), - dummyShoppingCartItem.getQuantity());
                dummyOrder.add(dummyShoppingCartItem);//product added to order
                clientCart.remove();
                orderFound = true;
            }
        }

		double totalPrice = 0;
		if(orderFound){
		    warehouse.addOrder(context.instance().getUser(), dummyOrder);

		   /*deduct price of all items from account*/
            for (int i = 0; i < dummyOrder.size(); i++){
                totalPrice += dummyOrder.get(i).getItem().getPrice() * dummyOrder.get(i).getQuantity();
            }
            warehouse.setClientBalance(context.instance().getUser(), warehouse.getClientBalance(context.instance().getUser()) - totalPrice);
        }
		if(backorderFound){
            warehouse.addBackorder(context.instance().getUser(), dummyBackorder);

            /*deduct price of all items from account*/
            for (int i = 0; i < dummyBackorder.size(); i++){
                totalPrice += dummyBackorder.get(i).getItem().getPrice() * dummyBackorder.get(i).getQuantity();
            }
            warehouse.setClientBalance(context.instance().getUser(), warehouse.getClientBalance(context.instance().getUser()) - totalPrice);
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
		System.out.println(CLIENT_DETAILS + ".) Show client details");
		System.out.println(LIST_PRODUCTS_WITH_SALE_PRICES + ".) Show list of products with sale prices");
		System.out.println(LIST_CLIENT_TRANSACTIONS + ".) Show client transactions");
		System.out.println(EDIT_CLIENT_CART + ".) Edit client's shopping cart");
		System.out.println(ADD_TO_CLIENT_CART + ".) Add to client's shopping cart");
		System.out.println(DISPLAY_CLIENT_CART + ".) Display client's shopping cart");
		System.out.println(DISPLAY_CLIENT_WAITLIST + ".) Display client's waitlist");
		System.out.println(PROCESS_CLIENT_ORDER + ".) Process client's order");
		System.out.println(HELP + ".) Help");
	}

	/*
     * Function:	logout
     * Type:		void
     * Privacy:		public
     * Description:	Logs out the user.
     */
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
				case DISPLAY_CLIENT_CART:          		displayClientCart();
														break;
				case DISPLAY_CLIENT_WAITLIST:          	displayClientWaitlist();
														break;
				case PROCESS_CLIENT_ORDER:          	processClientOrder();
														break;
				case HELP:              				help();
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
