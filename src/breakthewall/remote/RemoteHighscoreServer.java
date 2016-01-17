package breakthewall.remote;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breakthewall.remote.RemoteHighscore;

import java.rmi.server.UnicastRemoteObject;

public class RemoteHighscoreServer implements RemoteHighscore {
	
    public RemoteHighscoreServer() {}

    public String sayHello() {
    	
    	
	return "Ich bin Text vom Server";
    }
	
    public static void main(String args[]) {
	
	try {
	    RemoteHighscoreServer obj = new RemoteHighscoreServer();
	    RemoteHighscore stub = (RemoteHighscore) UnicastRemoteObject.exportObject(obj, 0);

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