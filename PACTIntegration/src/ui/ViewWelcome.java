package ui;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ViewWelcome extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Controller controller;
	
	public ViewWelcome(Controller controller) 
	{
		this.controller = controller;
				
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		JLabel lign1 = new JLabel("Bienvenue sur OptiBar !",JLabel.CENTER);
		JLabel lign2 = new JLabel("Mais avant tout... Barman ou Patron ?",JLabel.CENTER) ;
		JLabel lign3 = new JLabel(" Pour cela : Général ⇢ Utilisateur",JLabel.CENTER) ;
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(lign1)
				.addComponent(lign2)
				.addComponent(lign3)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(lign1)
				.addComponent(lign2)
				.addComponent(lign3)
				);
	}
}
