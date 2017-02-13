import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String action;
	private Date date;
	private float balence; 
	private float amount;
	
	public Transaction(String ac, Date d,float am,float bal){
		action = ac;
		date = d;
		balence = bal;
		amount = am;
	}
	
	public String getAction(){
		return this.action;
	}
	public Date getDate(){
		return this.date;
	}
	public float getBalence(){
		return this.balence;
	}
	public float getAmount(){
		return this.amount;
	}
}
