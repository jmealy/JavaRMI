import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ATM {

    private ATM() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            BankInterface stub = (BankInterface) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
            
            long sessionID = stub.login("James", "123");
            int bal = stub.inquiry(1, 0);
            System.out.println("balance: " + bal);
            stub.deposit(1, 20, 0);
            System.out.println("deposited 20");
            System.out.println("newbal:" + stub.inquiry(1, 0));
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}