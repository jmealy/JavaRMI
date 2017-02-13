import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface BankInterface extends Remote {
    
	public int[] login(String username, String password) throws RemoteException, InvalidLogin;//, InvalidLogin;
	
	public void deposit(int accountnum, float amount, int sessionID) throws RemoteException, InvalidSession; //, InvalidSession;
	
	public boolean withdraw(int accountnum, float amount, int sessionID) throws RemoteException, InvalidSession;
	
	public float inquiry(int accountnum, int sessionID) throws RemoteException, InvalidSession;
	
	public ArrayList<Transaction> getStatement(int accountnum, Date from, Date to, int sessionID) throws RemoteException, InvalidSession;
	
}