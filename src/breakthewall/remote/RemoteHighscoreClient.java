package breakthewall.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import breakthewall.BreakWallConfig;
import breakthewall.remote.RemoteHighscore;

public class RemoteHighscoreClient {

    public RemoteHighscoreClient() {
    	
    }
       
//	public HighscoreEntry[] getHighscore() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	public void overrideLocalHighscore() throws IOException{
		
		try {
		    Registry registry = LocateRegistry.getRegistry(2001);
		    RemoteHighscore stub = (RemoteHighscore) registry.lookup("Hello");
		    String response = stub.sayHello();
		    
			Path FROM = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscoreNetworkPath + BreakWallConfig.highscoreXML);
		    Path TO = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.highscoreXML);
		    //overwrite existing file, if exists
		    CopyOption[] options = new CopyOption[]{
		      StandardCopyOption.REPLACE_EXISTING,
		      StandardCopyOption.COPY_ATTRIBUTES
		    }; 
		    Files.copy(FROM, TO, options);
		    System.out.println("Copy highscore.xml from Network to Local. Done");
		    
		    System.out.println("Ich bin der Client | " + response);
		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}
		
	} 
	
	public void overrideNetworkHighscore() throws IOException{
		try {
		    Registry registry = LocateRegistry.getRegistry(2001);
		    RemoteHighscore stub = (RemoteHighscore) registry.lookup("Hello");
		    String response = stub.sayHello();
		    
		    Path FROM = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.highscoreXML);
			Path TO = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscoreNetworkPath + BreakWallConfig.highscoreXML);
		    //overwrite existing file, if exists
		    CopyOption[] options = new CopyOption[]{
		      StandardCopyOption.REPLACE_EXISTING,
		      StandardCopyOption.COPY_ATTRIBUTES
		    }; 
		    Files.copy(FROM, TO, options);
		    System.out.println("Copy highscore.xml from Local to Network. Done");
		    
		    System.out.println("Ich bin der Client | " + response);
		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}
		
		
	} 
    
}