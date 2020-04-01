import java.text.DecimalFormat;
import java.util.*;
import java.io.*;


class ShoppingCartItem implements Serializable {
    private Product item;
    private int quantity;
        
    /*
     * Function:    ShoppingCartItem
     * Type:        constructor(generic)
     * Privacy:        public
     * Description:    Client Constructor.
     */
    ShoppingCartItem(Product item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }//end constructor
        
    /*
     * Function:    getBalance
     * Type:        Product
     * Privacy:        public
	 * Description:    Gets Shopping Cart Item.
     */
    public Product getItem(){
        return item;
    }//end getItem

    /*
     * Function:    getQuantity
     * Type:        int
     * Privacy:        public
     * Description:    Gets Shopping Cart Item Quantity.
     */
    public int getQuantity(){
        return quantity;
    }//end getQuantity
        
    /*
     * Function:    setItem
     * Type:        void
     * Privacy:        public
     * Description:    Sets Shopping Cart Item.
     */
    public void setItem(Product item){
        this.item = item;
    }//end setItem

    /*
     * Function:    setQuantity
     * Type:        void
     * Privacy:        public
     * Description:    Sets Shopping Cart Item Quantity.
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }//end setId
        
    /*
     * Function:    toString
     * Type:        String
     * Privacy:        public
     * Description:    Converts ShoppingCartItem to string output.
     */
    public String toString(){
        return "Name: " + item.getName() + " Quantity: " + quantity;
    }//end display
}