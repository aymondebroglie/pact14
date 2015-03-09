package ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class ViewWelcome extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Controller controller;
	
	public ViewWelcome(Controller controller) 
	{
		this.controller = controller;
		//this.controller.setActualView(this) ;
				
		this.setLayout(new GridLayout(4,1));
		
		this.add(new JLabel("Bienvenue sur OptiBar ! \n",JLabel.CENTER));
		this.add(new JLabel("Mais avant tout... Barman ou Patron ?",JLabel.CENTER)) ;
		this.add(new JLabel(" Pour cela : Général >> Utilisateur",JLabel.CENTER)) ;
	}
}
