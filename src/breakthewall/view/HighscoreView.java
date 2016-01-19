package breakthewall.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import breakthewall.BreakWallConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class HighscoreView extends JPanel  {
	

	private JPanel xmlHighscore;
	public JScrollPane jScrollPane;
	public DefaultTableModel model;
	public JTable highscoreTable;
	private JButton btnBackMenu;
	private JLabel lblHighscores;
	private JPanel panelTable;
	
	
	public HighscoreView(Document highscoreDocument) {
		
		// Add Panel - Highscorebar without Scrollpane
		
		xmlHighscore = new JPanel();
		xmlHighscore.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, 55));
	        
		xmlHighscore.setBackground(new Color(245,245,245,250));
		xmlHighscore.setLayout(null);
 
        btnBackMenu = new JButton();
   		btnBackMenu.setActionCommand("BackMenu");
   		btnBackMenu.setForeground(new Color (250,250,250));
   		btnBackMenu.setBackground(new Color (250,250,250));
   		btnBackMenu.setSelectedIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
   		btnBackMenu.setIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
   		btnBackMenu.setBounds(10, 11, 50, 33);
   		xmlHighscore.add(btnBackMenu);	
   		
   		lblHighscores = new JLabel("Highscores");
   		lblHighscores.setForeground(Color.DARK_GRAY);
   		lblHighscores.setHorizontalAlignment(SwingConstants.CENTER);
   		lblHighscores.setFont(new Font("Calibri", Font.BOLD, 25));
   		lblHighscores.setBounds(0, 11, 600, 38);
   		xmlHighscore.add(lblHighscores);	
	
		add(xmlHighscore);	
		
		// Add Panel - HighscoreTable with Scrollpane
		
		panelTable = new JPanel();
		add(panelTable);
		
		model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) { return false; }
        };
         
        //creates an instance of the table class and sets it up in a scrollpane
        highscoreTable = new JTable(model);       
        highscoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jScrollPane = new JScrollPane(highscoreTable);  
        jScrollPane.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth-100, 390));
        
        panelTable.add(jScrollPane);
         
        //add some columns
        model.addColumn("Highscore");
        model.addColumn("Name");
        model.addColumn("Level");
        model.addColumn("Life");
	       
	        
        insertTableRows(model, highscoreDocument);	
        
        //creates sorted table 
        
        /**TableRowSorter<TableModel> sorter = new TableRowSorter<>(highscoreTable.getModel());
        highscoreTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
         
        int columnIndexToSort = 0;
        
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();**/
	        
	} 
	
	public int[] sortRow(NodeList list, String rowToSort) {
		int[] sortedHighscoreValues = new int[list.getLength()];
		int[] unsortedHighscoreValues = new int[list.getLength()];
		int[] highscoreIndices = new int[list.getLength()];
		for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (e.getNodeType() == Element.ELEMENT_NODE) {            	  
            	int listValue = Integer.parseInt((String) getArticleInfo(rowToSort,e));
            	unsortedHighscoreValues[i] = listValue;             
            }		
		}
	    for(int i = 0; i < unsortedHighscoreValues.length; i++) {
	    	sortedHighscoreValues[i] = unsortedHighscoreValues[i];
		}
		Arrays.sort(sortedHighscoreValues);
		int sortedCount = sortedHighscoreValues.length - 1;
		int index = 0;
		int count = 0;
		int tempValue = 1000000000;
		while(sortedCount > 0) {
			count = 0;
			while(count < unsortedHighscoreValues.length) {
				if(sortedHighscoreValues[sortedCount] == unsortedHighscoreValues[count]) {
					if(tempValue == sortedHighscoreValues[sortedCount]) {
						sortedCount--;
						index++;
					}
					highscoreIndices[index] = count;					
					tempValue = sortedHighscoreValues[sortedCount];
				}
				count++;				
			}
			sortedCount--;
			index++;
		}	
		return highscoreIndices;
	}
     
    public void insertTableRows(DefaultTableModel tableModel,Document doc) {            
        Element root = doc.getDocumentElement();
        NodeList list = root.getElementsByTagName("user");
        int[] highscoreIndices = sortRow(list, "highscore");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(highscoreIndices[i]);
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
		return btnBackMenu;
	}

	
	
}