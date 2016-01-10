package breakthewall.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import breakthewall.controller.BreakWallController;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;




import java.util.List;






public class HighscoreView extends JPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel xmlHighscore;
	private JButton button1;
 
	public JScrollPane jScrollPane1;
	public DefaultTableModel model;
	public JTable jTable1;
	
	
	
	public HighscoreView(Document highscoreDocument) {
		
		
		xmlHighscore = new JPanel();
		
        xmlHighscore.setPreferredSize(new Dimension(120, 500));
        xmlHighscore.setVisible(true);
        xmlHighscore.setOpaque(false);	
	
		button1 = new JButton ("BackMenu");		
		
		xmlHighscore.add(button1);
	
				
		add(xmlHighscore,BorderLayout.CENTER);	
		
		
			model = new DefaultTableModel() {
	            public boolean isCellEditable(int row, int column) { return false; }
	        };
	         
	        //creates an instance of the table class and sets it up in a scrollpane
	        jTable1 = new JTable(model);       
	        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        jScrollPane1 = new JScrollPane(jTable1);            
	        add(BorderLayout.CENTER,jScrollPane1);
	         
	        //add some columns
	        model.addColumn("Highscore");
	        model.addColumn("Name");
	        model.addColumn("Level");
	        model.addColumn("Life");
	       
	        
	        insertTableRows(model, highscoreDocument);	
	        
	        //creates sorted table 
	        
	        TableRowSorter<TableModel> sorter = new TableRowSorter<>(jTable1.getModel());
	        jTable1.setRowSorter(sorter);
	        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
	         
	        int columnIndexToSort = 0;
	        
	        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
	        sorter.setSortKeys(sortKeys);
	        sorter.sort();
	        
	        
	        
	        
	 
	        
	} 
     
    public void insertTableRows(DefaultTableModel tableModel,Document doc) {            
        Element root = doc.getDocumentElement();
        NodeList list = root.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); ++i) {
            Element e = (Element) list.item(i);
            if (e.getNodeType() == Element.ELEMENT_NODE) {
                Object[] row = { getArticleInfo("highscore",e), getArticleInfo("name",e),getArticleInfo("level",e),getArticleInfo("life",e)};
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
	
	public JButton getButton() {
		return button1;
	}

	
	
}
