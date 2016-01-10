package breakthewall.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import breakthewall.remote.aHello;

public class aClient {

    private aClient() {}

    public static void main(String[] args) {

	try {
	    Registry registry = LocateRegistry.getRegistry(2001);
	    aHello stub = (aHello) registry.lookup("Hello");
	    String response = stub.sayHello();
	    System.out.println("Antwort: " + response);
	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}