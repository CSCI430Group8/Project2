import java.util.*;
import java.text.*;
import java.io.*;
public class Context {
  
	private int currentState;
	private static Warehouse warehouse;
	private static Context context;
	private int currentUser;
	private String userID;
	private BufferedReader reader = new BufferedReader(new 
                                      InputStreamReader(System.in));
	public static final int IsManager = 0;
	public static final int IsClerk = 1;
	public static final int IsClient = 2;
	private WarehouseState[] states;
	private int[][] nextState;
	
	/*
     * Function:	getToken
     * Type:		void
     * Privacy:		public
     * Description:	Gets a string from the user input.
     */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}
  
	/*
     * Function:	yesOrNo
     * Type:		void
     * Privacy:		public
     * Description:	Gets input on a yes or no question.
     */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}
	
	/*
     * Function:	retrieve
     * Type:		void
     * Privacy:		public
     * Description:	Retrieves data from file called WarehouseData and
					sets all of the data into the warehouse database.
     */
	private void retrieve() {
		try {
			Warehouse tempWarehouse = Warehouse.retrieve();
			if (tempWarehouse != null) {
				System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
				warehouse = tempWarehouse;
			} else {
				System.out.println("File doesnt exist; creating new warehouse" );
				warehouse = Warehouse.instance();
			}
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	/*
     * Function:	setLogin
     * Type:		void
     * Privacy:		public
     * Description:	Sets the login type.
     */
	public void setLogin(int code)
	{currentUser = code;}

	/*
     * Function:	setUser
     * Type:		void
     * Privacy:		public
     * Description:	Sets the ID for a client.
     */
	public void setUser(String uID)
	{ userID = uID;}

	/*
     * Function:	getLogin
     * Type:		int
     * Privacy:		public
     * Description:	Gets the login type.
     */
	public int getLogin()
	{ return currentUser;}

	/*
     * Function:	getUser
     * Type:		String
     * Privacy:		public
     * Description:	Gets the ID of the client.
     */
	public String getUser()
	{ return userID;}
	
	/*
     * Function:	Context
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for Context class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				Context can be initialized at a time
     */
	private Context() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieve();
		} else {
			warehouse = Warehouse.instance();
		}
		// set up the FSM and transition table;
		states = new WarehouseState[4];
		states[0] = ManagerMenuState.instance();
		states[1] = ClerkMenuState.instance(); 
		states[2] = ClientMenuState.instance();
		states[3] = OpeningState.instance();
		nextState = new int[4][4];
		nextState[0][0] = -2;nextState[0][1] = 1; nextState[0][2] = -2;nextState[0][3] = 3;
		nextState[1][0] = 0; nextState[1][1] = -2;nextState[1][2] = 2; nextState[1][3] = 3;
		nextState[2][0] = -2;nextState[2][1] = 1; nextState[2][2] = -2;nextState[2][3] = 3;
		nextState[3][0] = 0; nextState[3][1] = 1; nextState[3][2] = 2;nextState[3][3] = -1;
		currentState = 3;
	}

	/*
     * Function:	changeState
     * Type:		void
     * Privacy:		public
     * Description:	Changes the state in the FSM.
     */
	public void changeState(int transition)
	{
		currentState = nextState[currentState][transition];
		if (currentState == -2) {
			System.out.println("Error has occurred"); 
			terminate();
		}
		if (currentState == -1) {
			terminate();
		}
		states[currentState].run();
	}

	/*
     * Function:	terminate
     * Type:		void
     * Privacy:		public
     * Description:	Outputs data into a file called WarehouseData and
					saves it for future use, then exits the system.
     */
	private void terminate()
	{
		if (yesOrNo("Save data?")) {
			if (warehouse.save()) {
				System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
			} else {
				System.out.println(" There has been an error in saving \n" );
			}
		}
		System.out.println(" Process complete. \n "); System.exit(0);
	}

	/*
     * Function:	instance
     * Type:		Context
     * Privacy:		public
     * Description:	This is the singleton for Context, which, if were to
     * 				try an initialize a second Context class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current Context class.
     */
	public static Context instance() {
		if (context == null) {
			context = new Context();
		}
		return context;
	}
	
	/*
     * Function:	process
     * Type:		void
     * Privacy:		public
     * Description:	processes through the system.
     */
	public void process(){
		states[currentState].run();
	}
  
	public static void main (String[] args){
		Context.instance().process(); 
	}


}
