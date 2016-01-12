package breakthewall;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import breakthewall.model.BreakWallXML;

public class BreakWallConfigXML {
	
	private static BreakWallXML handleXMLInstance = new BreakWallXML();
	private static Document configDoc = null;
	
	public static Document getConfigDocument() {
		if(configDoc == null) {
			File xml = new File(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.configXML);
			if (xml.exists() && xml.length() != 0) {
		    	configDoc = handleXMLInstance.parseFile(xml);
		    }
		}
		return configDoc;
	}
	
	public static void setGeneralConfigurations() {
		getConfigDocument();
		Element root = configDoc.getDocumentElement();
		int levels = root.getElementsByTagName("level").getLength();
		if(levels > 0) {
			BreakWallConfig.levelCount = root.getElementsByTagName("level").getLength();	
		} else {
			System.out.println("No levels found in 'config.xml'. Please define levels.");	
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
						if(valueType.equals("ArrayList<String>")) {						
							NodeList elementList = parameter.getElementsByTagName("mediaPath");
							String[] valueArray = new String[elementList.getLength()];
							for (int k = 0; k < elementList.getLength(); ++k) {
								Element subParameter = (Element) elementList.item(k);
								valueArray[k] = subParameter.getTextContent();  					
								
							}
							setComplexConfigValue(parameter.getNodeName(), valueArray);
						} else {
							valueKey = paramList.item(j).getNodeName();
							newValue = parameter.getTextContent().trim(); 					
							setConfigValue(valueType, valueKey, newValue);						
						}
					}

				}
		}
	}
	
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
	
	public static void setComplexConfigValue(String valueKey, Object[] newValues) {
		for(int i = 0; i < newValues.length; i++) {
			if(valueKey.equals("backgroundMusic")) {
				String newValue = (String) newValues[i];
				BreakWallConfig.backgroundMusic.add(newValue);
			}			
		}
		
	}
	
	public static void setConfigValue(String valueType, String valueKey, String newValue) {		
		Object tempNewValue = newValue;
		if(valueType.equals("Integer")) {
			try {
			tempNewValue = Integer.parseInt(newValue);
			} catch(NumberFormatException e1) {
				System.out.println("Error in config-data! Check attribute value of: " + valueKey);
			}
		}
		try {
			BreakWallConfig.class.getField(valueKey).set(null, tempNewValue);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e2) {
			System.out.println("Error in config-data! Check field: " + valueKey);
			e2.printStackTrace();
		}		
	}
}
