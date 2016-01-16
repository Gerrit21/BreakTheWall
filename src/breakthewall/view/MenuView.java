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
	private JButton btnExit;
	private JLabel lblMENU;
	private ArrayList<JButton> navigationButtonsMenu;
	

	public MenuView() {
	
	    menuPanel = new JPanel();
		
	    menuPanel.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
	    menuPanel.setVisible(true);
	    
	    navigationButtonsMenu = new ArrayList<JButton>();
		
        menuPanel.setBackground(new Color(245,245,245,240));
        menuPanel.setLayout(null);
        
        lblMENU = new JLabel("MENU");
		lblMENU.setForeground(Color.DARK_GRAY);
		lblMENU.setHorizontalAlignment(SwingConstants.CENTER);
		lblMENU.setFont(new Font("Calibri", Font.BOLD, 25));
		lblMENU.setBounds(248, 68, 104, 33);
		menuPanel.add(lblMENU);
		
		btnHighscores = new JButton("Highscores");
		btnHighscores.setBounds(248, 120, 104, 33);
		menuPanel.add(btnHighscores);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(248, 164, 104, 33);
		menuPanel.add(btnSave);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(248, 208, 104, 33);
		menuPanel.add(btnLoad);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(248, 252, 104, 33);
		menuPanel.add(btnBack);
		
		btnExit = new JButton("Exit");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.DARK_GRAY);
		btnExit.setBounds(248, 340, 104, 33);
		menuPanel.add(btnExit);
		
		navigationButtonsMenu.add(btnHighscores);
		navigationButtonsMenu.add(btnSave);
		navigationButtonsMenu.add(btnLoad);
		navigationButtonsMenu.add(btnBack);
		navigationButtonsMenu.add(btnExit);
		
		add(menuPanel);	
	
	}
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtonsMenu;
	}
	
}