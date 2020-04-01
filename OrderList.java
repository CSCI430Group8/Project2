import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;
    private LinkedList<Order> orders = new LinkedList<Order>();
    private static OrderList orderList;
    transient DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    /*
     * Function:	BackorderList
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	BackorderList Constructor.
     */
    private OrderList(){
    }//end constructor

    /*
     * Function:	instance
     * Type:		static BackorderList
     * Privacy:		public
     * Description:	Creates instance of BackorderList.
     */
    public static OrderList instance() {
        if (orderList == null) {
            return (orderList = new OrderList());
        }
        else {
            return orderList;
        }
    }//end instance

    /*
     * Function:	insertOrder
     * Type:		boolean
     * Privacy:		public
     * Description:	Inserts Order into BackorderList.
     */
    public boolean addOrder(String id, LinkedList<ShoppingCartItem> orderedItems) {
        boolean result;
        Order newOrder = new Order(id, orderedItems);
        result = orders.add(newOrder);
        return result;
    }//end insertProduct

    /*
     * Function:	getBackorders
     * Type:		Iterator
     * Privacy:		public
     * Description:	Returns an iterator for BackorderList.
     */
    public Iterator getOrders(){
        return orders.iterator();
    }//end getBackorders

    /*
     * Function:	writeObject
     * Type:		void
     * Privacy:		private
     * Description:	Writes BackorderList to disk.
     */
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(orderList);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }//end writeObject

    /*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads backorderList from disk.
     */
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (orderList != null) {
                return;
            }
            else {
                input.defaultReadObject();
                if (orderList == null) {
                    orderList = (OrderList) input.readObject();
                }
                else {
                    input.readObject();
                }
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }//end readObject

    /*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts ProductList to string output.
     */
    public String toString() {
        return orders.toString();

    }//end toString
}
