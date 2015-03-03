package windows;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;


public class ViewStocksManagement extends JPanel  implements ItemListener
{
	
	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(ItemEvent.SELECTED == e.getStateChange()){
				controller.ajoutAlcool(((BoissonCheckBox) (e.getItem())).getNom());
				
			}
			else {
				controller.enleverAlcool(((BoissonCheckBox) (e.getItem())).getNom());
			}
			}
		private Controller controller;
		ArrayList<String> tableauAlcool = new ArrayList<String>();
				
	
	private static final long serialVersionUID = 1L;
	

	public ViewStocksManagement(Controller controller) 
	{
	this.controller = controller;
	tableauAlcool.add("Whisky");
	tableauAlcool.add("Vodka");
	for(String boisson : tableauAlcool){
		tableauBouton.add(new BoissonCheckBox(boisson));
		
	}
	for(BoissonCheckBox boutton : tableauBouton){
		boutton.addItemListener(this);
		this.add(boutton);
	}
;	}
	
}
