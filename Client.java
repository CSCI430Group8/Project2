import java.text.DecimalFormat;
import java.util.*;
import java.io.*;


public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
    private double balance;
	private static final String CLIENT_STRING = "C";
    private String id,
            name,
            phone,
            address;
    private LinkedList<ShoppingCartItem> ShoppingCart = new LinkedList<ShoppingCartItem>();
	
	/*
     * Function:	Client
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	Client Constructor.
	 */
    Client(String name, String phone, String address){
        id = CLIENT_STRING + (ClientIdServer.instance()).getId();
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.balance = 0.00;//will always start with empty balance
    }//end constructor
	
	/*
     * Function:	getBalance
     * Type:		double
     * Privacy:		public
     * Description:	Gets Client Balance.
	 */
	public double getBalance(){
		return balance;
	}//end getBalance
	
	/*
     * Function:	getId
     * Type:		String
     * Privacy:		public
     * Description:	Gets Client ID.
	 */
	public String getId(){
		return id;
	}//end getId
	
	/*
     * Function:	getName
     * Type:		String
     * Privacy:		public
     * Description:	Gets Client Name.
	 */
	public String getName(){
		return name;
	}//end getName
	
	/*
     * Function:	getPhone
     * Type:		String
     * Privacy:		public
     * Description:	Gets Client Phone Number.
	 */
	public String getPhone(){
		return phone;
	}//end getPhone
	
	/*
     * Function:	getAddress
     * Type:		String
     * Privacy:		public
     * Description:	Gets Client Address.
	 */
	public String getAddress(){
		return address;
	}//end getAddress
	
	/*
     * Function:	setBalance
     * Type:		void
     * Privacy:		public
     * Description:	Sets Client Balance.
	 */
	public void setBalance(double balance){
		this.balance = balance;
	}//end setBalance
	
	/*
     * Function:	setId
     * Type:		void
     * Privacy:		public
     * Description:	Sets Client ID.
	 */
	public void setId(String id){
		this.id = id;
	}//end setId
	
	/*
     * Function:	setName
     * Type:		void
     * Privacy:		public
     * Description:	Sets Client Name.
	 */
	public void setName(String name){
		this.name = name;
	}//end setName
	
	/*
     * Function:	setPhone
     * Type:		void
     * Privacy:		public
     * Description:	Sets Client Phone Number.
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}//end setPhone
	
	/*
     * Function:	setAddress
     * Type:		void
     * Privacy:		public
     * Description:	Sets Client Address.
	 */
	public void setAddress(String address){
		this.address = address;
	}//end setAddress
	
	/*
     * Function:	setCartItemQuant
     * Type:		boolean
     * Privacy:		public
     * Description:	Sets new quantity for item in ShoppingCart.
	 */
	public boolean setCartItemQuant(String productId, int quantity) {
		boolean entryFound = false;
		Iterator allCartItems = getCartItems();
		while (allCartItems.hasNext() & !entryFound){
			ShoppingCartItem nextCartItem = (ShoppingCartItem)(allCartItems.next());
			if(nextCartItem.getItem().getId().contentEquals(productId)) { 
				entryFound = true;
				nextCartItem.setQuantity(quantity);
			}
		}
		return entryFound;
	}//end setCartItemQuant
	
	/*
     * Function:	removeCartItem
     * Type:		boolean
     * Privacy:		public
     * Description:	Removes ShoppingCartItem from ShoppingCart.
	 */
	public boolean removeCartItem(String productId) {
		boolean entryFound = false;
		Iterator allCartItems = getCartItems();
		while (allCartItems.hasNext() & !entryFound){
			ShoppingCartItem nextCartItem = (ShoppingCartItem)(allCartItems.next());
			if(nextCartItem.getItem().getId().contentEquals(productId)) { 
				entryFound = true;
				allCartItems.remove();
			}
		}
		return entryFound;
	}//end removeCartItem
	
	/*
     * Function:	insertToCart
     * Type:		boolean
     * Privacy:		public
     * Description:	Inserts Product and quantity into ShoppingCart.
	 */
	public boolean insertToCart(Product product, int quantity) {
		boolean result;
		ShoppingCartItem cartItem = new ShoppingCartItem(product, quantity);
		result = ShoppingCart.add(cartItem);
		return result;
	}//end insertToCart
	
	/*
     * Function:	getCartItems
     * Type:		Iterator
     * Privacy:		public
     * Description:	Returns an iterator for ShoppingCart
	 */
	public Iterator getCartItems(){
		return ShoppingCart.iterator();
	}//end getClients

    /*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts Client to string output.
	 */
    public String toString(){
        return "ID: " + id + " Name: " + name + " Balance: $" + balance + " Phone Number: " + phone + " Address: " + address;
    }//end display
}