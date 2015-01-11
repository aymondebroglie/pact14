package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewWelcome extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton barman = new JButton("Barman");
	private JButton gestionnaire = new JButton("Gestionnaire");

	private class BouttonBarman implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ViewBarmanHome vbh = new ViewBarmanHome();
			Window window = new Window(vbh);

		}

	}

	private class BouttonGestionnaire implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ViewBossHome vbh = new ViewBossHome();
			Window window = new Window(vbh);

		}

	}
	public ViewWelcome() {
		BouttonBarman bouttonBarman = new BouttonBarman();
		BouttonGestionnaire bouttonGestionnaire = new BouttonGestionnaire();
		barman.addActionListener(bouttonBarman);
		gestionnaire.addActionListener(bouttonGestionnaire);
		this.add(barman);
		this.add(gestionnaire);

	}
}
