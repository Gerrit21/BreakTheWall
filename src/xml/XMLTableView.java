package xml;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
 
import java.awt.*;
import java.io.File;
import java.awt.event.*;
 
public class XMLTableView extends JFrame {
    public static void main(String[] args) {
        new XMLTableView();
    }
     
    public File xml = null;
    public Document dom = null; 
    public JScrollPane jScrollPane1;
    public DefaultTableModel model;
    public JTable jTable1;
     
    public XMLTableView() {
        setTitle("XMLTableModel");
        setSize(600,135);
        xml = new File(System.getProperty("user.dir") + File.separator + "breakwall.xml");        
        installGUI();       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        setVisible(true);       
    }
     
    public void installGUI() {
        Container ctr = getContentPane();
        ctr.setLayout(new BorderLayout());
         
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) { return false; }
        };
         
        //creates an instance of the table class and sets it up in a scrollpane
        jTable1 = new JTable(model);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1 = new JScrollPane(jTable1);            
        ctr.add(BorderLayout.CENTER,jScrollPane1);
         
        //add some columns
        model.addColumn("Name");
        model.addColumn("Level");
        model.addColumn("Life");
        model.addColumn("Highscore");
                 
        //if the xml file exists at the current location in the default user directory
        //then parse the xml data with the parseFile(File file) method      
        if (xml.exists() && xml.length() != 0) {
            dom = parseFile(xml);
            insertTableRows(model,dom);
        }
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
     
    public void insertTableRows(DefaultTableModel tableModel,Document doc) {            
        Element root = doc.getDocumentElement();
        NodeList list = root.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); ++i) {
            Element e = (Element) list.item(i);
            if (e.getNodeType() == Element.ELEMENT_NODE) {
                Object[] row = { getArticleInfo("name",e),getArticleInfo("level",e),getArticleInfo("life",e),getArticleInfo("highscore",e) };
                tableModel.addRow(row);             
            }
        }       
         
        tableModel.fireTableStructureChanged(); 
        tableModel.fireTableDataChanged();
    }
     
    public Object getArticleInfo(String tagName, Element elem) {    
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