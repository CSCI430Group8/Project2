import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.io.*;


public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PRODUCT_STRING = "P";
    private String id,
            name;
    private int quantity,
			backorderQuantity;
    private double price;
    NumberFormat format;
	private LinkedList<PurchasePrice> PurchasePrices = new LinkedList<PurchasePrice>();
	
	class PurchasePrice implements Serializable {
		private Supplier supplier;
		private double purchasePrice;
        
		/*
		* Function:    PurchasePrice
		* Type:        constructor(generic)
		* Privacy:        public
		* Description:    PurchasePrice Constructor.
		*/
		PurchasePrice(Supplier supplier, double purchasePrice){
			this.supplier = supplier;
			this.purchasePrice = purchasePrice;
		}//end constructor
        
		/*
		* Function:    getSupplier
		* Type:        Supplier
		* Privacy:        public
		* Description:    Gets Supplier of Product.
		*/
		public Supplier getSupplier(){
			return supplier;
		}//end getSupplier

		/*
		* Function:    getPurchasePrice
		* Type:        int
		* Privacy:        public
		* Description:    Gets Product Purchase Price.
		*/
		public double getPurchasePrice(){
			return purchasePrice;
		}//end getPrice
        
		/*
		* Function:    setSupplier
		* Type:        void
		* Privacy:        public
		* Description:    Sets Supplier of Product.
		*/
		public void setSupplier(Supplier supplier){
			this.supplier = supplier;
		}//end setSupplier

		/*
		* Function:    setPurchasePrice
		* Type:        void
		* Privacy:        public
		* Description:    Sets Product Purchase Price.
		*/
		public void setPurchasePrice(double purchasePrice){
			this.purchasePrice = purchasePrice;
		}//end setId
        
		/*
		* Function:    toString
		* Type:        String
		* Privacy:        public
		* Description:    Converts PurchasePrice to string output.
		*/
		public String toString(){
			return "Name: " + supplier.getName() + " Price: " + purchasePrice;
		}//end display
	}
	
	/*
     * Function:	Product
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	Product Constructor.
	 */
	Product(String name, double price, int quantity){
        id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.backorderQuantity = 0;
        this.format = new DecimalFormat("#0.00");
    }//end constructor
	
	/*
     * Function:	getId
     * Type:		String
     * Privacy:		public
     * Description:	Gets Product ID.
	 */
	public String getId(){
		return id;
	}//end getId
	
	/*
     * Function:	getName
     * Type:		String
     * Privacy:		public
     * Description:	Gets Product Name.
	 */
	public String getName(){
		return name;
	}//end getName
	
	/*
     * Function:	getQuantity
     * Type:		int
     * Privacy:		public
     * Description:	Gets Product Quantity.
	 */
	public int getQuantity(){
		return quantity;
	}//end getQuantity

	/*
	 * Function:	getBackorderQuantity
	 * Type:		int
	 * Privacy:		public
	 * Description:	Gets Product's Backorder Quantity.
	 */
	public int getBackorderQuantity(){
		return backorderQuantity;
	}//end getQuantity
	
	/*
     * Function:	getPrice
     * Type:		double
     * Privacy:		public
     * Description:	Gets Product Price.
	 */
	public double getPrice(){
		return price;
	}//end getPrice
	
	/*
     * Function:	setId
     * Type:		void
     * Privacy:		public
     * Description:	Sets Product ID.
	 */
	public void setId(String id){
		this.id = id;
	}//end setId
	
	/*
     * Function:	setName
     * Type:		void
     * Privacy:		public
     * Description:	Sets Product Name.
	 */
	public void setName(String name){
		this.name = name;
	}//end setName
	
	/*
     * Function:	setQuantity
     * Type:		void
     * Privacy:		public
     * Description:	Sets Product Quantity.
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}//end setQuantity

	/*
	 * Function:	getBackorderQuantity
	 * Type:		int
	 * Privacy:		public
	 * Description:	Gets Product's Backorder Quantity.
	 */
	public void setBackorderQuantity(int newQuantity){
		this.backorderQuantity = newQuantity;
	}//end getQuantity
	
	/*
     * Function:	setPrice
     * Type:		void
     * Privacy:		public
     * Description:	Sets Product Price.
	 */
	public void setPrice(double price){
		this.price = price;
	}//end setPrice
	
	/*
     * Function:	setPurchasePrice
     * Type:		boolean
     * Privacy:		public
     * Description:	Sets new price for a purchase price with a supplier.
	 */
	public boolean setPurchasePrice(String supplierId, double purchasePrice) {
		boolean entryFound = false;
		Iterator allPurchasePrices = getPurchasePrices();
		while (allPurchasePrices.hasNext() & !entryFound){
			PurchasePrice nextPurchasePrice = (PurchasePrice)(allPurchasePrices.next());
			if(nextPurchasePrice.getSupplier().getId().contentEquals(supplierId)) { 
				entryFound = true;
				nextPurchasePrice.setPurchasePrice(purchasePrice);
			}
		}
		return entryFound;
	}//end setPurchasePrice
	
	/*
     * Function:	removePurchasePrice
     * Type:		boolean
     * Privacy:		public
     * Description:	Removes ShoppingCartItem from ShoppingCart.
	 */
	public boolean removePurchasePrice(String supplierId) {
		boolean entryFound = false;
		Iterator allPurchasePrices = getPurchasePrices();
		while (allPurchasePrices.hasNext() & !entryFound){
			PurchasePrice nextPurchasePrice = (PurchasePrice)(allPurchasePrices.next());
			if(nextPurchasePrice.getSupplier().getId().contentEquals(supplierId)) { 
				entryFound = true;
				allPurchasePrices.remove();
			}
		}
		return entryFound;
	}//end removePurchasePrice
	
	/*
     * Function:	insertPurchasePrice
     * Type:		boolean
     * Privacy:		public
     * Description:	Inserts Supplier and purchase price.
	 */
	public boolean insertPurchasePrice(Supplier supplier, double purchasePrice) {
		boolean result;
		PurchasePrice newPurchasePrice = new PurchasePrice(supplier, purchasePrice);
		result = PurchasePrices.add(newPurchasePrice);
		return result;
	}//end insertPurchasePrice
	
	/*
     * Function:	getPurchasePrices
     * Type:		Iterator
     * Privacy:		public
     * Description:	Returns an iterator for ShoppingCart
	 */
	public Iterator getPurchasePrices(){
		return PurchasePrices.iterator();
	}//end getPurchasePrices

    /*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts Product to string output.
	 */
    public String toString(){
        return "ID: " + id + " Name: " + name + " Price Per Item: $" + price + " Quantity: " + quantity + " Backordered Quantity: " + backorderQuantity;
    }//end toString
}