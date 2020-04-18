import java.util.*;
import java.text.*;
import java.io.*;
public class ManagerMenuState extends WarehouseState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private Context context;
	private static ManagerMenuState instance;
	private static final int EXIT = 0;
	private static final int ADD_PRODUCT = 1;
	private static final int ADD_SUPPLIER = 2;
	private static final int LIST_SUPPLIERS= 3;
	private static final int LIST_SUPPLIERS_OF_PRODUCT_WITH_PURCHASE_PRICES = 4;
	private static final int LIST_PRODUCTS_OF_SUPPLIER_WITH_PURCHASE_PRICES = 5;
	private static final int ADD_SUPPLIER_FOR_A_PRODUCT = 6;
	private static final int MODIFY_PURCHASE_PRICE_OF_PRODUCT_FROM_SUPPLIER = 7;
	private static final int CLERKMENU = 8;
	private static final int HELP = 9;
	
	/*
     * Function:	ManagerMenuState
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for ManagerMenuState class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				ManagerMenuState can be initialized at a time
     */
	private ManagerMenuState() {
		super();
		warehouse = Warehouse.instance();
	}
  
	/*
     * Function:	instance
     * Type:		ManagerMenuState
     * Privacy:		public
     * Description:	This is the singleton for ManagerMenuState, which, if were to
     * 				try an initialize a second ClientMenuState class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current ManagerMenuState class.
     */
	public static ManagerMenuState instance() {
		if (instance == null) {
		instance = new ManagerMenuState();
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
     * Function:	addProduct
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user to enter a product name, sales price, and quantity.
					Creates product from the inputs.
     */
	public void addProduct() {
        int quantity = 0;
        double price = 0;

        String name;

        name = getToken("\nProduct Name: ");
        price = Math.round(getPrice("\nProduct Sales Price: ") * 100.0) / 100.0;//rounds to 2 decimals
        quantity = getNumber("\nProduct Quantity: ");

        Product dummyProduct;//create dummy entry based on inputs

		dummyProduct = warehouse.addProduct(name, price, quantity);
		if (dummyProduct == null) {
			System.out.println("Could not add product, try again.");
        }
	} //end method
	
	/*
     * Function:	addSupplier
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user to enter a supplier name, phone number, and address.
					Creates supplier from the inputs.
     */
	public void addSupplier() {
        String name,
               phone,
               address;

        name = getToken("\nSupplier Name: ");
        phone = getToken("\nPhone Number: ");
        address = getToken("\nAddress: ");
        Supplier dummySupplier;

        dummySupplier = warehouse.addSupplier(name, phone, address);
        if (dummySupplier == null) {
            System.out.println("Could not add supplier, try again.");
        }		
	}
	
	/*
     * Function:	listSuppliers
     * Type:		void
     * Privacy:		public
     * Description:	List all of the suppliers in the system
     */
	public void listSuppliers() {
		Iterator allSuppliers = warehouse.getSuppliers();
		while (allSuppliers.hasNext()){
			Supplier nextSupplier = (Supplier)(allSuppliers.next());
            System.out.println(nextSupplier.toString());
            System.out.println();
        }//end while
		
	}
	
	/*
     * Function:	listSuppliersOfProductWithPurchasePrices
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user for a productId and outputs suppliers 
					and purchase prices associated with the product.
     */
	public void listSuppliersOfProductWithPurchasePrices() {
		boolean entryFound = false;
		String productId;
		
		productId = getToken("\nEnter the ID of the product: ");
		
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext() & !entryFound){
			Product nextProduct = (Product)(allProducts.next());
			if(nextProduct.getId().contentEquals(productId)) {
				Iterator allPurchasePrices = nextProduct.getPurchasePrices();
				while (allPurchasePrices.hasNext()) {
					Product.PurchasePrice nextPurchasePrice = (Product.PurchasePrice)(allPurchasePrices.next());
					System.out.println(nextPurchasePrice.toString());
				}
				entryFound = true;
			}
		}//end while
	}
	
	/*
     * Function:	listProductsOfSupplierWithPurchasePrices
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user for a supplierId and outputs products 
					and purchase prices associated with the supplier.
     */
	public void listProductsOfSupplierWithPurchasePrices() {
		boolean entryFound = false;
		String supplierId;
		
		supplierId = getToken("\nEnter the ID of the supplier: ");
		
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext()){
			Product nextProduct = (Product)(allProducts.next());
			Iterator allPurchasePrices = nextProduct.getPurchasePrices();
			while (allPurchasePrices.hasNext()) {
				Product.PurchasePrice nextPurchasePrice = (Product.PurchasePrice)(allPurchasePrices.next());
				if(nextPurchasePrice.getSupplier().getId().contentEquals(supplierId)){
					System.out.println("Product Name: " + nextProduct.getName() + " Product Purchase Price: " + nextPurchasePrice.getPurchasePrice());
				}
			}
		}//end while
		
	} //end method
	
	/*
     * Function:	addSupplierForAProduct
     * Type:		void
     * Privacy:		public
     * Description:	Connects a supplier and a product with a purchase price.
     */
	public void addSupplierForAProduct() {
		String productId,
				supplierId;
		
		double purchasePrice;
				
		boolean productFound = false,
				supplierFound = false,
				result = false;
		
		productId = getToken("\nInput the ID of the product: ");
        Iterator allProducts = warehouse.getProducts();
        Product nextProduct;
        while(!productFound & allProducts.hasNext()){
            nextProduct = (Product)allProducts.next();
            if(nextProduct.getId().contentEquals(productId)) {
                productFound = true;
				supplierId = getToken("\nInput the ID of the supplier: ");

				Iterator allSuppliers = warehouse.getSuppliers();
				Supplier nextSupplier;
				while(!supplierFound & allSuppliers.hasNext()){
					nextSupplier = (Supplier)allSuppliers.next();
					if(nextSupplier.getId().contentEquals(supplierId)) {
						supplierFound = true;
						purchasePrice = Math.round(getPrice("\nInput the purchase price of the product for the supplier: ") * 100.0) / 100.0;;
						result = warehouse.addPurchasePrice(nextProduct, nextSupplier, purchasePrice);
						if(result) {
							System.out.println("\nPurchase price successfully added.");
						}
						else {
							System.out.println("\nFailed to add purchase price, try again.");
						}
					}
				}
            }
        }
	}
	
	/*
     * Function:	modifyPurchasePriceOfProductFromSupplier
     * Type:		void
     * Privacy:		public
     * Description:	Prompts the user for a productId, supplierId and new purchase price.
					Changes purchase price between the product and supplier.
     */
	public void modifyPurchasePriceOfProductFromSupplier() {
		String productId,
				supplierId;
		
		double purchasePrice;
				
		boolean productFound = false,
				supplierFound = false,
				result = false;
		
		productId = getToken("\nInput the ID of the product: ");
        Iterator allProducts = warehouse.getProducts();
        Product nextProduct;
        while(!productFound & allProducts.hasNext()){
            nextProduct = (Product)allProducts.next();
            if(nextProduct.getId().contentEquals(productId)) {
                productFound = true;
				supplierId = getToken("\nInput the ID of the supplier: ");

				Iterator allSuppliers = warehouse.getSuppliers();
				Supplier nextSupplier;
				while(!supplierFound & allSuppliers.hasNext()){
					nextSupplier = (Supplier)allSuppliers.next();
					if(nextSupplier.getId().contentEquals(supplierId)) {
						supplierFound = true;
						purchasePrice = Math.round(getPrice("\nInput the new purchase price of the product for the supplier: ") * 100.0) / 100.0;;
						result = warehouse.setPurchasePrice(productId, supplierId, purchasePrice);
						if(result) {
							System.out.println("\nPurchase price successfully modified.");
						}
						else {
							System.out.println("\nFailed to modify purchase price, try again.");
						}
					}
				}
            }
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
		System.out.println(ADD_PRODUCT + ".) Add a product");
		System.out.println(ADD_SUPPLIER + ".) Add a supplier");
		System.out.println(LIST_SUPPLIERS + ".) Show list of suppliers");
		System.out.println(LIST_SUPPLIERS_OF_PRODUCT_WITH_PURCHASE_PRICES + ".) Show list of suppliers for a product, with purchase prices");
		System.out.println(LIST_PRODUCTS_OF_SUPPLIER_WITH_PURCHASE_PRICES + ".) Show list of products for a supplier, with purchase prices");
		System.out.println(ADD_SUPPLIER_FOR_A_PRODUCT + ".) Add a supplier for a product");
		System.out.println(MODIFY_PURCHASE_PRICE_OF_PRODUCT_FROM_SUPPLIER + ".) Modify purchase price for a particular product from a particular supplier");
		System.out.println(CLERKMENU + ".) Become a salesclerk");
		System.out.println(HELP + ".) Help");
	}

	/*
     * Function:	clerkmenu
     * Type:		void
     * Privacy:		public
     * Description:	Switches the user to the clerk menu.
     */
	public void clerkmenu()
	{    
		(Context.instance()).changeState(1);
	}
	
	/*
     * Function:	logout
     * Type:		void
     * Privacy:		public
     * Description:	Logs out the user.
     */
	public void logout()
	{
		(Context.instance()).changeState(3); // exit with a code 3
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
				case ADD_PRODUCT:        									addProduct();
																			break;
				case ADD_SUPPLIER:      									addSupplier();
																			break;
				case LIST_SUPPLIERS:      									listSuppliers();
																			break;
				case LIST_SUPPLIERS_OF_PRODUCT_WITH_PURCHASE_PRICES:    	listSuppliersOfProductWithPurchasePrices();
																			break;
				case LIST_PRODUCTS_OF_SUPPLIER_WITH_PURCHASE_PRICES:      	listProductsOfSupplierWithPurchasePrices();
																			break;
				case ADD_SUPPLIER_FOR_A_PRODUCT:          					addSupplierForAProduct();
																			break;
				case MODIFY_PURCHASE_PRICE_OF_PRODUCT_FROM_SUPPLIER:        modifyPurchasePriceOfProductFromSupplier();
																			break;
				case CLERKMENU:          									clerkmenu();
																			break;
				case HELP:              									help();
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
