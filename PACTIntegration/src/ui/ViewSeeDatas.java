package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ViewSeeDatas extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private class Stocks implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.obtenirstock();
		}
	}
	private class Commande implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.commande();
		}
	}
	
	private class EtatDesStocks implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.etatDesStocks();

		}
	}

	
	public ViewSeeDatas(Controller controller) {
		JButton stocks =new JButton("Stocks");
		JButton commande =new JButton("Commandes");
		JButton etatDesStocks = new JButton("Etat des Stocks");
		stocks.addActionListener(new Stocks());
		commande.addActionListener(new Commande());
		etatDesStocks.addActionListener(new EtatDesStocks());
		this.controller = controller;
		this.add(stocks);
		this.add(commande);
		this.add(etatDesStocks);
		//this.add(budget);
	}

}
