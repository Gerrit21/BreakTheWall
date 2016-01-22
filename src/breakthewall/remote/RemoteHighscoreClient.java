package breakthewall.remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import breakthewall.BreakWallConfig;
import breakthewall.model.BreakWallXML;
import breakthewall.remote.RemoteHighscore;

public class RemoteHighscoreClient {
	private BreakWallXML xmlHandler;
	private String localXMLPath, serverResponse;
	private boolean isLocked;
	private Registry registry;
	private RemoteHighscore stub;
	
    public RemoteHighscoreClient() {
    	isLocked = false;
    	try {
    	registry = LocateRegistry.getRegistry(2001);
    	stub = (RemoteHighscore) registry.lookup("Highscore");
		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}    	
    	localXMLPath = BreakWallConfig.xmlPath + BreakWallConfig.highscoreXML;
    	xmlHandler = new BreakWallXML();
    }
	
	public void setLocalHighscore() throws IOException {		
		    // the highscore document is retrieved from the server
		    String highscoreStringResponse = stub.getRemoteHighscore();
		    xmlHandler.writeXMLDocument(highscoreStringResponse, System.getProperty("user.dir") + File.separator + localXMLPath);
		    
			/** Passiert alles nicht zwischen Server und Client, sondern nur lokal! Es wird kein highscore-dokument vom server benutzt
			 * 
		    Path FROM = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscoreNetworkPath + BreakWallConfig.highscoreXML);
		    Path TO = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.xmlPath + BreakWallConfig.highscoreXML);
		    //overwrite existing file, if exists
		    CopyOption[] options = new CopyOption[]{
		      StandardCopyOption.REPLACE_EXISTING,
		      StandardCopyOption.COPY_ATTRIBUTES
		    }; 
		    Files.copy(FROM, TO, options);		    
		    **/
		    System.out.println("Copy highscore.xml from Network to Local. Done");
		    System.out.println("Client message: Highscore has been written to " + System.getProperty("user.dir") + File.separator + localXMLPath);	
	} 
	
	public void setNetworkHighscore() throws IOException {
		Document highscoreDoc = xmlHandler.parseFile(localXMLPath);
	    String xmlString = "";
		try {
			xmlString = xmlHandler.getStringFromDocument(highscoreDoc);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		serverResponse = stub.setRemoteHighscore(xmlString);
	    
	    /**
	    Path FROM = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.xmlPath + BreakWallConfig.highscoreXML);
		Path TO = Paths.get(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscoreNetworkPath + BreakWallConfig.highscoreXML);
	    //overwrite existing file, if exists
	    CopyOption[] options = new CopyOption[]{
	      StandardCopyOption.REPLACE_EXISTING,
	      StandardCopyOption.COPY_ATTRIBUTES
	    }; 
	    Files.copy(FROM, TO, options);
	    **/
	    
	    System.out.println("Copy highscore.xml from Local to Network. Done");		    
	    System.out.println("Server message: " + serverResponse);		
		
	}
	
	public boolean setRemoteHighscoreListLock(boolean lock) throws RemoteException {
		serverResponse = stub.setHighscoreListLock(lock);
		return isLocked;
	}
	
	public boolean getRemoteHighscoreListLock() throws RemoteException {
		serverResponse = stub.getHighscoreListLock();
		isLocked = Boolean.getBoolean(serverResponse);
		return isLocked;
	}
    
}