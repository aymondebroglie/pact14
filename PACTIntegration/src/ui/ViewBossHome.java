package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ViewBossHome extends JPanel {
	private static final long serialVersionUID = 1L;

	private Controller controller;

	public ViewBossHome(final Controller controller) {
		this.controller = controller;

		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		JButton consultervosdonnees = new JButton("Consulter vos donnees");
		JButton gerervosstocks = new JButton("Gerer vos stocks");
		JButton ajouterBarman = new JButton("Ajouter Barman");
		ajouterBarman.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.ecranAjoutBarman();
			}
			
		});
		/*this.add(ajouterBarman);

		this.add(consultervosdonnees);
		this.add(gerervosstocks);*/

		ConsulterVosDonnees cvd_listener = new ConsulterVosDonnees();
		consultervosdonnees.addActionListener(cvd_listener);

		GererVosStocks gvs_listener = new GererVosStocks();
		gerervosstocks.addActionListener(gvs_listener);
		
		layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, ajouterBarman, consultervosdonnees, gerervosstocks );
		
		layout.setHorizontalGroup(
	               layout.createParallelGroup() 
	                   .addComponent(ajouterBarman)
	                   .addComponent(consultervosdonnees)
	                   .addComponent(gerervosstocks)
	               );
	       
	    layout.setVerticalGroup(
	               layout.createSequentialGroup()
	               		.addComponent(ajouterBarman)
	                    .addComponent(consultervosdonnees)
	                    .addComponent(gerervosstocks)
	               );
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
