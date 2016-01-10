package breakthewall.view;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import java.text.SimpleDateFormat;
 

public class UserLoadView extends JPanel implements ActionListener {
   
	static JFrame frame;
    JLabel result;
    String currentPattern;
    private JButton button1;
    private JPanel patternPanel;
 
    public UserLoadView(Document highscoreDocument) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String[] patternExamples = {
        		  "Helmut",
                  "Franz",
                  "Theo",
                  "Günther",
                  "Paul",
                  "Hermann",
                  "Hans"
                 };
 
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
    	 System.out.println(currentPattern);
         
    }
    
    
    public void insertTableRows(Document doc) {            
        Element root = doc.getDocumentElement();
        NodeList list = root.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); ++i) {
            Element e = (Element) list.item(i);
            if (e.getNodeType() == Element.ELEMENT_NODE) {
            	 String[] Namen = {getArticleInfo("name",e)};
//                tableModel.addRow(Namen); 
            	System.out.println(Namen);
            }
        }       
    }
     
    
    public String getArticleInfo(String tagName, Element elem) {    
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
	

	public JButton getButton() {
		return button1;
	}	
    
    
    
    
}    