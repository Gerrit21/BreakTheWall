package breakthewall.remote;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
import org.xml.sax.InputSource;

import breakthewall.remote.RemoteHighscore;

public class RemoteHighscoreServer implements RemoteHighscore {
	
	private Document highscoreDoc;
	private File xmlFile;
	private Document dom = null;
	private String serverAnswer;
	boolean isLocked = false;
	private static final String xmlNetworkPath = "src\\breakthewall\\remote\\xml\\";
	private static final String highscoreXML = "highscore.xml";
	
	private String remoteXMLPath = System.getProperty("user.dir") + File.separator + xmlNetworkPath + highscoreXML;
	
	@Override
    public String setRemoteHighscore(String highscoreString) {
		if(isLocked == false) {
			writeXMLDocument(highscoreString, remoteXMLPath);
			serverAnswer = "Highscore list has been written to " + remoteXMLPath;
		} else if(isLocked == true) {
			serverAnswer = "The file " + highscoreXML + " you want to change is currently in use. Please try saving again later.";
		}
    	
    	return serverAnswer;
    }
    
	@Override
    public String getRemoteHighscore() throws RemoteException {
    	System.out.println("Test");
    	highscoreDoc = getHighscoreDocument(remoteXMLPath);
    	String highscoreString = "";
		try {
			highscoreString = getStringFromDocument(highscoreDoc);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
    	return highscoreString;
    }
	
	public String setHighscoreListLock(boolean isLocked) throws RemoteException {
		this.isLocked = isLocked;
		if(isLocked) {
			serverAnswer = highscoreXML + " is locked for writing.";
		} else {
			serverAnswer = highscoreXML + " is unlocked.";
		}
		return serverAnswer; 
	}
	
	public String getHighscoreListLock() throws RemoteException {
		return Boolean.toString(isLocked);
	}
	
	private Document getHighscoreDocument(String fileRef) throws RemoteException {
		dom = null;		
		xmlFile = new File(fileRef);
	    if (xmlFile.exists() && xmlFile.length() != 0) {
	    	dom = parseFile(xmlFile);
	    }
	    return dom;
	}
    
	//creates an instance of a Document object  
    private Document parseFile(File file) {    	
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();     
        dom = (Document) builder.parse(file);
        } catch (Exception e) { e.printStackTrace(); }  
        return dom;
    }
       
	/**
	 * Schreibt das XML-Dokument in eine Datei.
	 * 
	 * @param doc XML-Dokument.
	 * @param filename Name der zu schreibenden XML-Datei.
	 */
	private void writeXMLDocment(Document doc, String filename) {
		System.out.println(filename);
		File file = new File(filename);
		try {
			TransformerFactory transFactory 
			   = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();

			// Source und Result setzen:
			Source source = new DOMSource(doc);
			Result result = new StreamResult(file);

			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			
			// Dokument in Datei speichern:
			transformer.transform(source, result);

		} catch (TransformerException e1) {
			e1.printStackTrace();
		} // catch
	} // writeXMLDocument
	
	public void writeXMLDocument(String xmlString, String filename) {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        Document document = builder.parse(new InputSource(new StringReader(xmlString)));
	        writeXMLDocment(document, filename);
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}
	
	private String getStringFromDocument(Document doc) throws TransformerException {
	    DOMSource domSource = new DOMSource(doc);
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.transform(domSource, result);
	    return writer.toString();
	}
	
    public static void main(String args[]) {
	
		try {
		    RemoteHighscoreServer obj = new RemoteHighscoreServer();
		    RemoteHighscore stub = (RemoteHighscore) UnicastRemoteObject.exportObject(obj, 0);
	
		    // Bind the remote object's stub in the registry
		    Registry registry = LocateRegistry.createRegistry(2001);
		    registry.bind("Highscore", stub);
		    
		    System.err.println("Server ready");
		} catch (Exception e) {
		    System.err.println("Server exception: " + e.toString());
		    e.printStackTrace();
		}
    }
}
