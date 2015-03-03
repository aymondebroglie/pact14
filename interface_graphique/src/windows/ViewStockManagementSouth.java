package windows;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import controller.Controller;


public class ViewStockManagementSouth extends JPanel  implements ItemListener
{
	
	
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			}
		private Controller controller;
		private BoissonCheckBox soiree= new BoissonCheckBox("soirée");
		private BoissonCheckBox semaine= new BoissonCheckBox("semaine");
		private BoissonCheckBox mois= new BoissonCheckBox("mois");
		private BoissonCheckBox annee= new BoissonCheckBox("annee");
		
				
	
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
