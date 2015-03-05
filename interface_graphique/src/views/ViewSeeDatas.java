package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

public class ViewSeeDatas extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private class Stocks implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.addPreviousView(ViewSeeDatas.this);
			controller.obtenirstock();
		}
	}
	private class Commande implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.addPreviousView(ViewSeeDatas.this);
			controller.commande();
		}
	}
	
	private class Budget implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.addPreviousView(ViewSeeDatas.this);
			controller.budget();

		}
	}

	
	public ViewSeeDatas(Controller controller) {
		JButton stocks =new JButton("Stocks");
		JButton commande =new JButton("Commandes");
		JButton budget =new JButton("Budget");
		stocks.addActionListener(new Stocks());
		commande.addActionListener(new Commande());
		budget.addActionListener(new Budget());
		this.controller = controller;
		this.add(stocks);
		this.add(commande);
		//this.add(budget);
	}

}
