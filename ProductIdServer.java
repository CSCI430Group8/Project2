import java.io.*;

public class ProductIdServer implements Serializable {
	private  int idCounter;
	private static ProductIdServer productServer;
	
	/*
     * Function:	ProductIdServer
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	ProductIdServer Constructor.
	 */
	private ProductIdServer() {
		idCounter = 1;
	}//end constructor
	
	/*
     * Function:	instance
     * Type:		ProductIdServer
     * Privacy:		public
     * Description:	This is the singleton for ProductIdServer, which, if were to
     * 				try an initialize a second ProductIdServer class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current ProductIdServer class.
     */
	public static ProductIdServer instance() {
		if (productServer == null) {
			return (productServer = new ProductIdServer());
		} 
		else {
			return productServer;
		}
	}//end instance
	
	/*
     * Function:	getId
     * Type:		int
     * Privacy:		public
     * Description:	Gets Product ID counter.
	 */
	public int getId() {
		return idCounter++;
	}//end getId
	
	/*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts ProductIdServer to string output.
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
			productServer = (ProductIdServer) input.readObject();
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
     * Description:	Writes ProductIdServer to disk.
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(productServer);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end writeObject
	
	/*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads ProductIdServer from disk.
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (productServer == null) {
				productServer = (ProductIdServer) input.readObject();
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