package breakthewall.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import breakthewall.BreakWallConfig;
import breakthewall.controller.BreakWallController;
import breakthewall.model.BreakWallModel;
import breakthewall.model.GameElement;

/**
 * Klasse zur Stapelung von Bildelementen auf dem Spielfeld.
 * Enth�lt Methoden, um Bild-Elemente zu erzeugen und zu aktualisieren.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallView extends JFrame implements Observer {
	
	private Observable gameModel = null;
	private BreakWallController controller;
	private static final long serialVersionUID = 1L;
	private JLayeredPane gamePane;
	Map<String, JPanel> gameElements;
	ArrayList<GameElement> displayableElements;
	private int layerCount = 0;
	private NavigationBarView panelbar;
	
	
	/*
	 * Konstruktoraufruf initialisiert das Spielfenster
	 */
	
	public BreakWallView(Observable gameModel, BreakWallController controller) {
		this.gameModel = gameModel;
		gameModel.addObserver(this);		
		this.controller = controller;
		this.addKeyListener(controller);
		this.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
		this.setResizable(false);
		gamePane = new JLayeredPane();
		gameElements = new HashMap<String, JPanel>();
		this.add(gamePane, BorderLayout.CENTER);
		gamePane.setBounds(0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);
		// f�gt eine Hintergrundbild hinzu
		addElementToGameField(BreakWallConfig.bgImagePath, "background", 0, 40, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);
		this.setLocationRelativeTo(getParent());
		this.setTitle(BreakWallConfig.title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setFocusable(true);
		this.setVisible(true);

		panelbar = new NavigationBarView(controller);
		addPanelToGameField(panelbar, 0, 0, BreakWallConfig.barWidth, BreakWallConfig.barHeight);

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
	public void addElementToGameField(String imgPath, String id, int xCoord, int yCoord, int width, int height) {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(null);
		ImgPanel newImage = new ImgPanel(imgPath);
		gameElements.put(id, newPanel);
		newPanel.add(newImage);
		newPanel.setBounds(xCoord, yCoord, width, height);
		newPanel.setOpaque(false);
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
	
	@Override
	public void update(Observable gameModel, Object gameObject) {
		if(gameObject.equals("updateGameElements")) {
			displayableElements = ((BreakWallModel) gameModel).getElementList();
			for(int i = 0; i < displayableElements.size(); i++) {			
				if(gameElements.get(displayableElements.get(i).getId()) != null) {
					if(displayableElements.get(i).getDestroyedState() == false) {
						relocateElement(displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord());
					} else {
						removeElementFromGameField(displayableElements.get(i).getId());
					}
				} else {
					addElementToGameField(displayableElements.get(i).getImage(), displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord(), displayableElements.get(i).getWidth(), displayableElements.get(i).getHeight());
				}

			}			
		}
		if(gameObject.equals("focusGameElements")) {
			System.out.println("focus");
			this.requestFocus();
		}
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
