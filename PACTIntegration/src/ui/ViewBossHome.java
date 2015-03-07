package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ViewBossHome extends JPanel {
	private static final long serialVersionUID = 1L;

	private Controller controller;

	public ViewBossHome(Controller controller) {
		this.controller = controller;
		this.controller.setActualView(this) ;

		JButton consultervosdonnees = new JButton("Consulter vos donnees");
		JButton gerervosstocks = new JButton("Gerer vos stocks");
		JButton ajouterBarman = new JButton("Ajouter Barman");
		ajouterBarman.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				view.ecranAjoutBarman();
			}
			
		});
		this.add(ajouterBarman);

		this.add(consultervosdonnees);
		this.add(gerervosstocks);

		ConsulterVosDonnees cvd_listener = new ConsulterVosDonnees();
		consultervosdonnees.addActionListener(cvd_listener);

		GererVosStocks gvs_listener = new GererVosStocks();
		gerervosstocks.addActionListener(gvs_listener);
	}

	private class ConsulterVosDonnees implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.consulterVosDonnees();
		}
	}

	private class GererVosStocks implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.gestionStocks();
		}
	}
}
