package breakthrough;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Klasse zur Stapelung von Bildelementen auf dem Spielfeld.
 * Enth�lt Methoden, um Bild-Elemente zu erzeugen und zu aktualisieren.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLayeredPane gamePane;
	Map<String, JPanel> gameElements;
	private int layerCount = 0;
	private NavigationBar panelbar;
	
	
	/*
	 * Konstruktoraufruf initialisiert das Spielfenster
	 */
	
	public BreakWallView() {		
		this.setPreferredSize(new Dimension(BreakWallData.gameFieldWidth, BreakWallData.gameFieldHeight));
		this.setResizable(false);
		gamePane = new JLayeredPane();
		gameElements = new HashMap<String, JPanel>();
		this.add(gamePane, BorderLayout.CENTER);
		gamePane.setBounds(0, 0, BreakWallData.gameFieldWidth, BreakWallData.gameFieldHeight);
		// f�gt eine Hintergrundbild hinzu
		addElementToGameField(BreakWallData.bgImagePath, "", 0, 40, BreakWallData.gameFieldWidth, BreakWallData.gameFieldHeight);
		this.setLocationRelativeTo(getParent());
		this.setTitle(BreakWallData.title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setFocusable(true);
		this.setVisible(true);

		panelbar = new NavigationBar();
		addPanelToGameField(panelbar, 0, 0, BreakWallData.barWidth, BreakWallData.barHeight);

	}
	
	public void addPanelToGameField(JComponent newComp, int xCoord, int yCoord, int width, int height) {
		newComp.setBounds(xCoord, yCoord, width, height);
		newComp.setOpaque(true);
		// layerCount: Bildebene des Elements
		// der Hintergrund hat die Ebene 0
		gamePane.add(newComp, new Integer(layerCount), 0);
		layerCount++;
		gamePane.validate();
	}
	
	/**
	 * F�gt ein Bildelement einem JPanel und dieses dem Spielfeld hinzu.
	 * Bildelemente werden auf dem Spielfeld gestapelt.
	 * Die Element-Referenz wird in der HashMap gameElements gespeichert,
	 * um sie ggf. aktualisieren zu k�nnen
	 * 
	 * @param imgPath Bildpfad des zu zeichnenden Elements
	 * @param xCoord x-Position des zu zeichnenden Elements
	 * @param yCoord y-Position des zu zeichnenden Elements
	 * @param width Breite des zu zeichnenden Elements
	 * @param height H�he des zu zeichnenden Elements
	 */
	public void addElementToGameField(String imgPath, String optionalId, int xCoord, int yCoord, int width, int height) {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(null);
		ImgPanel newImage = new ImgPanel(imgPath);		
		newPanel.add(newImage);
		newPanel.setBounds(xCoord, yCoord, width, height);
		newPanel.setOpaque(false);
		// optionale Id oder Bildpfad als Referenz benutzen
		if(optionalId == "") {
			gameElements.put(imgPath, newPanel);
		} else {
			gameElements.put(optionalId, newPanel);
		}
		// layerCount: Bildebene des Elements
		// der Hintergrund hat die Ebene 0
		gamePane.add(newPanel, new Integer(layerCount), 0);
		layerCount++;
		gamePane.validate();
	}
	
	public void removeElementFromGameField(String elementId) {
		int removeIndex = gamePane.getIndexOf(gameElements.get(elementId));
		gamePane.remove(removeIndex);
		gamePane.revalidate();
		gamePane.repaint();
	}
	
	/**
	 * Ver�ndert die Position eines vorhandenen Elements auf dem Spielfeld.
	 * 
	 * @param imgPath Bildpfad des zu verschiebenden Elements
	 * @param xCoord neue x-Position des Elements
	 * @param yCoord neue y-Position des Elements
	 */
	
	public void relocateElement(String elementId, int xCoord, int yCoord) {
		JPanel redrawnElement = gameElements.get(elementId);
		redrawnElement.setLayout(null);
		redrawnElement.setLocation(xCoord, yCoord);
	}
	
	public void redrawElement(String elementId, int xCoord, int yCoord, int width, int height) {
		JPanel redrawnElement = gameElements.get(elementId);
		redrawnElement.setLayout(null);
		redrawnElement.setBounds(xCoord, yCoord, width, height);
	}
	
	/**
	 * Private Klasse zur Erzeugung eines Objekts vom Typ ImgPanel. 
	 * @author Mareike R�ncke, Gerrit Schulte
	 *
	 */
	
	private class ImgPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image img;
		private int x;
		private int y;
		
		/*
		 *  Konstruktoren erzeugen ein Objekt vom Typ Image
		 */

		  public ImgPanel(String img) {
		    this(new ImageIcon(img).getImage());
		    this.x = 0;
		    this.y = 0;
		  }	  

		  public ImgPanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }  

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, x, y, null);	    
		  }		
	}

}
