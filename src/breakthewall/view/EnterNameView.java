package breakthewall.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EnterNameView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel EnterUser;
	private JButton buttonEnter;
	private JTextField eingabeFeld, ausgabeFeld;
	

	public EnterNameView() {
		
		
		EnterUser = new JPanel();
        EnterUser.setPreferredSize(new Dimension(320, 200));
        EnterUser.setVisible(true);
        EnterUser.setOpaque(false);	
        EnterUser.setBackground(Color.green);
		
       		
		
        eingabeFeld = new JTextField("Your name?",20);
        EnterUser.add(eingabeFeld);
        
	    buttonEnter = new JButton ("Enter and Save");
	    buttonEnter.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 System.out.println(getTextFromTextField());
	         }
	      });
		EnterUser.add(buttonEnter);
		
		ausgabeFeld = new JTextField("",20);
        EnterUser.add(ausgabeFeld);
        ausgabeFeld.setEditable(false);
	      
		add(EnterUser,BorderLayout.CENTER);    
	} 
	
  
	public JButton getButton() {
		return buttonEnter;
	}

	public String getTextFromTextField() {
		
		return eingabeFeld.getText();
	}
	
	public JTextField getEingabeFeld() {
		return eingabeFeld;
	}


	
	
	
}
