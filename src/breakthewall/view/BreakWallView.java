package breakthewall.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.w3c.dom.Document;

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
	private MenuView pauseMenu;
	private HighscoreView showHighscore;
	private UserLoadView showUserLoad;
	private EnterNameView showEnterName;
	private JLabel gameInfo;
	
	
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
		this.setLocationRelativeTo(getParent());
		this.setTitle(BreakWallConfig.title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setFocusable(true);
		this.setVisible(true);
		buildGameLayout();
	}

	public void buildGameLayout() {
		// f�gt eine Hintergrundbild hinzu
		addElementToGameField(BreakWallConfig.bgImagePath, "background", 0, 40, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);
		panelbar = new NavigationBarView();
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationButtons = panelbar.getButtonList();
		for(int i = 0; i < navigationButtons.size(); i++) {
			navigationButtons.get(i).addActionListener(controller);
		}
		
		addPanelToGameField(panelbar, 0, 0, BreakWallConfig.barWidth, BreakWallConfig.barHeight);

		gameInfo = new JLabel("Spielhinweise");
		gameInfo.setBackground(new Color(139, 223, 113));
		gameInfo.setBorder(BorderFactory.createLineBorder(new Color(100, 158, 82)));
		addPanelToGameField(gameInfo, 0, (BreakWallConfig.offsetHeight - 42), BreakWallConfig.offsetWidth + 10, 20);
		
	}
	
	public void buildMenuLayout() {		
		pauseMenu = new MenuView();
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationMenuButtons = pauseMenu.getButtonList();
		for(int i = 0; i < navigationMenuButtons.size(); i++) {
			navigationMenuButtons.get(i).addActionListener(controller);
		}
		gameElements.put("mainMenu", pauseMenu);
		addPanelToGameField(pauseMenu, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	public void buildHighscoreLayout(Document highscoreDocument) {		
		showHighscore = new HighscoreView(highscoreDocument);
		
		// add ActionListener to navigation buttons
		showHighscore.getButton().addActionListener(controller);
				
		
		gameElements.put("mainHighscore", showHighscore);
		addPanelToGameField(showHighscore, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	public void buildUserSelectLayout(Document highscoreDocument) {		
		showUserLoad = new UserLoadView(highscoreDocument);
		
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationButtonsLoad = showUserLoad.getButtonList();
		for(int i = 0; i < navigationButtonsLoad.size(); i++) {
			navigationButtonsLoad.get(i).addActionListener(controller);
		}
				
		gameElements.put("mainHighscore", showUserLoad);
		addPanelToGameField(showUserLoad, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	public void buildEnterNameLayout() {		
		showEnterName = new EnterNameView();
		
		// add ActionListener to navigation buttons
		showEnterName.getButton().addActionListener(controller);
				
		
		gameElements.put("mainEnterName", showEnterName);
		addPanelToGameField(showEnterName, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	public void editGameInfo(String newText) {
		gameInfo.setText(newText);
	}
	
	public void clearGameField() {
		gamePane.removeAll();
		gameElements.clear();
		layerCount = 0;
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
			BreakWallModel currentModel = (BreakWallModel) gameModel;
			displayableElements = currentModel.getElementList();
			for(int i = 0; i < displayableElements.size(); i++) {			
				if(gameElements.get(displayableElements.get(i).getId()) != null) {
					if(displayableElements.get(i).getDestroyedState() == false) {
						redrawElement(displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord(), displayableElements.get(i).getWidth(), displayableElements.get(i).getHeight());
					} else {
						removeElementFromGameField(displayableElements.get(i).getId());
					}
				} else {
					addElementToGameField(displayableElements.get(i).getImage(), displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord(), displayableElements.get(i).getWidth(), displayableElements.get(i).getHeight());
				}

			}
			editGameInfo(currentModel.getInfoText());
			panelbar.updateScoreView(currentModel.getScore());
			panelbar.updateLevelView(currentModel.getLevel());
			panelbar.updateLifeView(currentModel.getLives());
			//pauseMenu.updateScoreMenuView(currentModel.getScore());
		
		}
		if(gameObject.equals("focusGameElements")) {
			panelbar.setPlayPauseButton("Pause");
			this.requestFocus();
		}
		if(gameObject.equals("stopGame")) {
			panelbar.setPlayPauseButton("Play");
		}	
		if(gameObject.equals("playGame")) {
			panelbar.setPlayPauseButton("Pause");
		}		
		if(gameObject.equals("initLevel")) {
			clearGameField();
			controller.initNewLevel();
		}
		if(gameObject.equals("updateLevel")) {
			buildGameLayout();
		}
		if(gameObject.equals("loadLevel")) {
			clearGameField();
			controller.initExistingLevel();
			buildGameLayout();
		}		
		if(gameObject.equals("showMenu")) {
			buildMenuLayout();	
		}
		if(gameObject.equals("quitMenu")) {		
			removeElementFromGameField("mainMenu");
		}	
		
		if(gameObject.equals("showHighscore")) {
			Document highscoreDocument = ((BreakWallModel) gameModel).getHighscoreDocument();
			buildHighscoreLayout(highscoreDocument);			
		}	
		
		if(gameObject.equals("showUserSelect")) {
			Document highscoreDocument = ((BreakWallModel) gameModel).getHighscoreDocument();
			buildUserSelectLayout(highscoreDocument);			
		}
		
		if(gameObject.equals("loadUserGame")) {
			removeElementFromGameField("mainHighscore");
			removeElementFromGameField("mainMenu");
			
		}
		
		if(gameObject.equals("showEnterName")) {
			buildEnterNameLayout();			
		}	
		
		if(gameObject.equals("quitHighscore")) {		
			removeElementFromGameField("mainHighscore");
			System.out.println("Highscore remove");
		}	
		
		if(gameObject.equals("quitEnterName")) {		
			removeElementFromGameField("mainEnterName");
			System.out.println("EnterName remove");
		}	
		
		
		
		if(gameObject.equals("showPlayButton")) {			
			panelbar.setMusicButton(true);
			this.requestFocus();
		}
		if(gameObject.equals("showMuteButton")) {			
			panelbar.setMusicButton(false);
			this.requestFocus();
		}
	}

	/**
	 * ֦fentliche Klasse zur Erzeugung eines Images aus einer URL.
	 * Bei nicht vorhandenem Bild unter der URL wird eine Fehlerinfo ausgegeben. 
	 * @author Mareike R?e, Gerrit Schulte
	 * @param e Instanz der Klasse, die das Bild erzeugen will
	 * @param ref String-Referenz des Bildpfades
	 * @return newImg Image-Instanz
	 */
	public static Image getImageByURL(Object e, String ref) {
		Image newImg = null;
		try {
			newImg = new ImageIcon(e.getClass().getResource(ref)).getImage();
		} catch(NullPointerException e1) {
			System.out.println("Das Bild kann unter dem angegebenen Pfad nicht gefunden werden: " + ref);
			e1.printStackTrace();
		}
		
		return newImg;
	}
	
	/**
	 * Private Klasse zur Erzeugung eines Objekts vom Typ ImgPanel. 
	 * @author Mareike R�ncke, Gerrit Schulte
	 *
	 */
	
	private class ImgPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image newImg;
		private int x;
		private int y;
		
		/*
		 *  Konstruktoren erzeugen ein Objekt vom Typ Image
		 */

		  public ImgPanel(String imgRef) {
			try {
			    newImg = new ImageIcon(getImageByURL(this, imgRef)).getImage();
			    this.x = 0;
			    this.y = 0;
			    Dimension size = new Dimension(newImg.getWidth(null), newImg.getHeight(null));
			    setPreferredSize(size);
			    setMinimumSize(size);
			    setMaximumSize(size);
			    setSize(size);
			    setLayout(null);
			} catch(NullPointerException e) {
				System.out.println("Could not find image for image path: " + imgRef);
				System.out.println("Check if path is correct.");
				e.printStackTrace();
			}
		  }	    

		  public void paintComponent(Graphics g) {
		    g.drawImage(newImg, x, y, null);	    
		  }		
	}

}
