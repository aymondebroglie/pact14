package ui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


public class ViewBossLogin extends JPanel implements KeyListener
{
private Controller controller;
private JPasswordField mdp = new JPasswordField();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private class MotDePasse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			controller.motDePasse(mdp.getPassword());
		}

		
	}
	
	public ViewBossLogin(Controller controller){	
		this.controller = controller;
		
		
		this.add(new JLabel("Veuillez rentrer votre mot de passe"));
		MotDePasse mp = new MotDePasse();
		mdp.setPreferredSize(new Dimension(300,25));
		JButton boutton = new JButton("OK");
		boutton.addActionListener(mp);
		mdp.addKeyListener(this);
		this.add(mdp);
		this.add(boutton);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	     if (key == KeyEvent.VK_ENTER) {
	    	 controller.motDePasse(mdp.getPassword());
	}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
