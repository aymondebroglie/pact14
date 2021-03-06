package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import controller.Controller;

public class ViewChangePassword extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPasswordField mdp = new JPasswordField();
	private JPasswordField nmdp1 = new JPasswordField();
	private JPasswordField nmdp2 = new JPasswordField();

	private class ChangeMotDePasse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

		controller.addPreviousView(ViewChangePassword.this);
		controller.changerMDP(mdp.getPassword(),nmdp1.getPassword(),nmdp2.getPassword());	

							
	
	}
	}
	

	public ViewChangePassword(Controller view) {
		this.controller = view;

		this.add(new JLabel("Votre mot de passe actuel"));
		mdp.setPreferredSize(new Dimension(300, 25));
		this.add(mdp);
		ChangeMotDePasse mp = new ChangeMotDePasse();
		this.add(new JLabel("Nouveau mot de passe"));
		nmdp1.setPreferredSize(new Dimension(300, 25));
		nmdp2.setPreferredSize(new Dimension(300, 25));
		JButton bouton = new JButton("OK");
		bouton.addActionListener(mp);
		this.add(nmdp1);
		this.add(new JLabel("Confirmer le mot de passe"));
		this.add(nmdp2);
		this.add(bouton);
	}
}
