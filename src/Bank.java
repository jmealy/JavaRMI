    
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
        
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deposit(int accountnum, int amount, long sessionID) throws RemoteException {
		int i = getAccountIndex(accountnum);
		accounts.get(i).setBalance(accounts.get(i).getBalance()+ amount);
	}

	@Override
	public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int inquiry(int accountnum, long sessionID) throws RemoteException {
		int i = getAccountIndex(accountnum);
		return  accounts.get(i).getBalance();
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
