package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewCommandManagement extends JPanel {
	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
	private Controller controller;
	ArrayList<String> tableauAlcool ;
	private static final long serialVersionUID = 1L;
	
	public class EcouteurAction implements ActionListener {
		
		private ViewCommandManagement vsm;
		
		public EcouteurAction(ViewCommandManagement viewCommandManagement){
			this.vsm =viewCommandManagement;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.visualiserCommandes(vsm);
			
	}
	}

	public ViewCommandManagement(Controller controller) {
		this.controller = controller;
		this.controller.setActualView(this) ;
		this.tableauAlcool=controller.obtenirAlcools();
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
