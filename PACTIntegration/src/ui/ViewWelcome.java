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
				
		this.setLayout(new GridLayout(4,1));
		
		this.add(new JLabel(
				"Bienvenue sur OptiBar ! \n"
				+"Mais avant tout... Etes-vous un barman ou le patron ? \n"
				+"Il suffit pour cela d'aller dans le menu : \n"));
		
		this.add(new JLabel(" G�n�ral >> Utilisateur",JLabel.CENTER)) ;
	}
}
