package breakthewall.view;


import javax.swing.*;

import breakthewall.controller.BreakWallController;

import java.awt.*;
import java.util.ArrayList;




public class MenuView extends JPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel menup;
	private JButton bu1;
	private JButton bu2;
	private JButton bu3;
	private JButton bu4;
	private ArrayList<JButton> navigationButtonsMenu;
	private JLabel lab1;
	private JLabel lab2;
	private JLabel lab3;
	
	private TextField scorecount;
	private TextField levelcount;
	private TextField lifecount; 
	private int counter = 0;
	
	
	public MenuView() {
		
		navigationButtonsMenu = new ArrayList<JButton>();
		menup = new JPanel();
		
        menup.setPreferredSize(new Dimension(120, 500));
        menup.setVisible(true);
        menup.setOpaque(false);	
		
		lab1 = new JLabel ("Current Score");
	
		bu1 = new JButton ("Highscores");		
		// b1.addActionListener(controller);
		bu2 = new JButton ("Save");
		//b2.addActionListener(controller);
		bu3 = new JButton ("Back");
		//b3.addActionListener(controller);
		bu4 = new JButton ("Exit");
		//b4.addActionListener(controller);
		scorecount = new TextField(" 1234 ");
		scorecount.setEditable(false);
		scorecount.setFocusable(false);
		
		navigationButtonsMenu.add(bu1);
		navigationButtonsMenu.add(bu2);
		navigationButtonsMenu.add(bu3);
		navigationButtonsMenu.add(bu4);
		
		menup.add(lab1);
		menup.add(scorecount);

		menup.add(bu1);
		menup.add(bu2);
		menup.add(bu3);
		menup.add(bu4);
				
		add(menup,BorderLayout.CENTER);	
	
	}
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtonsMenu;
	}

	
	public int setCurrentCounter(int zahl) {
		counter = zahl;
		return counter;
	}
	
	public int getCurrentCounter() {
		return counter;
	}
	
	
	
}
