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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViewBarmanHome(ViewController view){
		this.view = view;
		 JButton Note = new JButton("Imprimer Note");
		 ImprimerNote imprimerNote = new ImprimerNote();
		 Note.addActionListener(imprimerNote);
		 this.add(Note);
;	}
}
