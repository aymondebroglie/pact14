package ui;

import javax.swing.JPanel;

// trame de vue pour les parametres (langues, systemes d'unites, et caetera ....)
public class ViewSettings extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Controller controller ;
	
	public ViewSettings(Controller controller)
	{
		this.controller = controller ;
	}

}
