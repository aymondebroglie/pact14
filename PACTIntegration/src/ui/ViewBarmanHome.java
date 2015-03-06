package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


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

	public ViewBarmanHome(Controller view) {
		this.view = view;
		JButton note = new JButton("Imprimer Note");
		ImprimerNote imprimerNote = new ImprimerNote();
		RetirerGoulot retirerGoulot = new RetirerGoulot();
		JButton bouteilleFinie = new JButton("Bouteille finie");
		bouteilleFinie.addActionListener(new BouteilleFinie());
		note.addActionListener(imprimerNote);
		this.add(note);
		this.add(bouteilleFinie);
		JButton goulot = new JButton("Retirer goulot");
		goulot.addActionListener(retirerGoulot);
		this.add(goulot);


	
}
		
	
}
