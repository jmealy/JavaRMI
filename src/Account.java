
public class Account {
	
	public int accountNumber;
	private String username;
	private String password;
	private int balance;
	
	public Account(int num, String uname, String pass, int bal){
		accountNumber = num;
		username = uname;
		password = pass;
		balance = bal;
	}
	
	public int getBalance(){
		return this.balance;
	}
	
	public void setBalance(int bal){
		this.balance = bal;
	}
}
