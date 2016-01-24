package breakthewall.view;


import javax.swing.*;

import breakthewall.BreakWallConfig;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EnterNameView extends JPanel {
	

	private JPanel enterUser;
	private JButton btnEnterSave;
	private JTextField eingabeFeld;
	private JButton btnBackMenu;
	private JLabel lblEnterName;
	private ArrayList<JButton> navigationButtonsSave;
	
	/*
	 * Constructor. 
	 * Add JButton, JTextField and JLabel to JPanel
	 */	
	
	public EnterNameView() {
		
		enterUser = new JPanel();
	    enterUser.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
        
	    enterUser.setBackground(new Color(245,245,245,250));
	    enterUser.setLayout(null);
	    
	    navigationButtonsSave = new ArrayList<JButton>();
	    
		//Layout everything.
		
	    lblEnterName = new JLabel("Enter your Name");
	    lblEnterName.setForeground(Color.GRAY);
		lblEnterName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterName.setBounds(231, 139, 138, 14);
		enterUser.add(lblEnterName);
		
		btnEnterSave = new JButton("Enter and Save");
		btnEnterSave.setBounds(231, 208, 138, 33);
		enterUser.add(btnEnterSave);
		
		eingabeFeld = new JTextField();
		eingabeFeld.setHorizontalAlignment(SwingConstants.CENTER);
		eingabeFeld.setFont(new Font("Calibri", Font.PLAIN, 16));
		eingabeFeld.setBounds(231, 164, 138, 33);
		enterUser.add(eingabeFeld);
		eingabeFeld.setColumns(10); 
		
		btnBackMenu = new JButton();
		btnBackMenu.setActionCommand("BackSaveMenu");
		btnBackMenu.setForeground(new Color (250,250,250));
		btnBackMenu.setBackground(new Color (250,250,250));
		btnBackMenu.setSelectedIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		btnBackMenu.setIcon(new ImageIcon(this.getClass().getResource(BreakWallConfig.backArrow)));
		btnBackMenu.setBounds(10, 11, 50, 33);
		enterUser.add(btnBackMenu);
		
		navigationButtonsSave.add(btnEnterSave);
		navigationButtonsSave.add(btnBackMenu);
		
		add(enterUser);	
	} 
	
	
	/**
	 * public method to call in BreakWallView and to add ActionListener for MVC
	 */
  
	public ArrayList<JButton> getButtonList() {
		return navigationButtonsSave;
	}

	public String getTextFromTextField() {
		
		return eingabeFeld.getText();
	}
	
	public JTextField getEingabeFeld() {
		return eingabeFeld;
	}


	
	
	
}