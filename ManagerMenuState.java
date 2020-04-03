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
	private ManagerMenuState() {
		super();
		warehouse = Warehouse.instance();
		//context = Context.instance();
	}
  
	public static ManagerMenuState instance() {
		if (instance == null) {
		instance = new ManagerMenuState();
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
	
	public void addProduct() {
		//System.out.println("need to add");
		
		int input = EXIT + 1;//arbitrary non-exit number

        while(input != EXIT) {

            int quantity = 0;
            double price = 0;

            String name,
                    phone,
                    address,
                    supplier;

           // System.out.println("\nWhat would you like to add?\n" + 
					//EXIT + ".) Go Back\n" +
                   // CLIENT + ".) Add Client\n"+
                    PRODUCT + ".) Add Product\n"+
                   // SUPPLIER + ".) Add Supplier\n");

            input = inputScanner.nextInt();
			

            switch(input){
                case EXIT:
                    break;
               /* case CLIENT:
                    System.out.print("\nName: ");
                    name = inputScanner.next();
                    System.out.print("\nPhone Number: ");
                    phone = inputScanner.next();
                    System.out.print("\nAddress: ");
                    address = inputScanner.next();
                    Client dummyClient;

                    dummyClient = warehouse.addClient(name, phone, address);
                    if (dummyClient == null) {
                        System.out.println("Could not add client, try again.");
                    }
                    break;*/
                case PRODUCT:
                    System.out.print("\nProduct Name: ");
                    name = inputScanner.next();
                    System.out.print("\nSupplier Name: ");
                    supplier = inputScanner.next();
                    System.out.print("\nProduct Price: ");
                    price = Math.round(inputScanner.nextDouble() * 100.0) / 100.0;//rounds to 2 decimals
                    System.out.print("\nProduct Quantity: ");
                    quantity = inputScanner.nextInt();

                    Product dummyProduct;//create dummy entry based on inputs

					dummyProduct = warehouse.addProduct(name, supplier, price, quantity);
					if (dummyProduct == null) {
					    System.out.println("Could not add product, try again.");
                    }
                    break;
                /*case SUPPLIER:
                    System.out.print("\nName: ");
                    name = inputScanner.next();
                    System.out.print("\nPhone Number: ");
                    phone = inputScanner.next();
                    System.out.print("\nAddress: ");
                    address = inputScanner.next();
                    Supplier dummySupplier;

                    dummySupplier = warehouse.addSupplier(name, phone, address);
                    if (dummySupplier == null) {
                        System.out.println("Could not add supplier, try again.");
                    }

                    break;
                default:
                    System.out.println("Not a valid input.\n");
                    break;*/
            }//end switch
        }//end while
		
	} //end method
	
	public void addSupplier() {
		System.out.println("need to add");
		
		int input = EXIT + 1;//arbitrary non-exit number

        while(input != EXIT) {

            int quantity = 0;
            double price = 0;

            String name,
                    phone,
                    address,
                    supplier;

           // System.out.println("\nWhat would you like to add?\n" + 
					//EXIT + ".) Go Back\n" +
                   // CLIENT + ".) Add Client\n"+
                    //PRODUCT + ".) Add Product\n"+
                    SUPPLIER + ".) Add Supplier\n");

            input = inputScanner.nextInt();
			

            switch(input){
                case EXIT:
                    break;
               /* case CLIENT:
                    System.out.print("\nName: ");
                    name = inputScanner.next();
                    System.out.print("\nPhone Number: ");
                    phone = inputScanner.next();
                    System.out.print("\nAddress: ");
                    address = inputScanner.next();
                    Client dummyClient;

                    dummyClient = warehouse.addClient(name, phone, address);
                    if (dummyClient == null) {
                        System.out.println("Could not add client, try again.");
                    }
                    break;*/
                /*case PRODUCT:
                    System.out.print("\nProduct Name: ");
                    name = inputScanner.next();
                    System.out.print("\nSupplier Name: ");
                    supplier = inputScanner.next();
                    System.out.print("\nProduct Price: ");
                    price = Math.round(inputScanner.nextDouble() * 100.0) / 100.0;//rounds to 2 decimals
                    System.out.print("\nProduct Quantity: ");
                    quantity = inputScanner.nextInt();

                    Product dummyProduct;//create dummy entry based on inputs

					dummyProduct = warehouse.addProduct(name, supplier, price, quantity);
					if (dummyProduct == null) {
					    System.out.println("Could not add product, try again.");
                    }*/
                    break;
                case SUPPLIER:
                    System.out.print("\nName: ");
                    name = inputScanner.next();
                    System.out.print("\nPhone Number: ");
                    phone = inputScanner.next();
                    System.out.print("\nAddress: ");
                    address = inputScanner.next();
                    Supplier dummySupplier;

                    dummySupplier = warehouse.addSupplier(name, phone, address);
                    if (dummySupplier == null) {
                        System.out.println("Could not add supplier, try again.");
                    }

                    break;
                default:
                    System.out.println("Not a valid input.\n");
                    break;*/
            }//end switch
        }//end while
		
	}
	
	public void listSuppliers() {
		System.out.println("need to add");
		
		Iterator allSuppliers = warehouse.getSuppliers();
		while (allSuppliers.hasNext()){
			Supplier nextSupplier = (Supplier)(allSuppliers.next());
            System.out.println(nextSupplier.toString());
            System.out.println();
        }//end while
		
	}
	
	public void listSuppliersOfProductWithPurchasePrices() {
		System.out.println("need to add");
		
		
		Iterator allSuppliers = warehouse.getSuppliers();
		while (allSuppliers.hasNext()){
			Supplier nextSupplier = (Supplier)(allSuppliers.next());
            System.out.println(nextSupplier.toString());
            System.out.println();
		
	}
	
	public void listProductsOfSupplierWithPurchasePrices() {
		System.out.println("need to add");
		
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext()){
			Product nextProduct = (Product)(allProducts.next());
            System.out.println(nextProduct.toString());
            System.out.println();
        }//end while
		
	} //end method
	
	public void addSupplierForAProduct() {
		System.out.println("need to add");
		
		System.out.println("Please enter productID:");
		productID = inputScanner.next();
		System.out.println("Please enter supplierID:");
		supplierID = inputScanner.next();
		System.out.println("Please enter the purchase price:");
		purchasePrice = inputScanner.next();
		
		
		
		
	}
	
	public void modifyPurchasePriceOFProductFromSupplier() {
		System.out.println("need to add");
		
		System.out.println("Please enter productID:");
		productID = inputScanner.next();
		System.out.println("Please enter supplierID:");
		supplierID = inputScanner.next();
		System.out.println("Please enter the purchase price:");
		purchasePrice = inputScanner.next();
		
	}

	public void help() {
		System.out.println("Enter a number between 0 and 9 as explained below:");
		System.out.println(EXIT + " to Exit");
		System.out.println(ADD_PRODUCT + " to add a product");
		System.out.println(ADD_SUPPLIER + " to add a supplier");
		System.out.println(LIST_SUPPLIERS + " to show list of suppliers");
		System.out.println(LIST_SUPPLIERS_OF_PRODUCT_WITH_PURCHASE_PRICES + " to show list of suppliers for a product, with purchase prices");
		System.out.println(LIST_PRODUCTS_OF_SUPPLIER_WITH_PURCHASE_PRICES + " to show list of products for a supplier, with purchase prices");
		System.out.println(ADD_SUPPLIER_FOR_A_PRODUCT + " to add a supplier for a product");
		System.out.println(MODIFY_PURCHASE_PRICE_OF_PRODUCT_FROM_SUPPLIER + " to modify purchase price for a particular product from a particular supplier");
		System.out.println(CLERKMENU + " to become a salesclerk");
		System.out.println(HELP + " for help");
	}

	public void clerkmenu()
	{    
		(Context.instance()).changeState(1);
	}

	public void logout()
	{
		(Context.instance()).changeState(3); // exit with a code 3
	}
 

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
				case MODIFY_PURCHASE_PRICE_OF_PRODUCT_FROM_SUPPLIER:        modifyPurchasePriceOFProductFromSupplier();
																			break;
				case CLERKMENU:          									clerkmenu();
																			break;
				case HELP:              									help();
																			break;
			}
		}
		logout();
	}
	
	public void run() {
		process();
	}
}
