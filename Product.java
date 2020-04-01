import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.io.*;


public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PRODUCT_STRING = "P";
    private String id,
            name,
			supplier;
    private int quantity,
			backorderQuantity;
    private double price;
    NumberFormat format;
	
	/*
     * Function:	Product
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	Product Constructor.
	 */
	Product(String name, String supplier, double price, int quantity){
        id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.backorderQuantity = 0;
        this.supplier = supplier;
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
	 * Function:	setSupplier
	 * Type:		void
	 * Privacy:		public
	 * Description:	Sets Product Supplier.
	 */
	public void setSupplier(String supplier){
		this.supplier = supplier;
	}//end setPrice

    /*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts Product to string output.
	 */
    public String toString(){
        return "ID: " + id + " Name: " + name + " Supplier: " + supplier + " Price Per Item: $" + price + " Quantity: " + quantity + " Backordered Quantity: " + backorderQuantity;
    }//end toString
}