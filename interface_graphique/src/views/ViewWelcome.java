package views;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class ViewWelcome extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Controller controller;
	
	public ViewWelcome(Controller controller) 
	{
		this.controller = controller;
				
		this.setLayout(new GridLayout(4,1));
		
		this.add(new JLabel("Bienvenue sur OptiBar !",JLabel.CENTER));
		
		this.add(new JLabel("Mais avant tout... Etes-vous un barman ou le patron ?",JLabel.CENTER)) ;
		
		this.add(new JLabel("Il suffit pour ça d'aller dans le menu :",JLabel.CENTER)) ;
		
		this.add(new JLabel(" Général >> Utilisateur",JLabel.CENTER)) ;
	}
}
