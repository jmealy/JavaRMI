import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;

public class ATM {

    private ATM() {}

    @SuppressWarnings("deprecation")
	public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            BankInterface stub = (BankInterface) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);

            long sessionID = stub.login("James", "123");              
            System.out.println("sessionID: " + sessionID);
            int bal = stub.inquiry(1, 1);
            System.out.println("balance: " + bal);
            stub.deposit(1, 20, 1);
            System.out.println("deposited 20");
            System.out.println("newbal:" + stub.inquiry(1, 1));
            stub.withdraw(1, 20, 1);
            ArrayList<Transaction> Statement = new ArrayList<Transaction>();
            Date fDate = new Date();
            fDate.setHours(fDate.getHours()-1);
            System.out.println(fDate.getHours()-1);
            Statement = stub.getStatement(1, fDate, new Date(), 1);
            for(int i = 0 ; i < Statement.size(); i++){
            	Transaction t = Statement.get(i);
            	System.out.println("Action: "+ t.getAction()+ " Date: "+t.getDate().toString()+ " Amount: "+ t.getBalence());
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