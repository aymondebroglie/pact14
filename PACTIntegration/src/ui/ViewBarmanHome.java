package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ViewBarmanHome extends JPanel {
	private Controller view;

	private class ImprimerNote implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.imprimerNote();
		}
	}

	private class RetirerGoulot implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.retirerGoulot();

		}
	}
	
	private class BouteilleFinie implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.bouteilleFinie();
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViewBarmanHome(final Controller controller) {
		this.view = controller;

		
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		JButton note = new JButton("Imprimer Note");
		ImprimerNote imprimerNote = new ImprimerNote();
		RetirerGoulot retirerGoulot = new RetirerGoulot();
		JButton bouteilleFinie = new JButton("Bouteille finie");
		bouteilleFinie.addActionListener(new BouteilleFinie());
		note.addActionListener(imprimerNote);
		//this.add(note);
		//this.add(bouteilleFinie);
		
		JButton goulot = new JButton("Retirer goulot");
		goulot.addActionListener(retirerGoulot);
		//this.add(goulot);
		
		JButton associerGoulot = new JButton("Associer Goulot");
		associerGoulot.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			 controller.ecranAssocierGoulot();
				
			}
			
		});
		//this.add(associerGoulot);

		layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, note, bouteilleFinie, goulot, associerGoulot );
		
		layout.setHorizontalGroup(
	               layout.createParallelGroup() 
	                   .addComponent(note)
	                   .addComponent(bouteilleFinie)
	                   .addComponent(goulot)
	                   .addComponent(associerGoulot)
	               );
	       
	    layout.setVerticalGroup(
	               layout.createSequentialGroup()
	                   .addComponent(note)
	                   .addComponent(bouteilleFinie)
	                   .addComponent(goulot)
	                   .addComponent(associerGoulot)
	               );
	    
	    
	    	
}
		
	
}
