package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



import controller.Controller;

public class ViewBossHome extends JPanel 
{	
	private static final long serialVersionUID = 1L;
	
	private Controller controller ;
	
	public  ViewBossHome(Controller controller)
	{
		this.controller = controller;
		
		JButton consultervosdonnees =  new JButton("Consulter vos donnees");
		JButton gerervosstocks =  new JButton("Gérer vos stocks");
		
		this.add(consultervosdonnees);
		this.add(gerervosstocks) ;
		
		ConsulterVosDonnees cvd_listener = new ConsulterVosDonnees();
		consultervosdonnees.addActionListener(cvd_listener);
		
		GererVosStocks gvs_listener = new GererVosStocks();
		gerervosstocks.addActionListener(gvs_listener);
	}
	
	private class ConsulterVosDonnees implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.addPreviousView(ViewBossHome.this);
			controller.consulterVosDonnees();
		}
	}
	private class GererVosStocks implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.addPreviousView(ViewBossHome.this);
			controller.gestionStocks();
		}
	}
}
