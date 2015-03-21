package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


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

	
	public ViewSeeDatas(Controller controller) 
	{
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		JButton stocks =new JButton("Stocks");
		JButton commande =new JButton("Commandes");
		JButton etatDesStocks = new JButton("Etat des Stocks");
		stocks.addActionListener(new Stocks());
		commande.addActionListener(new Commande());
		etatDesStocks.addActionListener(new EtatDesStocks());
		this.controller = controller;
		/**this.add(stocks);
		this.add(commande);
		this.add(etatDesStocks);*/
		//this.add(budget);
		
		layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, stocks, commande, etatDesStocks );
		
		layout.setHorizontalGroup(
	               	layout.createParallelGroup(GroupLayout.Alignment.CENTER) 
	                   	.addComponent(stocks)
	                   	.addComponent(commande)
	                   	.addComponent(etatDesStocks)
	               	);
	       
	    layout.setVerticalGroup(
	               	layout.createSequentialGroup()
	               		.addComponent(stocks)
	                   	.addComponent(commande)
	                   	.addComponent(etatDesStocks)
	               	);
	}

}
