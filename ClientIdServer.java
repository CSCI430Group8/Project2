import java.io.*;

public class ClientIdServer implements Serializable {
	private  int idCounter;
	private static ClientIdServer clientServer;
	
	/*
     * Function:	ClientIdServer
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	ClientIdServer Constructor.
	 */
	private ClientIdServer() {
		idCounter = 1;
	}//end constructor
	
	/*
     * Function:	instance
     * Type:		ClientIdServer
     * Privacy:		public
     * Description:	This is the singleton for ClientIdServer, which, if were to
     * 				try an initialize a second ClientIdServer class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current ClientIdServer class.
     */
	public static ClientIdServer instance() {
		if (clientServer == null) {
			return (clientServer = new ClientIdServer());
		} 
		else {
			return clientServer;
		}
	}//end instance
	
	/*
     * Function:	getId
     * Type:		int
     * Privacy:		public
     * Description:	Gets Client ID counter.
	 */
	public int getId() {
		return idCounter++;
	}//end getId
	
	/*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts ClientIdServer to string output.
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
			clientServer = (ClientIdServer) input.readObject();
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
     * Description:	Writes ClientIdServer to disk.
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(clientServer);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end writeObject
	
	/*
     * Function:	readObject
     * Type:		void
     * Privacy:		private
     * Description:	Reads ClientIdServer from disk.
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (clientServer == null) {
				clientServer = (ClientIdServer) input.readObject();
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