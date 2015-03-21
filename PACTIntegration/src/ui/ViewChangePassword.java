package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;



public class ViewChangePassword extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPasswordField mdp = new JPasswordField();
	private JPasswordField nmdp1 = new JPasswordField();
	private JPasswordField nmdp2 = new JPasswordField();

	private class ChangeMotDePasse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

		controller.changerMDP(mdp.getPassword(),nmdp1.getPassword(),nmdp2.getPassword());	

							
	
	}
	}
	

	public ViewChangePassword(Controller controller) {
		this.controller = controller ;

		this.add(new JLabel("Votre mot de passe actuel"));
		mdp.setPreferredSize(new Dimension(300, 25));
		mdp.addKeyListener(this);
		this.add(mdp);
		ChangeMotDePasse mp = new ChangeMotDePasse();
		this.add(new JLabel("Nouveau mot de passe"));
		nmdp1.setPreferredSize(new Dimension(300, 25));
		nmdp2.setPreferredSize(new Dimension(300, 25));
		JButton bouton = new JButton("OK");
		bouton.addActionListener(mp);
		nmdp1.addKeyListener(this);
		nmdp2.addKeyListener(this);
		this.add(nmdp1);
		this.add(new JLabel("Confirmer le mot de passe"));
		this.add(nmdp2);
		this.add(bouton);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	     if (key == KeyEvent.VK_ENTER) {
	    	 controller.changerMDP(mdp.getPassword(),nmdp1.getPassword(),nmdp2.getPassword());
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
