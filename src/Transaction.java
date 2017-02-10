import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String action;
	private Date date;
	private int balence;
	private int amount;
	
	public Transaction(String ac, Date d,int amt, int bal){
		action = ac;
		date = d;
		balence = bal;
		amount = amt;
	}
	
	public String getAction(){
		return this.action;
	}
	public Date getDate(){
		return this.date;
	}
	public int getBalence(){
		return this.balence;
	}
	public int getAmount(){
		return this.amount;
	}
}
