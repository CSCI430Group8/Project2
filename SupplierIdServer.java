import java.io.*;

public class SupplierIdServer implements Serializable {
	private  int idCounter;
	private static SupplierIdServer supplierServer;
	
	/*
     * Function:	SupplierIdServer
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	SupplierIdServer Constructor.
	 */
	private SupplierIdServer() {
		idCounter = 1;
	}//end constructor
	
	/*
     * Function:	instance
     * Type:		SupplierIdServer
     * Privacy:		public
     * Description:	This is the singleton for SupplierIdServer, which, if were to
     * 				try an initialize a second SupplierIdServer class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current SupplierIdServer class.
     */
	public static SupplierIdServer instance() {
		if (supplierServer == null) {
			return (supplierServer = new SupplierIdServer());
		} 
		else {
			return supplierServer;
		}
	}// end instance
	
	/*
     * Function:	getId
     * Type:		int
     * Privacy:		public
     * Description:	Gets Supplier ID counter.
	 */
	public int getId() {
		return idCounter++;
	}//end getId
	
	/*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts SupplierIdServer to string output.
	 */
	public String toString() {
		return ("IdServer" + idCounter);
	}//end toString
	
	/*
     * Function:	retrieve
     * Type:		void
     * Privacy:		public
     * Description:	Retrieves data from disk to get counter.
     */
	public static void retrieve(ObjectInputStream input) {
		try {
			supplierServer = (SupplierIdServer) input.readObject();
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}//end retrieve
	
	/*
     * Function:	writeObject
     * Type:		void
     * Privacy:		private
     * Description:	Writes SupplierIdServer to disk.
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(supplierServer);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end writeObject
	
	/*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads SupplierIdServer from disk.
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (supplierServer == null) {
				supplierServer = (SupplierIdServer) input.readObject();
			} 
			else {
				input.readObject();
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end readObject
}