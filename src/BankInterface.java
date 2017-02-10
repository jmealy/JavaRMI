import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface BankInterface extends Remote {
    String sayHello() throws RemoteException;
    
	public long login(String username, String password) throws RemoteException, InvalidLogin;//, InvalidLogin;
	
	public void deposit(int accountnum, float amount, long sessionID) throws RemoteException, InvalidSession; //, InvalidSession;
	
	public void withdraw(int accountnum, float amount, long sessionID) throws RemoteException, InvalidSession;
	
	public float inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession;
	
	public ArrayList<Transaction> getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession;
	
}