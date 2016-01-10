package breakthewall.remote;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breakthewall.remote.aHello;

import java.rmi.server.UnicastRemoteObject;

public class aServer implements aHello {
	
    public aServer() {}

    public String sayHello() {
	return "Ich bin Text vom Server";
    }
	
    public static void main(String args[]) {
	
	try {
	    aServer obj = new aServer();
	    aHello stub = (aHello) UnicastRemoteObject.exportObject(obj, 0);

	    // Bind the remote object's stub in the registry
	    Registry registry = LocateRegistry.createRegistry(2001);
	    registry.bind("Hello", stub);

	    System.err.println("Server ready");
	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}