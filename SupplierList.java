import java.text.DecimalFormat;
import java.util.*;
import java.io.*;


public class SupplierList implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedList<Supplier> suppliers = new LinkedList<Supplier>();
	private static SupplierList supplierList;
	
	/*
     * Function:	SupplierList
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	SupplierList Constructor.
	 */
	private SupplierList() {
	}//end constructor
	
	/*
     * Function:	instance
     * Type:		static SupplierList
     * Privacy:		public
     * Description:	Creates an instance of SupplierList.
	 */
	public static SupplierList instance() {
		if (supplierList == null) {
			return (supplierList = new SupplierList());
		} 
		else {
			return supplierList;
		}
	}//end instance
	
	/*
     * Function:	insertSupplier
     * Type:		boolean
     * Privacy:		public
     * Description:	Inserts Supplier into SupplierList.
	 */
	public boolean insertSupplier(Supplier supplier) {
		boolean result;
		result = suppliers.add(supplier);
		return result;
	}//end insertSupplier

	/*
     * Function:	getSupplier
     * Type:		Iterator
     * Privacy:		public
     * Description:	Returns an iterator for SupplierList.
	 */
	public Iterator getSuppliers(){
		return suppliers.iterator();
	}//end getSuppliers

	/*
	 * Function:	setSupplierName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierName(String id, String name){
		Boolean entryFound = false;
		for(int i = 0; !entryFound & i < suppliers.size(); i++){
			if(suppliers.get(i).getId().contentEquals(id)){
				entryFound = true;
				suppliers.get(i).setName(name);
			}//end if
		}//end for
		return entryFound;//product is found and changed
	}//end setSupplierName

	/*
	 * Function:	setSupplierName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierPhone(String id, String phone){
		Boolean entryFound = false;
		for(int i = 0; !entryFound & i < suppliers.size(); i++){
			if(suppliers.get(i).getId().contentEquals(id)){
				entryFound = true;
				suppliers.get(i).setPhone(phone);
			}//end if
		}//end for
		return entryFound;//product is found and changed
	}//end setSupplierPhone

	/*
	 * Function:	setSupplierName
	 * Type:		boolean
	 * Privacy:		public
	 * Description:
	 */
	public boolean setSupplierAddress(String id, String address){
		Boolean entryFound = false;
		for(int i = 0; !entryFound & i < suppliers.size(); i++){
			if(suppliers.get(i).getId().contentEquals(id)){
				entryFound = true;
				suppliers.get(i).setAddress(address);
			}//end if
		}//end for
		return entryFound;//product is found and changed
	}//end setSupplierAddress
  
	/*
     * Function:	writeObject
     * Type:		void
     * Privacy:		private
     * Description:	Writes SupplierList to disk.
     */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(supplierList);
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end writeObject
	
	/*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads SupplierList from disk.
     */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (supplierList != null) {
				return;
			} 
			else {
				input.defaultReadObject();
				if (supplierList == null) {
					supplierList = (SupplierList) input.readObject();
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
     * Description:	Converts SupplierList to string output*/
	public String toString() {
		return suppliers.toString();
	}//end toString
}