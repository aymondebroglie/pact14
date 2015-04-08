package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ViewAddBarman extends JPanel implements KeyListener{

	
	private static final long serialVersionUID = 1L;
	private JTextField nom = new JTextField();
	private JTextField prenom = new JTextField();
	private JTextField age = new JTextField();
	Controller controller;

	public ViewAddBarman(final Controller controller)
	{
		this.controller = controller;
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		nom.setPreferredSize(new Dimension(300,25));
		prenom.setPreferredSize(new Dimension(300,25));
		age.setPreferredSize(new Dimension(300,25));
		JLabel l_nom = new JLabel("Nom");
		nom.addKeyListener(this);
		//this.add(nom);
		JLabel l_prenom = new JLabel("Prénom");
		prenom.addKeyListener(this);
		//this.add(prenom);
		JLabel l_age = new JLabel("Âge");
		age.addKeyListener(this);
		//this.add(age);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.ajoutBarman(nom.getText(),prenom.getText(),Integer.parseInt(age.getText()));
			}
			
		});
		//this.add(ok);
		
		layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, l_nom, l_prenom, l_age );
		layout.linkSize(SwingConstants.HORIZONTAL, nom, prenom, age );
		
		layout.setHorizontalGroup(
	               	layout.createParallelGroup(GroupLayout.Alignment.CENTER) 
	                   	.addGroup(layout.createSequentialGroup()
	                   			.addComponent(l_nom)
	                   			.addComponent(nom))
	                	.addGroup(layout.createSequentialGroup()
	                			.addComponent(l_prenom)
	                			.addComponent(prenom))
	                	.addGroup(layout.createSequentialGroup()
	                			.addComponent(l_age)
	                			.addComponent(age))
	                	.addComponent(ok)
	               	);
	       
	    layout.setVerticalGroup(
	               	layout.createSequentialGroup()
	               		.addGroup(layout.createParallelGroup()
	                   			.addComponent(l_nom)
	                   			.addComponent(nom))
	                	.addGroup(layout.createParallelGroup()
	                			.addComponent(l_prenom)
	                			.addComponent(prenom))
	                	.addGroup(layout.createParallelGroup()
	                			.addComponent(l_age)
	                			.addComponent(age))
	                	.addComponent(ok)
	               	);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	     if (key == KeyEvent.VK_ENTER) {
		controller.ajoutBarman(nom.getText(),prenom.getText(),Integer.parseInt(age.getText()));
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
