package windows;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewBossLogin extends JPanel 
{
private ViewController view;
private JFormattedTextField mdp = new JFormattedTextField("Mot de Passe");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private class MotDePasse implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				view.motDePasse(mdp.getText());
			}
			
		}
		
	}
	
	public ViewBossLogin(ViewController view){
		this.view = view;
		this.add(new JLabel("Veuillez rentrer votre mot de passe"));
		MotDePasse mp = new MotDePasse();
		mdp.setPreferredSize(new Dimension(300,25));
		this.addKeyListener(mp);
		this.add(mdp);
	}
}
