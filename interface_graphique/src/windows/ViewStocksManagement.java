package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class ViewStocksManagement extends JPanel {

	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
	private Controller controller;
	ArrayList<String> tableauAlcool = /*controller.obtenirAlcools();*/ 
			new ArrayList<String>() {/**
				 */
				private static final long serialVersionUID = 1L;

			{ add("Whisky"); add("Vodka"); add("Alcool"); }};
	private static final long serialVersionUID = 1L;
	
	public class EcouteurAction implements ActionListener {
		
		private ViewStocksManagement vsm;
		
		public EcouteurAction(ViewStocksManagement vsm){
			this.vsm =vsm;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.visualiser(vsm);
			
	}
	}

	public ViewStocksManagement(Controller controller) {
		this.controller = controller;
		for (String boisson : tableauAlcool) {
			tableauBouton.add(new BoissonCheckBox(boisson));

		}
		for(BoissonCheckBox boutton: tableauBouton){
			this.add(boutton);
		}
		JButton boutton = new JButton("Visualiser");
		EcouteurAction ea = new EcouteurAction(this);
		boutton.addActionListener(ea);
		this.add(boutton);
	}

	public ArrayList<String> obtenirBouttonAlcool() {

		ArrayList<String> tableau = new ArrayList<String>();
		for (BoissonCheckBox boutton : tableauBouton) {
			if (boutton.isSelected()) {
				tableau.add(boutton.getNom());
			}
		}
		return tableau;

	}

}
