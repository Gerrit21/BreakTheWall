package breakthewall.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import breakthewall.remote.RemoteHighscore;

public class RemoteHighscoreClient {

    public RemoteHighscoreClient() {
    	
    }
       
//	public HighscoreEntry[] getHighscore() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void addEntry(int score) {
	
		try {
		    Registry registry = LocateRegistry.getRegistry(2001);
		    RemoteHighscore stub = (RemoteHighscore) registry.lookup("Hello");
		    String response = stub.sayHello();
		    
		    
		    System.out.println("A new highscore entry has been added to the server. NOT. " + Integer.toString(score));
		    System.out.println("Ich bin der Client | " + response);
		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}
		
		
	}
    
}