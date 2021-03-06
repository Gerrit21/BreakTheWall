package breakthewall.model;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import breakthewall.BreakWallConfig;
import breakthewall.view.EnterNameView;

import org.w3c.dom.NodeList;
import javax.xml.transform.*; // TransformerFactory, Transformer
import javax.xml.transform.dom.*; // DOMSource
import javax.xml.transform.stream.*; // StreamResult

/**
 *
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallXML {
	
	public File xml = null;
	public Document dom = null;
	private Document doc;
	private BreakWallModel model;
	private boolean booleanNameDuplicate;
	private int userNameCount;
	
	public BreakWallXML() {
		
	}
	
	public BreakWallXML(BreakWallModel model) {
		this.model = model;
		doc = null;
		
	}
	
	/**
	 * Adds XML-Data to highscore.xml
	 * 
	 * @param doc existing XML-Document.
	 * @return modified XML-Dokument.
	 * @throws ParserConfigurationException 
	 */
	public void createUserXML(String userName, ArrayList<GameElement> brickList) throws ParserConfigurationException {		
	
		int currentScore = model.getScore();
		int currentLevel = model.getLevel();
		int currentLives = model.getLives();
				
		File XmlFile = new File(System.getProperty("user.dir") + File.separator + BreakWallConfig.xmlPath + BreakWallConfig.highscoreXML);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
		    try {
		        doc = dBuilder.parse(XmlFile);
		    } catch (SAXException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }

	    doc.getDocumentElement().normalize();

	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());	
		
		// Search user-elements for exisiting names:
		NodeList nList = doc.getElementsByTagName("name");
		userNameCount = 1; 			
		 
		for (int temp = 0; temp < nList.getLength(); temp++) {

		        Element eElement = (Element) nList.item(temp);
				// Searching for Element:
				Text textNode = (Text) eElement.getFirstChild();
				// Read:
				
				if (textNode.getData().contains(userName)) {
					userNameCount++;
					System.out.println(userName + " already exists " + userNameCount);
					booleanNameDuplicate = true;	
				}			
		}
		
		// Name already exists? Then add count to name
		if(booleanNameDuplicate == true) {
			NodeList bwList = doc.getElementsByTagName("breakwall");
			bwList.item(0).appendChild(createUser(doc,
							userName + Integer.toString(userNameCount),
							currentLevel, currentLives, currentScore, brickList));			
		} 
		
		// else: Write new Name
		else {
			NodeList bwList = doc.getElementsByTagName("breakwall");
			bwList.item(0).appendChild(createUser(doc,
							userName,
							currentLevel, currentLives, currentScore, brickList));
		}
		

		writeXMLDocment(doc, System.getProperty("user.dir") + File.separator + BreakWallConfig.xmlPath + BreakWallConfig.highscoreXML);
		System.out.println("Datei '" + BreakWallConfig.highscoreXML + "' wurde erzeugt.");
	}
	
	/** 
	 * @param doc XML-Dokument, to insert a User.
	 * @param name
	 * @param Level
	 * @param life
	 * @param highscore
	 * @param bricklist
	 * @return <tt>user></tt>-Element.
	 */
	protected Element createUser(Document doc,
			String name, int Level, int life, int highscore, ArrayList<GameElement> brickList) {
		Text txtName,
		txtLevel, txtLife, txtHighscore;
		
		Element usr = doc.createElement("user");
		
		// Name:
		Element elName = doc.createElement("name");
		txtName = doc.createTextNode(name);
		elName.appendChild(txtName);
		elName.setNodeValue(name);
		
		
		// Level
		Element elLevel = doc.createElement("level");
		txtLevel = doc.createTextNode(Integer.toString(Level));
		elLevel.appendChild(txtLevel);
		
		
		// Life:
		Element elLife = doc.createElement("life");
		txtLife = doc.createTextNode(Integer.toString(life));
		elLife.appendChild(txtLife);
		
		
		// Highscore:
		Element elHighscore = doc.createElement("highscore");
		txtHighscore = doc.createTextNode(Integer.toString(highscore));
		elHighscore.appendChild(txtHighscore);
		
		usr.appendChild(elName);
		usr.appendChild(elLevel);
		usr.appendChild(elLife);
		usr.appendChild(elHighscore);
		usr.appendChild(brickWallInfo(doc, brickList));
		return usr;
	} 
	
	
	/**
	 * public Element to manage and save brickcount, position, stability, bonus, ...
	 */
	
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
	
    public Document getHighscoreDocument() {
    	return this.doc;
    }
    
    public void setHighscoreDocument(Document doc) {
    	this.doc = doc;
    }
	
	/**
	 * Writes XML-Document File.
	 * 
	 * @param doc XML-Document.
	 * @param filename Name of new XML-File.
	 */
	public void writeXMLDocment(Document doc, String filename) {
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
	
	public String getStringFromDocument(Document doc) throws TransformerException {
	    DOMSource domSource = new DOMSource(doc);
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.transform(domSource, result);
	    return writer.toString();
	}
	
	
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
