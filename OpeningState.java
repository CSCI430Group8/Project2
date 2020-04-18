
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
	private static OpeningState instance;
	private OpeningState() {
		super();
		// context = Context.instance();
	}
	
	public static OpeningState instance() {
		if (instance == null) {
			instance = new OpeningState();
		}
		return instance;
	}
	
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
 
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}
	
	private void manager(){
		(Context.instance()).setLogin(Context.IsManager);
		(Context.instance()).changeState(0);
	}
	
	private void clerk(){
		(Context.instance()).setLogin(Context.IsClerk);
		(Context.instance()).changeState(1);
	}

	private void client(){
		boolean clientFound = false;
		String clientId = getToken("Please input the client id: ");
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

	public void process() {
		int command;
		System.out.println("Input 0 to login as Manager\n"+ 
							"Input 1 to login as Clerk\n" +
							"Input 2 to login as Client\n" +
							"Input 3 to exit the system\n");   
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
			System.out.println("Input 0 to login as Manager\n"+ 
							"Input 1 to login as Clerk\n" +
							"Input 2 to login as Client\n" +
							"Input 3 to exit the system\n");  
		}
		(Context.instance()).changeState(3);
	}

	public void run() {
		process();
	}
}