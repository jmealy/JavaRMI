import java.util.ArrayList;

public class Account {
	
	public int accountNumber;
	public String username;
	private String password;
	private float balance;
	private ArrayList<Transaction> Transactions = new ArrayList<Transaction>();
	
	public Account(int num, String uname, String pass, float bal){
		accountNumber = num;
		username = uname;
		password = pass;
		balance = bal;
	}
	
	public float getBalance(){
		return this.balance;
	}
	
	public void setBalance(float bal){
		this.balance = bal;
	}
	
	public void addTranaction(Transaction tra){
		this.Transactions.add(tra);
	}
	public ArrayList<Transaction> getTransactions(){
		return this.Transactions;
	}
	
	public boolean checkPassword(String password){
		if (this.password.equals(password)){
			return true;
		}
		return false;
	}
}
