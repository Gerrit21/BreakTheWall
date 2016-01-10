package breakthewall.model;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import breakthewall.BreakWallConfig;
import breakthewall.view.EnterNameView;

import org.w3c.dom.NodeList;
import javax.xml.transform.*; // TransformerFactory, Transformer
import javax.xml.transform.dom.*; // DOMSource
import javax.xml.transform.stream.*; // StreamResult

public class BreakWallXML {
	
	public File xml = null;
	public Document dom = null;
	private BreakWallModel model;
	
	public BreakWallXML() {
		
	}
	
	public BreakWallXML(BreakWallModel model) {
		this.model = model;
	}
	
	/**
	 * Erzeugt ein XML-Dokument
	 * 
	 * @param doc Neu erstelltes XML-Dokument.
	 * @return Modifiziertes XML-Dokument.
	 */
	public void createUserXML(ArrayList<GameElement> brickList) {		
		Element xml;
		Element doctype;
		Element uuserRoot;
		Element usr1, usr2;
		EnterNameView UserEingabe = new EnterNameView(); 
		Document doc = getXMLDocument();
		
		uuserRoot = doc.createElement("breakwall");
		
		int currentScore = model.getScore();
		int currentLevel = model.getLevel();
		int currentLives = model.getLives();
		
		System.out.println(UserEingabe.getTextFromTextBox());
		String namen = UserEingabe.getTextFromTextBox();
		
		System.out.println(currentScore);
	
		doc.appendChild(uuserRoot);

		// User einfuegen:
		usr1 = createUser(doc,
				namen,
				currentLevel, currentLives, currentScore, brickList);
		uuserRoot.appendChild(usr1);
		usr2 = createUser(doc,
				"Helmut",
				3, 3, currentScore, brickList);
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
	 * @param Level Levelnummer.
	 * @param life Life.
	 * @param highscore Highscore.
	 * @return <tt>user></tt>-Element.
	 */
	protected Element createUser(Document doc,
			String name, int Level, int life, int highscore, ArrayList<GameElement> brickList) {
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
		txtLevel = doc.createTextNode(Integer.toString(Level));
		elLevel.appendChild(txtLevel);
		
		
		// Life: Inhalt befindet sich in #PCDATA:
		Element elLife = doc.createElement("life");
		txtLife = doc.createTextNode(Integer.toString(life));
		elLife.appendChild(txtLife);
		
		
		// Highscore: Inhalt befindet sich in #PCDATA:
		Element elHighscore = doc.createElement("highscore");
		txtHighscore = doc.createTextNode(Integer.toString(highscore));
		elHighscore.appendChild(txtHighscore);
		
		usr.appendChild(elName);
		usr.appendChild(elLevel);
		usr.appendChild(elLife);
		usr.appendChild(elHighscore);
		usr.appendChild(brickWallInfo(doc, brickList));
		return usr;
	} // createUser
	
	public Element brickWallInfo(Document doc, ArrayList<GameElement> brickList) {
		Element brickWallInfo, brickWallBrick, brickWallType;
		Element brickWallBonus = null;
		Element brickWallStability = null;
		Element brickWallXPos = null;
		Element brickWallYPos = null;
		brickWallInfo = doc.createElement("brickWall");		
		for(int i = 0; i < brickList.size(); i++) {
			GameElement currentElement = brickList.get(i); 
			brickWallBrick = doc.createElement("brick");
			brickWallType = doc.createElement("type");			
			String brickType = currentElement.getClass().getSimpleName();
			brickWallType.appendChild(doc.createTextNode(brickType));
			if(brickType.equals("BrickNormal")) {
				BrickNormal brick = (BrickNormal) currentElement;
				
				brickWallBonus = doc.createElement("bonus");
				
				brickWallStability = doc.createElement("stability");
				brickWallStability.appendChild(doc.createTextNode(Integer.toString(brick.getStability())));;				

			} else if(brickType.equals("BrickBonus")) {
				BrickBonus brick = (BrickBonus) currentElement;
				
				brickWallBonus = doc.createElement("bonus");
				brickWallBonus.appendChild(doc.createTextNode(brick.getBonusObject().getClass().getSimpleName()));
				
				brickWallStability = doc.createElement("stability");
				brickWallStability.appendChild(doc.createTextNode(Integer.toString(brick.getStability())));				
			}
			
			brickWallXPos = doc.createElement("xPos");
			brickWallXPos.appendChild(doc.createTextNode(Integer.toString(currentElement.getXCoord())));

			brickWallYPos = doc.createElement("yPos");
			brickWallYPos.appendChild(doc.createTextNode(Integer.toString(currentElement.getYCoord())));

			brickWallBrick.appendChild(brickWallType);
			brickWallBrick.appendChild(brickWallBonus);
			brickWallBrick.appendChild(brickWallStability);
			brickWallBrick.appendChild(brickWallXPos);
			brickWallBrick.appendChild(brickWallYPos);			
			brickWallInfo.appendChild(brickWallBrick);
		}
		return brickWallInfo; 
	}
	
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
	
    public Object getTagInfo(String tagName, Element elem) {    
        NodeList list = elem.getElementsByTagName(tagName);
        for (int i = 0; i < list.getLength(); ++i) {
            Node node = (Node) list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Node child = (Node) node.getFirstChild();
                return child.getTextContent().trim();
            }
             
            return null;
        }
     
        return null;
    }

}
