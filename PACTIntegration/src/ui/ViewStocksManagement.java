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
	private Controller controller;
	ArrayList<String> tableauAlcool;

	private static final long serialVersionUID = 1L;

	
	
	public class EcouteurVisualiser implements ActionListener {

		private ViewStocksManagement vsm;

		
		public EcouteurVisualiser(ViewStocksManagement vsm) {
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
	
	public class EcouteurStats implements ActionListener 
	{
		private ViewStocksManagement vsm ;
		
			public EcouteurStats(ViewStocksManagement vsm)
		{
			this.vsm = vsm ;
		}

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					controller.visualiserStat(vsm);
				} 
				catch (DimensionException e1) 
				{
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
		for (BoissonCheckBox bouton : tableauBouton) {
			this.add(bouton);
		}
		
				
		JButton bouton_visualiser = new JButton("Visualiser");
		EcouteurVisualiser ev = new EcouteurVisualiser(this);
		bouton_visualiser.addActionListener(ev);
		this.add(bouton_visualiser);
		
		JButton bouton_outilstat = new JButton("Outil Statistique") ;
		EcouteurStats es = new EcouteurStats(this) ;
		bouton_outilstat.addActionListener(es) ;
		this.add(bouton_outilstat) ;
		
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
