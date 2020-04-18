
import java.util.*;
import java.text.*;
import java.io.*;
public class OpeningState extends WarehouseState{
	private static final int MANAGER_LOGIN = 0;
	private static final int CLERK_LOGIN = 1;
	private static final int CLIENT_LOGIN = 2;
	private static final int EXIT = 3;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private Context context;
	
	/*
     * Function:	OpeningState
     * Type:		constructor(generic)
     * Privacy:		private
     * Description:	Constructor for OpeningState class. This is made private because
     * 				it is using the singleton methodology to make sure only one
     * 				OpeningState can be initialized at a time
     */
	private static OpeningState instance;
	private OpeningState() {
		super();
	}
	
	/*
     * Function:	instance
     * Type:		OpeningState
     * Privacy:		public
     * Description:	This is the singleton for OpeningState, which, if were to
     * 				try an initialize a second ClientMenuState class it would
     * 				restrict access to the constructor and only return a copy
     *  			of the current OpeninguState class.
     */
	public static OpeningState instance() {
		if (instance == null) {
			instance = new OpeningState();
		}
		return instance;
	}
	
	/*
     * Function:	getCommand
     * Type:		void
     * Privacy:		public
     * Description:	Gets a command for the menu from input.
     */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" ));
				if (value <= EXIT && value >= MANAGER_LOGIN) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}
	
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
     * Function:	manager
     * Type:		void
     * Privacy:		public
     * Description:	Changes the state to the ManagerMenuState.
     */
	private void manager(){
		(Context.instance()).setLogin(Context.IsManager);
		(Context.instance()).changeState(0);
	}
	
	/*
     * Function:	clerk
     * Type:		void
     * Privacy:		public
     * Description:	Changes the state to the ClerkMenuState.
     */
	private void clerk(){
		(Context.instance()).setLogin(Context.IsClerk);
		(Context.instance()).changeState(1);
	}

	/*
     * Function:	client
     * Type:		void
     * Privacy:		public
     * Description:	Changes the state to the ClientMenuState.
     */
	private void client(){
		boolean clientFound = false;
		String clientId = getToken("Please input the Client ID: ");
		Iterator allClients = Warehouse.instance().getClients();
        Client nextClient;
        while(!clientFound & allClients.hasNext()){
            nextClient = (Client)allClients.next();
            if(nextClient.getId().contentEquals(clientId)) {
				clientFound = true;
				(Context.instance()).setLogin(Context.IsClient);
				(Context.instance()).setUser(clientId);      
				(Context.instance()).changeState(2);
			}
		}
		if(!clientFound) 
			System.out.println("Invalid client id.");
	} 
	
	/*
     * Function:	process
     * Type:		void
     * Privacy:		public
     * Description:	processes through the menu options.
     */
	public void process() {
		int command;
		System.out.println("Enter a number between 0 and 3 as explained below:");
		System.out.println("0.) Login as Manager\n"+ 
							"1.) Login as Clerk\n" +
							"2.) Login as Client\n" +
							"3.) Exit the system\n");   
		while ((command = getCommand()) != EXIT) {

			switch (command) {
				case MANAGER_LOGIN:     manager();
										break;
				case CLERK_LOGIN:       clerk();
										break;
				case CLIENT_LOGIN:      client();
										break;
				default:                System.out.println("Invalid choice");
                                
			}	
			System.out.println("0.) Login as Manager\n"+ 
								"1.) Login as Clerk\n" +
								"2.) Login as Client\n" +
								"3.) Exit the system\n");  
		}
		(Context.instance()).changeState(3);
	}
	
	/*
     * Function:	run
     * Type:		void
     * Privacy:		public
     * Description:	Runs the process of the menu.
     */
	public void run() {
		process();
	}
}