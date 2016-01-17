package breakthewall;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class to read config data from config.xml document
 * and fit it into game variables
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
import breakthewall.model.BreakWallXML;

public class BreakWallConfigXML {
	
	// instance of BreakWallXML, which contains methods for xml handling
	private static BreakWallXML handleXMLInstance = new BreakWallXML();
	private static Document configDoc = null;
	
	/**
	 * Public methods reads config.xml, stores its data in a Document object
	 * 
	 * @return configDoc Document containing config data
	 */
	public static Document getConfigDocument() {
		if(configDoc == null) {
			File xml = new File(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.configXML);
			if (xml.exists() && xml.length() != 0) {
		    	configDoc = handleXMLInstance.parseFile(xml);
		    }
		}
		return configDoc;
	}
	
	/**
	 * Public method reads common game parameters from config document's "common" section.
	 * Calls methods to associate stored data with game variables 
	 */
	public static void setGeneralConfigurations() {
		getConfigDocument();
		Element root = configDoc.getDocumentElement();
		int levels = root.getElementsByTagName("level").getLength();
		// check for existing levels
		// quit game if no level configurations are stored in document
		if(levels > 0) {
			BreakWallConfig.levelCount = root.getElementsByTagName("level").getLength();	
		} else {
			System.out.println("No levels found in 'config.xml'. Please define levels.");
			System.exit(0);
		}	
		NodeList list = root.getElementsByTagName("common");		
		String valueKey, newValue, valueType; 
		for (int i = 0; i < list.getLength(); ++i) {
			Element e = (Element) list.item(i);
			NodeList paramList = e.getChildNodes();
				for (int j = 0; j < paramList.getLength(); ++j) {
					if(paramList.item(j).getNodeType() == Element.ELEMENT_NODE) {						
						Element parameter = (Element) paramList.item(j);
						valueType = parameter.getAttribute("data-type");
						// call method for complex configuration data like ArrayLists
						if(valueType.equals("ArrayList<String>")) {						
							NodeList elementList = parameter.getElementsByTagName("mediaPath");
							String[] valueArray = new String[elementList.getLength()];
							for (int k = 0; k < elementList.getLength(); ++k) {
								Element subParameter = (Element) elementList.item(k);
								valueArray[k] = subParameter.getTextContent();  					
								
							}
							setComplexConfigValue(parameter.getNodeName(), valueArray);
							// call method for primitive configuration data like int, String	
						} else {
							valueKey = paramList.item(j).getNodeName();
							newValue = parameter.getTextContent().trim(); 					
							setConfigValue(valueType, valueKey, newValue);						
						}
					}

				}
		}
	}
	
	/**
	 * Sets the value of a configuration parameter to the given value
	 * if the data types conform
	 * 
	 * @param valueType	data type of the parameter that should be changes
	 * @param valueKey	string representation of the parameter key
	 * @param newValue	new value of config parameter
	 */
	public static void setConfigValue(String valueType, String valueKey, String newValue) {		
		Object tempNewValue = newValue;
		// cast if value is of type "int"
		if(valueType.equals("Integer")) {
			try {
			tempNewValue = Integer.parseInt(newValue);
			} catch(NumberFormatException e1) {
				System.out.println("Error in config-data! Check attribute value of: " + valueKey);
			}
		}
		try {
			// set new value of config parameter
			BreakWallConfig.class.getField(valueKey).set(null, tempNewValue);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e2) {
			System.out.println("Error in config-data! Check field: " + valueKey);
			e2.printStackTrace();
		}		
	}
	
	/**
	 * Public methods that handles complex parameters separately
	 * 
	 * @param valueKey	data type of parameter to be stored
	 * @param newValues	Object array of new values
	 */
	public static void setComplexConfigValue(String valueKey, Object[] newValues) {
		for(int i = 0; i < newValues.length; i++) {
			if(valueKey.equals("backgroundMusic")) {
				// objects need to be cast to appropriate type 
				String newValue = (String) newValues[i];
				BreakWallConfig.backgroundMusic.add(newValue);
			}			
		}
		
	}
	
	/**
	 * Public method that sets specific parameters which can change
	 * for every level, like image path for background picture
	 * and height of the brick wall
	 * 
	 * @param level the level configurations should be loaded for
	 */
	public static void setLevelConfigurations(int level) {
		getConfigDocument();
		Element root = configDoc.getDocumentElement();
		NodeList list = root.getElementsByTagName("level");
		for (int i = 0; i < list.getLength(); ++i) {
			Element e = (Element) list.item(i);
			if ((e.getNodeType() == Element.ELEMENT_NODE) && (e.getAttribute("number").equals(Integer.toString(level)))) {
				NodeList paramList = e.getElementsByTagName("*");
				for (int j = 0; j < paramList.getLength(); ++j) {
					Element parameter = (Element) paramList.item(j);
					String valueType = parameter.getAttribute("data-type");
					String valueKey = paramList.item(j).getNodeName();
					String newValue = parameter.getTextContent().trim(); 					
					setConfigValue(valueType, valueKey, newValue);					
				}
			}
		}		
	}
}
