package breakthewall.view;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import breakthewall.BreakWallConfig;

import java.util.*;
import java.text.SimpleDateFormat;
 

public class UserLoadView extends JPanel  {
   
	
    JLabel result;
    String currentPattern;
    private String[] Namen;
    private JButton btnLoadGame;
    private JButton btnBackMenu;
    private JPanel patternPanel;
    private JLabel lblLoadName;
    private JComboBox<String[]> patternList;
    private ArrayList<JButton> navigationButtonsLoad;
	private String PleaseChoose;
    
	
	/*
	 * Constructor. 
	 * Add JButtons, JTextFields, JCombobox and JLabels to JPanel
	 */	
 
    public UserLoadView(Document highscoreDocument) {
       
    	patternPanel = new JPanel();
    	patternPanel.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
        
    	patternPanel.setBackground(new Color(245,245,245,250));
    	patternPanel.setLayout(null);
	    
    	navigationButtonsLoad = new ArrayList<JButton>();
	    
    	
        getNameXML(highscoreDocument);
        
        String[] patternExamples = getNamen();
 
        currentPattern = patternExamples[0];
        
        //Layout everything.
    	
		lblLoadName = new JLabel("Select your username");
		lblLoadName.setForeground(Color.GRAY);
		lblLoadName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadName.setBounds(231, 139, 138, 14);
		patternPanel.add(lblLoadName);
        
        patternList = new JComboBox(patternExamples);
        patternList.setBounds(231, 164, 138, 33);
        patternList.setEditable(false);
        patternPanel.add(patternList);
		
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.setBounds(231, 208, 138, 33);
		patternPanel.add(btnLoadGame);
		
		btnBackMenu = new JButton();
		btnBackMenu.setActionCommand("BackMenu");
		btnBackMenu.setForeground(new Color (250,250,250));
		btnBackMenu.setBackground(new Color (250,250,250));
		btnBackMenu.setSelectedIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		btnBackMenu.setIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		btnBackMenu.setBounds(10, 11, 50, 33);
		patternPanel.add(btnBackMenu);
        
    	navigationButtonsLoad.add(btnLoadGame);
		navigationButtonsLoad.add(btnBackMenu);
        
        add(patternPanel);
         
    } 
 

	/**
	 * public method to fill JCombobox via highscore.xml
	 */
    
    public void getNameXML(Document doc) { 
    	
    	
    	ArrayList<String> aList = new ArrayList<String>();
    	PleaseChoose = "Please Choose";
    	aList.add(PleaseChoose);
        Element root = doc.getDocumentElement();
        NodeList list = doc.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); ++i) {
        	
        	 NodeList name = root.getElementsByTagName("name");
        	 Element line = (Element) name.item(i);
        	
             aList.add (getCharacterDataFromElement(line));
             
        }    
        
 //		ArrayList to Array konvertieren
        
        String NameList[] = new String[aList.size()];              
		for(int j =0;j<aList.size();j++){
			
		  NameList[j] = aList.get(j);
		}
		
        setNamen(NameList);
    }
     
    /**
	 * public static String to look for xml data  
	 */
    
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
      }
	
    /**
	 * public method to call in BreakWallView and to add ActionListener for MVC
	 */
    
    public ArrayList<JButton> getButtonList() {
		return navigationButtonsLoad;
	}
    
    /**
	 * @return the current NameList from highscoreXML
	 */
    
	public String[] getNamen() {
		return Namen;
	}

	/**
	 * set the current NameList
	 */
	
	public void setNamen(String[] Names) {
		Namen = Names;
	}	
	
	/**
	 * public method to call in MVC
	 */
    
    public JComboBox<String[]> getDropdownMenu() {
		return patternList;
    	
    }
    
    
}    