package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewBarmanHome extends JPanel
{
private ViewController view;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViewBarmanHome(ViewController view){
		this.view = view;
		 JButton note = new JButton("Imprimer Note");
		 ImprimerNote imprimerNote = new ImprimerNote();
		 RetirerGoulot retirerGoulot = new RetirerGoulot();
		 note.addActionListener(imprimerNote);
		 this.add(note);
		 JButton goulot = new JButton("retirer goulot");
		 goulot.addActionListener(retirerGoulot);
		 this.add(goulot);
;	}
}
