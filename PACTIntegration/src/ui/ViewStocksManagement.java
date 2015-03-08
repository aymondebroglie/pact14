package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import stat.DimensionException;



public class ViewStocksManagement extends JPanel {

	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
	private JCheckBox outilstat = new JCheckBox("Outils statistiques") ;
	private Controller controller;
	ArrayList<String> tableauAlcool;

	private static final long serialVersionUID = 1L;

	public boolean isStat()
	{
		return this.outilstat.isSelected() ;
	}
	
	public class EcouteurAction implements ActionListener {

		private ViewStocksManagement vsm;

		
		public EcouteurAction(ViewStocksManagement vsm) {
			this.vsm = vsm;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				controller.visualiser(vsm);
			} catch (DimensionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public ViewStocksManagement(Controller controller) {
		this.controller = controller;
		this.controller.setActualView(this) ;
		
		tableauAlcool=controller.obtenirAlcools();
		for (String boisson : tableauAlcool) {
			tableauBouton.add(new BoissonCheckBox(boisson));

		}
		for (BoissonCheckBox boutton : tableauBouton) {
			this.add(boutton);
		}
		this.add(outilstat) ;
				
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
