package breakthewall.view;


import javax.swing.*;

import breakthewall.controller.BreakWallController;

import java.awt.*;




public class NavigationBarView extends JPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private JLabel lab1;
	private JLabel lab2;
	private JLabel lab3;
	
	private TextField scorecount;
	private TextField levelcount;
	private TextField lifecount; 
	private int counter = 0;
	
	
	public NavigationBarView(BreakWallController controller) {
				
		p = new JPanel();
		p.setBackground(Color.GRAY);
        p.setPreferredSize(new Dimension(590, 40));
        p.setVisible(true);
        p.setOpaque(false);	
		
		lab1 = new JLabel ("Score");
		lab2 = new JLabel ("Level");
		lab3 = new JLabel ("Life");
		b1 = new JButton ("Play");
		b1.addActionListener(controller);
		b2 = new JButton ("Pause");
		b2.addActionListener(controller);
		b3 = new JButton ("Save");
		b3.addActionListener(controller);
		b4 = new JButton ("Exit");
		b4.addActionListener(controller);
		scorecount = new TextField(" 1234 ");
		scorecount.setEditable(false);
		scorecount.setFocusable(false);
		levelcount = new TextField(" 1 ");
		levelcount.setFocusable(false);
		levelcount.setEditable(false);
		lifecount = new TextField(" 2 ");
		lifecount.setFocusable(false);
		lifecount.setEditable(false);
		
		
		p.add(lab1);
		p.add(scorecount);
		p.add(lab2);
		p.add(levelcount);
		p.add(lab3);
		p.add(lifecount);
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
				
		add(p,BorderLayout.NORTH);	
	
	}

	
	public int setCurrentCounter(int zahl) {
		counter = zahl;
		return counter;
	}
	
	public int getCurrentCounter() {
		return counter;
	}
	
	
	
}
