package windows;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import controller.Controller;

public class ViewChangePassword extends JPanel {
	private Controller view;
	private JPasswordField nmdp1 = new JPasswordField();
	private JPasswordField nmdp2 = new JPasswordField();

	
	private class MotDePasse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.changeMotDePasse(nmdp1.getPassword(),nmdp2.getPassword());
           			
		}

		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ViewChangePassword(Controller view){
		this.view = view;
		MotDePasse mp = new MotDePasse();
		this.add(new JLabel("Nouveau mot de passe"));
		nmdp1.setPreferredSize(new Dimension(300,25));
		this.add(new JLabel("Confirmer le mot de passe"));
		nmdp2.setPreferredSize(new Dimension(300,25));
		JButton boutton = new JButton("OK");
		boutton.addActionListener(mp);
		this.add(nmdp1);
		this.add(nmdp2);
		this.add(boutton);
	}
}
