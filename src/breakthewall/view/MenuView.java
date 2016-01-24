package breakthewall.view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import breakthewall.BreakWallConfig;
import breakthewall.controller.BreakWallController;

import java.awt.*;
import java.util.ArrayList;




public class MenuView extends JPanel  {
	
	private JPanel menuPanel;
	private JButton btnHighscores;
	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnBack;
	private JButton btnNewGame;
	private JButton btnExit;
	private JLabel lblMENU;
	private ArrayList<JButton> navigationButtonsMenu;
	
	/*
	 * Constructor. 
	 * Add different (Menu) JButtons and JLabel to JPanel. 
	 * Add all Buttons to ArrayList<JButton>
	 */	
	
	public MenuView() {
	
	    menuPanel = new JPanel();
		
	    menuPanel.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
	    menuPanel.setVisible(true);
	    
	    navigationButtonsMenu = new ArrayList<JButton>();
		
        menuPanel.setBackground(new Color(245,245,245,240));
        menuPanel.setLayout(null);
        
		//Layout everything.
        
        lblMENU = new JLabel("MENU");
		lblMENU.setForeground(Color.DARK_GRAY);
		lblMENU.setHorizontalAlignment(SwingConstants.CENTER);
		lblMENU.setFont(new Font("Calibri", Font.BOLD, 25));
		lblMENU.setBounds(248, 68, 104, 33);
		menuPanel.add(lblMENU);
		
		btnHighscores = new JButton("Highscores");
		btnHighscores.setBounds(248, 164, 104, 33);
		menuPanel.add(btnHighscores);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(248, 208, 104, 33);
		menuPanel.add(btnSave);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(248, 252, 104, 33);
		menuPanel.add(btnLoad);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(248, 296, 104, 33);
		btnBack.setSelectedIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		btnBack.setIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		menuPanel.add(btnBack);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(248, 120, 104, 33);
		menuPanel.add(btnNewGame);
		
		btnExit = new JButton("Exit");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.DARK_GRAY);
		btnExit.setBounds(248, 360, 104, 33);
		menuPanel.add(btnExit);
		
		
		navigationButtonsMenu.add(btnHighscores);
		navigationButtonsMenu.add(btnSave);
		navigationButtonsMenu.add(btnLoad);
		navigationButtonsMenu.add(btnBack);
		navigationButtonsMenu.add(btnExit);
		navigationButtonsMenu.add(btnNewGame);
		
		add(menuPanel);	
	
	}
	
	/**
	 * public method to call in BreakWallView and to add ActionListener for MVC
	 */
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtonsMenu;
	}
	
}