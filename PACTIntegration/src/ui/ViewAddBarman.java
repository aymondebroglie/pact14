package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewAddBarman extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private  Controller controller;
	private JTextField nom = new JTextField();
	private JTextField prenom = new JTextField();
	private JTextField age = new JTextField();

	public ViewAddBarman(final Controller controller){
		nom.setPreferredSize(new Dimension(300,25));
		prenom.setPreferredSize(new Dimension(300,25));
		age.setPreferredSize(new Dimension(300,25));
		this.add(new JLabel("Nom"));
		this.add(nom);
		this.add(new JLabel("Prenom"));
		this.add(prenom);
		this.add(new JLabel("Age"));
		this.add(age);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.ajoutBarman(nom.getText(),prenom.getText(),Integer.parseInt(age.getText()));
			}
			
		});
		this.add(ok);
	}

	
}
