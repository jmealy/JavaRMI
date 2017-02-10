import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ATM {

    private ATM() {}

    @SuppressWarnings("deprecation")
	public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            BankInterface stub = (BankInterface) registry.lookup("Hello");
            long sessionID = stub.login("James", "123");
            boolean CLIActive = true;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcom to ATM");
            while(CLIActive){
            	
            	
            	//Where login would go 
            	String line = "";
            	System.out.print("Plese enter action: ");
            	line = scanner.nextLine();
            	line.toLowerCase();
            	switch(line){ 
            	
            	case"inquiry":
            		System.out.println("Current balence: "+stub.inquiry(1, 0));
            		break;
            	
            	case"withdraw":
            		System.out.println("Plese enter an amount to withdraw");
            		int am = scanner.nextInt();
            		stub.withdraw(1, am, sessionID);
            		break;
            	
            	case"deposit":
            		System.out.println("Plese enter an amount to deposit");
            		int amt = scanner.nextInt();
            		stub.deposit(1, amt, sessionID);
            		break;
            		
            	case"statement":
            		ArrayList<Transaction> Statement = new ArrayList<Transaction>();
                    Date fDate = new Date();
                    fDate.setHours(fDate.getHours()-1);
                    Statement = stub.getStatement(1, fDate, new Date(), sessionID);
                    if(!Statement.isEmpty()){
                    	for(int i = 0 ; i < Statement.size(); i++){
                    		Transaction t = Statement.get(i);
                    		System.out.println("Action: "+ t.getAction()+ " Date: "+t.getDate().toString()+ " Amount: "+ t.getAmount() + " Balence: "+ t.getBalence());
                    	}
                    }else{
                    	System.out.println("No previous transactions");
                    }
            		break;
            
            	case "exit":
            		System.out.println("Goodbye");
            		CLIActive = false;
            		scanner.close();
            		break;
            	default:
            		if(line != ""){
            		System.out.println("Command not found");
            		}
            		break;
            	}
            	
            	
            }
       
            //stub.deposit(1, 20, 0);
            
            //stub.withdraw(1, 20, 0);
            
            
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}