package windows;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;


public class ViewStocksManagement extends JPanel  implements ItemListener
{
	

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(ItemEvent.SELECTED == e.getStateChange()){
				controller.ajoutAlcool(((BoissonCheckBox) (e.getItem())).getNom());
				
			}
			else {
				controller.enleverAlcool(((BoissonCheckBox) (e.getItem())).getNom());
			}
			}
	private BoissonCheckBox vodka = new BoissonCheckBox("Vodka");
	private BoissonCheckBox  whisky = new BoissonCheckBox ("Whisky");
	private BoissonCheckBox  alcool= new BoissonCheckBox ("Whisky");
	private static final long serialVersionUID = 1L;
	private Controller controller;

	public ViewStocksManagement(Controller controller) 
	{
	this.controller = controller;
	//Il faut ici demander a la base de donnée la liste des alcools
	vodka.addItemListener(this);
	whisky.addItemListener(this);
	alcool.addItemListener(this);
	this.add(vodka);
	this.add(whisky);
	this.add(alcool);
;	}
	
}
