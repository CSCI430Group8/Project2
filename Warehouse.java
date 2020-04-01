import java.text.DecimalFormat;
import java.util.*;
import java.io.*;



public class Warehouse implements Serializable {
    transient Scanner inputScanner = new Scanner(System.in);//create scanner for input

    private static Warehouse warehouse;
	private ClientList clients;
	private SupplierList suppliers;
	private ProductList inventory;
	private BackorderList backorders;
	private OrderList orders;

    /*
     * Function:	Warehouse
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for Warehouse class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				Warehouse can be initialized at a time
     */
    private Warehouse(){
        clients = ClientList.instance();
        suppliers = SupplierList.instance();
        inventory = ProductList.instance();
        backorders = BackorderList.instance();
        orders = OrderList.instance();
    }//end constructor

    /*
     * Function:	instance
     * Type:		Warehouse
     * Privacy:		public
     * Description:	This is the singleton for Warehouse, which, if were to
     * 				try an initialize a second Warehouse class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current Warehouse class.
     */
    public static Warehouse instance(){
        if (warehouse == null){
            warehouse = new Warehouse();
        }
        return warehouse;
    }//end instance
	
	/*
     * Function:	getClients
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the ClientList that allows
					for traversal through the different Clients within the
					list for the tester.
     */
	public Iterator getClients() {
		return clients.getClients();
	}//end getClients

	/*
     * Function:	getSuppliers
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the SupplierList that allows
					for traversal through the different Suppliers within the
					list for the tester.
     */
	public Iterator getSuppliers() {
		return suppliers.getSuppliers();
	}//end getSuppliers

	/*
     * Function:	getProducts
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the ProductList that allows
					for traversal through the different Products within the
					list for the tester.
     */
	public Iterator getProducts() {
		return inventory.getProducts();
	}//end getProducts
	
	/*
     * Function:	getBackorders
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the BackorderList that allows
					for traversal through the different backorders within the
					list for the tester.
     */
	public Iterator getBackorders() {
		return backorders.getBackorders();
	}//end getProducts

	/*
     * Function:	getOrders
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the OrderList that allows
					for traversal through the different orders within the
					list for the tester.
     */
	public Iterator getOrders() {
		return orders.getOrders();
	}//end getProducts
	
	/*
     * Function:	getCartItems
     * Type:		Iterator
     * Privacy:		public
     * Description:	This returns an iterator for the Client ShoppingCart that allows
					for traversal through the different items within the
					shopping cart of the client.
     */
	public Iterator getCartItems(String id) {
		return clients.getCartItems(id);
	}//end getProducts
	
	/*
     * Function:	addBackorder
     * Type:		void
     * Privacy:		public
     * Description:	Add an order to the backorder list.
     */
	public void addBackorder(String clientId, LinkedList<ShoppingCartItem> orderedItems) {
		backorders.addBackorder(clientId, orderedItems);
	}//end getProducts

	/*
	 * Function:	addOrder
	 * Type:		void
	 * Privacy:		public
	 * Description:	Add an order to the backorder list.
	 */
	public void addOrder(String clientId, LinkedList<ShoppingCartItem> orderedItems) {
		orders.addOrder(clientId, orderedItems);
	}//end getProducts
	
	/*
     * Function:	addClient
     * Type:		Client
     * Privacy:		public
     * Description:	This adds a Client to the ClientList, and then it returns 
					if the Client that was added.
     */
	public Client addClient(String name, String phone, String address) {
		Client client = new Client(name, phone, address);
		if (clients.insertClient(client)) {
			return (client);
		}
		return null;
	}//end addClient
	
	/*
     * Function:	addSupplier
     * Type:		Supplier
     * Privacy:		public
     * Description:	This adds a Supplier to the SupplierList, and then it returns 
					if the Supplier that was added.
     */
	public Supplier addSupplier(String name, String phone, String address) {
		Supplier supplier = new Supplier(name, phone, address);
		if (suppliers.insertSupplier(supplier)) {
			return (supplier);
		}
		return null;
	}//end addSupplier
	
	/*
     * Function:	addProduct
     * Type:		Product
     * Privacy:		public
     * Description:	This adds a Product to the ProductList, and then it returns 
					if the Product that was added.
     */
	public Product addProduct(String name, String supplier, double price, int quantity) {
		Product product = new Product(name, supplier, price, quantity);
		if (inventory.insertProduct(product)) {
			return (product);
		}
		return null;
	}//end addProduct

	/*
     * Function:	setProductPrice
     * Type:		boolean
     * Privacy:		public
     * Description:
     */
	public boolean setProductPrice(String id, double price){
		return inventory.setProductPrice(id, price);
	}//end setProductPrice

	/*
	 * Function:	getProductPrice
	 * Type:		double
	 * Privacy:		public
	 * Description:
	 */
	public double getProductPrice(String id){
		return inventory.getProductPrice(id);
	}//end getProductPrice

	/*
	 * Function:	setProductQuantity
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setProductQuantity(String id, int quantity){
		return inventory.setProductQuantity(id, quantity);
	}//end setProductQuantity
	
	/*
	 * Function:	addProductQuantity
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean addProductQuantity(String id, int quantity){
		return inventory.setProductQuantity(id, inventory.getProductQuantity(id) + quantity);
	}//end addProductQuantity

	/*
	 * Function:	setProductBackorderQuantity
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setProductBackorderQuantity(String id, int quantity){
		return inventory.setProductBackorderQuantity(id, quantity);
	}//end setProductQuantity
	
	/*
	 * Function:	addProductBackorderQuantity
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean addProductBackorderQuantity(String id, int quantity){
		return inventory.setProductBackorderQuantity(id, inventory.getProductBackorderQuantity(id) + quantity);
	}//end addProductQuantity

	/*
	 * Function:	getProductQuantity
	 * Type:		int
	 * Privacy:		public
	 * Description:
	 */
	public int getProductQuantity(String id){
		return inventory.getProductQuantity(id);
	}//end getProductQuantity

	/*
	 * Function:	getProductBackorderQuantity
	 * Type:		int
	 * Privacy:		public
	 * Description:
	 */
	public int getProductBackorderQuantity(String id){
		return inventory.getProductBackorderQuantity(id);
	}//end getProductQuantity

	/*
	 * Function:	setProductName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setProductName(String id, String name){
		return inventory.setProductName(id, name);
	}//end setProductName
	
	/*
	 * Function:	setCartItemQuant
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setCartItemQuant(String clientId, String productId, int quantity){
		boolean result = false,
				entryFound = false;
		Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!entryFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(clientId)) {
                entryFound = true;
				result = nextClient.setCartItemQuant(productId, quantity);
            }
        }
		return result;
	}//end setCartItemQuant

	/*
	 * Function:	getProductName
	 * Type:		String
	 * Privacy:		public
	 * Description:
	 */
	public String getProductName(String id){
		return inventory.getProductName(id);
	}//end getProductName

	/*
	 * Function:	setClientBalance
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setClientBalance(String id, double balance){
		return clients.setClientBalance(id, balance);
	}//end setClientBalance

	/*
	 * Function:	getClientBalance
	 * Type:		double
	 * Privacy:		public
	 * Description:
	 */
	public double getClientBalance(String id){
		return clients.getClientBalance(id);
	}//end getProductPrice

	/*
	 * Function:	setClientName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setClientName(String id, String name){
		return clients.setClientName(id, name);
	}//end setClientName

	/*
	 * Function:	setClientPhone
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setClientPhone(String id, String phone){
		return clients.setClientPhone(id, phone);
	}//end setClientPhone

	/*
	 * Function:	setClientAddress
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setClientAddress(String id, String address){
		return clients.setClientAddress(id, address);
	}//end setClientAddress

	/*
	 * Function:	setSupplierName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierName(String id, String name){
		return suppliers.setSupplierName(id, name);
	}//end setSupplierName

	/*
	 * Function:	setSupplierPhone
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierPhone(String id, String phone){
		return suppliers.setSupplierPhone(id, phone);
	}//end setSupplierPhone

	/*
	 * Function:	setSupplierAddress
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierAddress(String id, String address){
		return suppliers.setSupplierAddress(id, address);
	}//end setSupplierAddress
	
	/*
     * Function:	addToClientCart
     * Type:		boolean
     * Privacy:		public
     * Description:	This finds the Client using their ID and then 
					inserts a Product into their Cart.
	 */
	public boolean addToClientCart(Client client, Product product, int quantity) {
		/* Copy product to add in cart */
		return client.insertToCart(product, quantity);
	}//end addToClientCart

	/*
     * Function:	addToOrder
     * Type:		boolean
     * Privacy:		public
     * Description:	This finds the Client using their ID and then
					inserts a Product into their Cart.
	 */
	
	/*
     * Function:	removeClient
     * Type:		boolean
     * Privacy:		public
     * Description:	This removes a Client from the ClientList, and then it returns 
					if the Client that was removed.
     */
	public boolean removeClient(String id) {
		boolean entryFound = false;
		Iterator allClients = warehouse.getClients();
        while(!entryFound & allClients.hasNext()){
            Client nextClient = (Client)(allClients.next());
            if(nextClient.getId().contentEquals(id)){
                entryFound = true;
                allClients.remove();
            }
        }
		return entryFound;
	}//end removeClient
	
	/*
     * Function:	removeSupplier
     * Type:		boolean
     * Privacy:		public
     * Description:	This removes a Supplier from the SupplierList, and then it returns 
					if the Supplier that was removed.
     */
	public boolean removeSupplier(String id) {
		boolean entryFound = false;
		Iterator allSuppliers = warehouse.getSuppliers();
        while(!entryFound & allSuppliers.hasNext()){
            Supplier nextSupplier = (Supplier)(allSuppliers.next());
            if(nextSupplier.getId().contentEquals(id)){
                entryFound = true;
                allSuppliers.remove();
            }
        }
		return entryFound;
	}//end removeSupplier
	
	/*
     * Function:	removeProduct
     * Type:		boolean
     * Privacy:		public
     * Description:	This removes a Product from the ProductList, and then it returns 
					if the Product that was removed.
     */
	public boolean removeProduct(String id) {
		boolean entryFound = false;
		Iterator allProducts = warehouse.getProducts();
        while(!entryFound & allProducts.hasNext()){
            Product nextProduct = (Product)(allProducts.next());
            if(nextProduct.getId().contentEquals(id)){
                entryFound = true;
                allProducts.remove();
            }
        }
		return entryFound;
	}//end removeProduct
	
	/*
	 * Function:	removeCartItem
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean removeCartItem(String clientId, String productId){
		boolean result = false,
				entryFound = false;
		Iterator allClients = warehouse.getClients();
        Client nextClient;
        while(!entryFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(clientId)) {
                entryFound = true;
				result = nextClient.removeCartItem(productId);
            }
        }
		return result;
	}//end removeCartItem
	
	/*
     * Function:	retrieve
     * Type:		Warehouse
     * Privacy:		public
     * Description:	Retrieves data from file called WarehouseData and
					sets all of the data into the warehouse database.
     */
	public static Warehouse retrieve() {
		try {
			FileInputStream file = new FileInputStream("WarehouseData");
			ObjectInputStream input = new ObjectInputStream(file);
			input.readObject();
			ClientIdServer.retrieve(input);
			ProductIdServer.retrieve(input);
			SupplierIdServer.retrieve(input);
			return warehouse;
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
			return null;
		} 
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}//end retrieve
	
	/*
     * Function:	save
     * Type:		boolean
     * Privacy:		public
     * Description:	Outputs data into a file called WarehouseData and
					saves it for future use.
     */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("WarehouseData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(warehouse);
			output.writeObject(ClientIdServer.instance());
			output.writeObject(ProductIdServer.instance());
			output.writeObject(SupplierIdServer.instance());
			return true;
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}//end save
	
	/*
     * Function:	writeObject
     * Type:		void
     * Privacy:		private
     * Description:	Writes Warehouse class to disk.
     */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(warehouse);
		} 
		catch(IOException ioe) {
			System.out.println(ioe);
		}
	}//end writeObject
	
	/*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads Warehouse class from disk.
     */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			input.defaultReadObject();
			if (warehouse == null) {
				warehouse = (Warehouse) input.readObject();
			} 
			else {
				input.readObject();
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}//end readObject
	
	/*
     * Function:	toString
     * Type:		boolean
     * Privacy:		private
     * Description:	Converts Warehouse into string output.
     */
	public String toString() {
		return clients + "\n" + suppliers + "/n" + inventory;
	}//end toString
}