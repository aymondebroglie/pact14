package views;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import controller.Controller;

public class ViewBossLogin extends JPanel 
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
			controller.addPreviousView(ViewBossLogin.this);
			controller.motDePasse(mdp.getPassword());
		}

		
	}
	
	public ViewBossLogin(Controller view){	
		this.controller = view;
		this.add(new JLabel("Veuillez rentrer votre mot de passe"));
		MotDePasse mp = new MotDePasse();
		mdp.setPreferredSize(new Dimension(300,25));
		JButton boutton = new JButton("OK");
		boutton.addActionListener(mp);
		this.add(mdp);
		this.add(boutton);
	}
}
