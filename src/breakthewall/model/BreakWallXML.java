package breakthewall.model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import breakthewall.BreakWallConfig;

import org.w3c.dom.NodeList;
import javax.xml.transform.*; // TransformerFactory, Transformer
import javax.xml.transform.dom.*; // DOMSource
import javax.xml.transform.stream.*; // StreamResult

public class BreakWallXML {
	
	public File xml = null;
	public Document dom = null;
	
	/**
	 * Erzeugt ein XML-Dokument
	 * 
	 * @param doc Neu erstelltes XML-Dokument.
	 * @return Modifiziertes XML-Dokument.
	 */
	public void createUserXML() {		
		Element xml;
		Element doctype;
		Element uuserRoot;
		Element usr1, usr2;
		
		Document doc = getXMLDocument();
		
		uuserRoot = doc.createElement("breakwall");
		
		doc.appendChild(uuserRoot);

		// User einfuegen:
		usr1 = createUser(doc,
				"Peter",
				"2", "4", "1234");
		uuserRoot.appendChild(usr1);
		usr2 = createUser(doc,
				"Helmut",
				"3", "3", "4321");
		uuserRoot.appendChild(usr2);

		
		
		// Highscore ersetzen:
		// Neuer Textknoten:
		Text newTextNode = doc.createTextNode("5555");
		
		// highscore-Elemente im Dokument suchen:
		NodeList nList = doc.getElementsByTagName("highscore");
		// Alle highscore-Elemente durchsuchen:
		for (int i=0; i<nList.getLength(); i++) {
			Element el = (Element) nList.item(i);
			// Text-Knoten ermitteln:
			Text oldTextNode = (Text) el.getFirstChild();
			// Text-Knoten ggf. ersetzen:
			if (oldTextNode.getData().equals("4321")) {
				el.replaceChild(newTextNode, oldTextNode);
			} // if
		} // for
		
		
		writeXMLDocment(doc, System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.highscoreXML);
		System.out.println("Datei '" + BreakWallConfig.highscoreXML + "' wurde erzeugt.");
	} // generateXML
	
	/** 
	 * @param doc XML-Dokument, in das eine User eingef&uuml;gt werden soll.
	 * @param name Spitzname.
	 * @param level Levelnummer.
	 * @param life Life.
	 * @param highscore Highscore.
	 * @return <tt>user></tt>-Element.
	 */
	protected Element createUser(Document doc,
			String name, String level, String life, String highscore) {
		Text txtName,
		txtLevel, txtLife, txtHighscore;
		
		Element usr = doc.createElement("user");
		
		// Name: Inhalt befindet sich in #PCDATA:
		Element elName = doc.createElement("name");
		txtName = doc.createTextNode(name);
		elName.appendChild(txtName);
		elName.setNodeValue(name);
		
		
		// Level ist #PCDATA:
		Element elLevel = doc.createElement("level");
		txtLevel = doc.createTextNode(level);
		elLevel.appendChild(txtLevel);
		
		
		// Life: Inhalt befindet sich in #PCDATA:
		Element elLife = doc.createElement("life");
		txtLife = doc.createTextNode(life);
		elLife.appendChild(txtLife);
		
		
		// Highscore: Inhalt befindet sich in #PCDATA:
		Element elHighscore = doc.createElement("highscore");
		txtHighscore = doc.createTextNode(highscore);
		elHighscore.appendChild(txtHighscore);
		
		usr.appendChild(elName);
		usr.appendChild(elLevel);
		usr.appendChild(elLife);
		usr.appendChild(elHighscore);
		return usr;
	} // createUser
	
	public Document parseFile(String fileRef) {
		xml = new File(System.getProperty("user.dir") + File.separator + fileRef);
        // if the xml file exists at the current location in the default user directory
        // then parse the xml data with the parseFile(File file) method      
        if (xml.exists() && xml.length() != 0) {
            dom = parseFile(xml);
        }		
		return dom;
	}
	
	//creates an instance of a Document object  
    public Document parseFile(File file) {    	
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
	public void writeXMLDocment(Document doc, String filename) {
		System.out.println(filename);
		File file = new File(filename);

		try {

			TransformerFactory transFactory 
			   = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();

			// Source und Result setzen:
			Source source = new DOMSource(doc);
			Result result = new StreamResult(file);

			// NEU
			// Eigenschaften setzen
			//transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
			// transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 1.1//EN");
			//transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "breakwall");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			
			// Dokument in Datei speichern:
			transformer.transform(source, result);

		} catch (TransformerException e1) {
			e1.printStackTrace();
		} // catch
	} // writeXMLDocument

	/**
	 * Erstellung eines neuen XML-Dokuments als Instanz der Klasse <tt>Document</tt>.
	 * @return <tt>Document</tt>-Objekt als Repr&auml;sentant eines XML-Dokuments.
	 */
	public Document getXMLDocument() {
		Document document = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// XML-Dokument erstellen
			document = builder.newDocument();
			document.setXmlStandalone(true);
		}
		catch (ParserConfigurationException e) {
			System.err.println("Fehler beim Erstellen "
					+" des XML-Dokuments!");
			e.printStackTrace();
		} // catch
		
		return document;
	} // getXMLDocument

}
