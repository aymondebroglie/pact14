package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.Controller;

public class ViewWelcome extends JPanel 
{

	private static final long serialVersionUID = 1L;
	private JButton barman = new JButton("Barman");
	private JButton gestionnaire = new JButton("Gestionnaire");
	private Controller view;

	private class BoutonBarman implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			view.boutonBarman();
		}

	}

	private class BoutonGestionnaire implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			view.boutonGestionnaire();
		}
	}
	public ViewWelcome(Controller view) 
	{
		this.view = view;
		BoutonBarman boutonBarman = new BoutonBarman();
		BoutonGestionnaire boutonGestionnaire = new BoutonGestionnaire();
		barman.addActionListener(boutonBarman);
		gestionnaire.addActionListener(boutonGestionnaire);
		this.add(barman);
		this.add(gestionnaire);

	}
}
