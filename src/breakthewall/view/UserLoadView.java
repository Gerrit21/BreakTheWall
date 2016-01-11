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

import java.util.*;
import java.text.SimpleDateFormat;
 

public class UserLoadView extends JPanel implements ActionListener {
   
	
    JLabel result;
    String currentPattern;
    private String[] Namen;
    private JButton button1;
    private JButton button2;
    private JPanel patternPanel;
    private ArrayList<JButton> navigationButtonsLoad;
    
 
    public UserLoadView(Document highscoreDocument) {
       
    	
    	navigationButtonsLoad = new ArrayList<JButton>();
    	
    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        
        getNameXML(highscoreDocument);
        
        String[] patternExamples = getNamen();
 
        currentPattern = patternExamples[0];
        
       
        
        //Set up the UI for selecting a pattern.
        JLabel patternLabel1 = new JLabel("Select your Username");
       
 
        JComboBox patternList = new JComboBox(patternExamples);
        patternList.setEditable(true);
        patternList.addActionListener(this);
 
        //Create the UI for displaying result.
        JLabel resultLabel = new JLabel("Auswahl",
                                        JLabel.LEADING); //== LEFT
        result = new JLabel(" ");
        result.setForeground(Color.black);
        result.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(Color.black),
             BorderFactory.createEmptyBorder(5,5,5,5)
        ));
 
        //Lay out everything.
        patternPanel = new JPanel();
        patternPanel.setPreferredSize(new Dimension(0, 0));
        
        button1 = new JButton ("BackMenu");	
        patternPanel.add(button1);
        button2 = new JButton ("Load Game");
        patternPanel.add(button2);
        
    	navigationButtonsLoad.add(button1);
		navigationButtonsLoad.add(button2);
        
        patternPanel.add(patternLabel1);
        patternPanel.add(patternList);
 
        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultPanel.add(result);
 
 
        add(patternPanel,BorderLayout.CENTER);
        add(resultPanel,BorderLayout.CENTER);
        
      
        
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        ausgeben();
        
      
        
        
        
    } //constructor
 
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String newSelection = (String)cb.getSelectedItem();
        currentPattern = newSelection;
        ausgeben();
    }
 
    
    public void ausgeben() {
    	 result.setText(currentPattern);
    	 //System.out.println(currentPattern);
         
    }
    
    
    public void getNameXML(Document doc) { 
    	
    	
    	ArrayList<String> aList = new ArrayList<String>();
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
     
    
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
      }
	

    public ArrayList<JButton> getButtonList() {
		return navigationButtonsLoad;
	}

	public String[] getNamen() {
		return Namen;
	}

	public void setNamen(String[] Names) {
		Namen = Names;
	}	
    
    
    
    
}    