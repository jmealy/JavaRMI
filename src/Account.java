
public class Account {
	
	public int accountNumber;
	private String username;
	private String password;
	private long balance;
	
	public Account(int num, String uname, String pass, long bal){
		accountNumber = num;
		username = uname;
		password = pass;
		balance = bal;
	}
	
	public long getBalance(){
		return this.balance;
	}
	
	public void setBalance(long bal){
		this.balance = bal;
	}
}
