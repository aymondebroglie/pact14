package windows;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;



import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.Controller;


public class ViewStockManagementSouth extends JPanel  implements ItemListener
{
	
	
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			}
		private Controller controller;
		private JRadioButton soiree= new JRadioButton("soirée");
		private JRadioButton  semaine= new JRadioButton ("semaine");
		private JRadioButton  mois= new JRadioButton ("mois");
		private JRadioButton  annee= new JRadioButton ("annee");
		
				
	
	private static final long serialVersionUID = 1L;
	

	public ViewStockManagementSouth(Controller controller) 
	{
	this.controller = controller;
	soiree.addItemListener(this);
	semaine.addItemListener(this);
	mois.addItemListener(this);
	annee.addItemListener(this);
	this.add(soiree);
	this.add(semaine);
	this.add(mois);
	this.add(annee);

	
	}
	
}
