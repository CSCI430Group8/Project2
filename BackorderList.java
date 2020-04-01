import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class BackorderList implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedList<Order> backorders = new LinkedList<Order>();
	private static BackorderList backorderList;
	transient DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	/*
     * Function:	BackorderList
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	BackorderList Constructor.
	 */
	private BackorderList(){
	}//end constructor
	
	/*
     * Function:	instance
     * Type:		static BackorderList
     * Privacy:		public
     * Description:	Creates instance of BackorderList.
	 */
	public static BackorderList instance() {
		if (backorderList == null) {
			return (backorderList = new BackorderList());
		}
		else {
			return backorderList;
		}
	}//end instance
	
	/*
     * Function:	insertOrder
     * Type:		boolean
     * Privacy:		public
     * Description:	Inserts Order into BackorderList.
	 */
	public boolean addBackorder(String id, LinkedList<ShoppingCartItem> orderedItems) {
		boolean result;
		Order newOrder = new Order(id, orderedItems);
		result = backorders.add(newOrder);
		return result;
	}//end insertProduct

	/*
     * Function:	getBackorders
     * Type:		Iterator
     * Privacy:		public
     * Description:	Returns an iterator for BackorderList.
	 */
	public Iterator getBackorders(){
		return backorders.iterator();
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
			output.writeObject(backorderList);
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
			if (backorderList != null) {
				return;
			} 
			else {
				input.defaultReadObject();
				if (backorderList == null) {
					backorderList = (BackorderList) input.readObject();
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
		return backorders.toString();

	}//end toString
}
