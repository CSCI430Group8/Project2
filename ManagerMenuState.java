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
	
	public double getPrice(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double num = Double.valueOf(item);
				System.out.println(num);
				return num;
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
        int quantity = 0;
        double price = 0;

        String name,
               supplier;

        name = getToken("\nProduct Name: ");
        supplier = getToken("\nSupplier Name: ");
        price = Math.round(getPrice("\nProduct Price: ") * 100.0) / 100.0;//rounds to 2 decimals
        quantity = getNumber("\nProduct Quantity: ");

        Product dummyProduct;//create dummy entry based on inputs

		dummyProduct = warehouse.addProduct(name, supplier, price, quantity);
		if (dummyProduct == null) {
			System.out.println("Could not add product, try again.");
        }
	} //end method
	
	public void addSupplier() {
        String name,
               phone,
               address;

        name = getToken("\nName: ");
        phone = getToken("\nPhone Number: ");
        address = getToken("\nAddress: ");
        Supplier dummySupplier;

        dummySupplier = warehouse.addSupplier(name, phone, address);
        if (dummySupplier == null) {
            System.out.println("Could not add supplier, try again.");
        }		
	}
	
	public void listSuppliers() {
		Iterator allSuppliers = warehouse.getSuppliers();
		while (allSuppliers.hasNext()){
			Supplier nextSupplier = (Supplier)(allSuppliers.next());
            System.out.println(nextSupplier.toString());
            System.out.println();
        }//end while
		
	}
	
	public void listSuppliersOfProductWithPurchasePrices() {
		Iterator allSuppliers = warehouse.getSuppliers();
		while (allSuppliers.hasNext()){
			Supplier nextSupplier = (Supplier)(allSuppliers.next());
            System.out.println(nextSupplier.toString());
            System.out.println();
		}//end while
	}
	
	public void listProductsOfSupplierWithPurchasePrices() {
		Iterator allProducts = warehouse.getProducts();
		while (allProducts.hasNext()){
			Product nextProduct = (Product)(allProducts.next());
            System.out.println(nextProduct.toString());
            System.out.println();
        }//end while
		
	} //end method
	
	public void addSupplierForAProduct() {
		String productId,
			   supplierId,
			   purchasePrice;
		
		productId = getToken("\nPlease enter the ID of the product: ");
		supplierId = getToken("\nPlease enter the ID of the supplier: ");
		purchasePrice = getToken("\nPlease enter the purchase price: ");
		
		
		
		
	}
	
	public void modifyPurchasePriceOFProductFromSupplier() {
		String productId,
			   supplierId,
			   purchasePrice;
		
		productId = getToken("\nPlease enter the ID of the product: ");
		supplierId = getToken("\nPlease enter the ID of the supplier: ");
		purchasePrice = getToken("\nPlease enter the purchase price: ");
		
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
