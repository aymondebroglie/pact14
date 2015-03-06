package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class ViewStockManagementSouth extends JPanel {

	private JRadioButton soiree = new JRadioButton("soirée");
	private JRadioButton semaine = new JRadioButton("semaine");
	private JRadioButton mois = new JRadioButton("mois");
	private JRadioButton annee = new JRadioButton("année");
	private static final long serialVersionUID = 1L;

	public ViewStockManagementSouth(final Controller controller) {
		ButtonGroup bg = new ButtonGroup();
		bg.add(soiree);
		bg.add(semaine);
		bg.add(mois);
		bg.add(annee);
		soiree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setDuree("soiree");
			}

		});
		semaine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setDuree("semaine");
			}

		});
		mois.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setDuree("mois");
			}

		});
		annee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setDuree("annee");
			}

		});
		this.add(soiree);
		this.add(semaine);
		this.add(mois);
		this.add(annee);

	}

}
