import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set; 
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
        
public class Bank implements BankInterface {
	
	private static ArrayList<Account> accounts; // users accounts
    public Bank() {
    	
    	accounts = new ArrayList<Account>();
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
    
    public String sayHello() {
        return "Hello !";
    }

	@Override
	public long login(String username, String password) throws RemoteException {
		int i = 0;//getAccountIndex(username);
		if (accounts.get(i).checkPassword(password)){
			return 5;//getSessionID();
			
		}else{
			throw new InvalidLogin("Wrong Password: " + password);
		}
	}

	@Override
	public void deposit(int accountnum, int amount, long sessionID) throws RemoteException {
		int i = getAccountIndex(accountnum);
		Account ac = accounts.get(i);
		int nBal = ac.getBalance() + amount;
		Date d = new Date();
		ac.setBalance(nBal);
		Transaction tra = new Transaction("Deposit",d,ac.getBalance());
		ac.addTranaction(tra);
		nBal = 0;
	}

	@Override
	public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException {
		int i = getAccountIndex(accountnum);
		int nBal = 0;
		Account ac = accounts.get(i);
		if(accounts.get(i).getBalance() < amount){
			System.out.println("Insufficent Funds");
		}
		else{
			nBal = ac.getBalance() - amount;
			ac.setBalance(nBal);
			ac.addTranaction(new Transaction("Withdraw",new Date(), ac.getBalance()));
		}
		
	}

	@Override
	public int inquiry(int accountnum, long sessionID) throws RemoteException {
		int i = getAccountIndex(accountnum);
		return  accounts.get(i).getBalance();
	}
	@Override
	public ArrayList<Transaction> getStatement(int accountnum,Date from, Date to, long sessionID){
		int i = getAccountIndex(accountnum);
		ArrayList<Transaction> validTransactions = new ArrayList<Transaction>();
		Account ac = accounts.get(i);
		validTransactions = ac.getTransactions();
		
		for(int a = 0 ; a < validTransactions.size(); a++){
			if(validTransactions.get(a).getDate().before(from) || validTransactions.get(a).getDate().after(to)){
				validTransactions.remove(a);
			}
		}
		
		return validTransactions;
		
		
	}
	
	private int getAccountIndex(int accountnum){
		
		int index = 0;
		
		for (int i=0; i<accounts.size() ; i++){
			if (accounts.get(i).accountNumber == accountnum){
				index = i;
			}
		}
		return index;
	}
}
