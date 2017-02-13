import java.util.Set; 
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
        
public class Bank implements BankInterface {
	
	private static ArrayList<Account> accounts; // users accounts
	private static ArrayList<Integer> sessions; // users accounts
	private static int currid;
	
    public Bank() {
    	sessions = new ArrayList<Integer>();	// list of active session IDs
    	accounts = new ArrayList<Account>();	// list of accounts in system
    	currid = 0;
    	// add dummy accounts for testing
		accounts.add(new Account(1, "James", "123", 100));
	    accounts.add(new Account(2, "Sean", "321", 10));
	    accounts.add(new Account(3, "Shrek", "123", 400));
    }
       
    public static void main(String args[]) {
        
        try {
            Bank obj = new Bank();
            BankInterface stub = (BankInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /*********
     * @Params:  username and password.
     * It checks to see if username and password are valid
     * It returns an array of length 2 containing the sessionID and account number of client
     *********/
	@Override
	public int[] login(String username, String password) throws RemoteException, InvalidLogin {
		int i = getAccountIndex(username);
		System.out.println("Account num:" + i);
		if (accounts.get(i).checkPassword(password)){
			return new int[]{getSessionID(), accounts.get(i).accountNumber};
		}else{
			throw new InvalidLogin("Wrong Password: " + password);
		}
	}

    /*********
     * A method to generate a unique sessionID and start a session timer
     * Remove the sessionID from the list of valid sessions when timer runs out
     *********/
	private int getSessionID() {
		currid ++;
		int id = currid;
		sessions.add(id);
		new java.util.Timer().schedule( 
    	        new java.util.TimerTask() {
    	            @Override
    	            public void run() {
    	            	// this code is executed when timer runs out.
    	            	System.out.println("removing sessionID:" + id);
    	            	sessions.remove(sessions.indexOf(id));
    	            	cancel();
    	            }
    	        }, 
    	        180*1000 
    	);	
		return id;
	}

    /*********
     * Takes an amount as a p of money as a parameter, and adds it to the user's balance
     *********/
	@Override
	public void deposit(int accountnum, float amount, int sessionID) throws RemoteException, InvalidSession {
		// check if the session is still valid
		if (!sessions.contains(sessionID)){
			throw new InvalidSession("Session has timed out");
		}
		int i = getAccountIndex(accountnum);
		Account ac = accounts.get(i);
		float nBal = ac.getBalance() + amount;
		Date d = new Date();
		ac.setBalance(nBal);
		Transaction tra = new Transaction("Deposit",d,amount,ac.getBalance());
		ac.addTranaction(tra);
		nBal = 0;
	}

    /*********
     * removes the specified amount from the users balance.
     *********/
	@Override
	public boolean withdraw(int accountnum, float amount, int sessionID) throws RemoteException, InvalidSession {
		boolean notEnough = false;
		// check if the session is still valid
		if (!sessions.contains(sessionID)){
			throw new InvalidSession("Session has timed out");
		}
		int i = getAccountIndex(accountnum);
		float nBal = 0;
		Account ac = accounts.get(i);
		if(accounts.get(i).getBalance() < amount){
			notEnough = true;
		}
		else{
			nBal = ac.getBalance() - amount;
			ac.setBalance(nBal);
			ac.addTranaction(new Transaction("Withdraw",new Date(), amount ,ac.getBalance()));
		}
		
		return notEnough;
		
	}

    /*********
     * returns the users current balance
     *********/
	@Override
	public float inquiry(int accountnum, int sessionID) throws RemoteException, InvalidSession {
		// check if the session is still valid
		if (!sessions.contains(sessionID)){
			throw new InvalidSession("Session has timed out");
		}
		int i = getAccountIndex(accountnum);
		return  accounts.get(i).getBalance();
	}
	
    /*********
     * returns a Statement object to the user for the specified dates
     *********/
	@Override
	public ArrayList<Transaction> getStatement(int accountnum,Date from, Date to, int sessionID) throws InvalidSession{
		// check if the session is still valid
		if (!sessions.contains(sessionID)){
			throw new InvalidSession("Session has timed out");
		}
		int i = getAccountIndex(accountnum);
		ArrayList<Transaction> validTransactions = new ArrayList<Transaction>();
		Account ac = accounts.get(i);
		validTransactions = ac.getTransactions();
		if(from != null && to != null){
			for(int a = 0 ; a < validTransactions.size(); a++){
				if(validTransactions.get(a).getDate().before(from) || validTransactions.get(a).getDate().after(to)){
				validTransactions.remove(a);
				}
			}
		}
		
		return validTransactions;
		
		
	}
	
    /*********
     * returns the index of the requested account within the accounts array, based on the given accountnumber
     *********/
	private int getAccountIndex(int accountnum){
		
		int index = 0;
		
		for (int i=0; i<accounts.size() ; i++){
			if (accounts.get(i).accountNumber == accountnum){
				index = i;
			}
		}
		return index;
	}
	
    /*********
     * returns the index of the requested account within the accounts array, based on the given username
     *********/
	private int getAccountIndex(String uname){
		
		int index = 0;
		
		for (int i=0; i<accounts.size() ; i++){
			if (accounts.get(i).username.equals(uname)){
				index = i;
			}
		}
		return index;
	}
}
