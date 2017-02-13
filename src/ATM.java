import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ATM {

    private ATM() {}

	public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            BankInterface stub = (BankInterface) registry.lookup("Hello");
                   

            boolean CLIActive = true;
            boolean login = false;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcom to ATM");
      
            int sessionID = -1;
            int accountNumber = 0;
            int[] loginDetails = new int[2];
            while(!login){
            	
            	// prompt user to enter login information
            	System.out.print("Plese enter Username: ");
            	String username =  scanner.nextLine();
            	System.out.print("Plese enter password: ");
            	String pass = scanner.nextLine();
            	
            	//login method returns sessionID and account number in an integer array (loginDetails)
            	loginDetails =  stub.login(username, pass);
            	
            	//get ID values from the returned array
            	sessionID = loginDetails[0];	
            	accountNumber = loginDetails[1]; 
            	
            	// check if login was successful
            	if(sessionID == -1){
            		System.out.print("Invalid login");
            	}
            	else{
            		System.out.println("Login Sucsessful!!");
            		System.out.println("Enter help to view commands");
            		login = true;
            	}
            }
            
            
            while(CLIActive){
            	
            	
            	//Where login would go 
            	String line = "";
            	System.out.print("Plese enter action: ");
            	line = scanner.nextLine();
            	line.toLowerCase();
            	switch(line){ 
            	
            	case"inquiry":
            		System.out.println("Current balence: "+stub.inquiry(accountNumber, sessionID));
            		line = null;
            		break;
            	
            	case"withdraw":
            		System.out.print("Plese enter an amount to withdraw: ");
            		int am = scanner.nextInt();
            		boolean check = stub.withdraw(accountNumber, am, sessionID);
            		
            		if(!check){
            		System.out.println("Withdrew: "+am);
            		}
            		else{
            			System.out.println("Insufficent Funds!!!");
            		}
            		line = null;
            		break;
            	
            	case"deposit":
            		System.out.print("Plese enter an amount to deposit: ");
            		int amt = scanner.nextInt();
            		stub.deposit(accountNumber, amt, sessionID);
            		System.out.println("Deposited: "+amt);
            		line = null;
            		break;
            		
            	case"statement":
            		ArrayList<Transaction> Statement = new ArrayList<Transaction>();
            		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
            		System.out.println("Plese enter date in dd/MMM/yyy format . Leave dates blank to get all transactions");
            		System.out.println("From Date: ");
            		Date tDate = null;
            		Date fDate = null;
            		String date = null;
            		date = scanner.nextLine();
            		if(!date.isEmpty()){
            			try{
            			fDate = df.parse(date);
            			System.out.println(fDate);
            			}
            			catch(ParseException e ){
            				e.printStackTrace();
            			}
            		}
            		System.out.println("To Date: ");
            		date = null;
            		date = scanner.nextLine();
            		if(!date.isEmpty()){
            			try{
                			tDate = df.parse(date);
                			System.out.println(tDate);
                			}
                			catch(ParseException e ){
                				e.printStackTrace();
                			}
            		}
            		
            		if(!date.isEmpty()){
                    Statement = stub.getStatement(accountNumber, fDate, tDate, sessionID);
            		}
            		else{
            			Statement = stub.getStatement(accountNumber,null,null, sessionID);
            		}
                    if(!Statement.isEmpty()){
                    	for(int i = 0 ; i < Statement.size(); i++){
                    		Transaction t = Statement.get(i);
                    		System.out.println("Action: "+ t.getAction()+ " Date: "+t.getDate().toString()+ " Amount: "+ t.getAmount() + " Balence: "+ t.getBalence());
                    	}
                    }else{
                    	System.out.println("No previous transactions");
                    }
                    line = null;
            		break;
            
            	case "logout":
            		System.out.println("Goodbye");
            		CLIActive = false;
            		scanner.close();
            		break;
       
            	case"help":
            		System.out.println("Available commands:");
            		System.out.println("deposit- To deposit money into your account");
            		System.out.println("withdraw- Take money out your account");
            		System.out.println("inquiry - To view balence from given dates");
            		System.out.println("statement- To view a list of prevous transactions on the account");
            		System.out.println("logout - To logout of ATM");
            		System.out.println("help - To view all availble commands");
            		break;
            	}
            }
            
            
        } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidSession e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLogin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}