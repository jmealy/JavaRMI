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
            
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}