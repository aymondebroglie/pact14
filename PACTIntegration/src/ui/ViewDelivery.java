package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewDelivery extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ArrayList<String> tableauAlcool;
    private String alcoolSelectionne;
	public ViewDelivery(final Controller controller) {
		this.controller = controller;
		tableauAlcool = controller.obtenirAlcools();;
		JComboBox<String> menu = new JComboBox<String>();
		alcoolSelectionne = tableauAlcool.get(0);
		
		menu.setPreferredSize(new Dimension(300,25));
		for (String boisson : tableauAlcool) {
			menu.addItem(boisson);
		}
		menu.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				alcoolSelectionne = (String) arg0.getItem();
			}
			
		});
		this.add(new JLabel("Selectionnez l'alcool livré"));
		this.add(menu);
		final JTextField nombreBouteille = new JTextField("0");
		nombreBouteille.setPreferredSize(new Dimension(300,25));
		this.add(new JLabel("Nombre de Bouteilles"));
		this.add(nombreBouteille);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.livraison(alcoolSelectionne,Integer.parseInt(nombreBouteille.getText()));
				
			}
			
		});
		this.add(ok);
		JButton ajout = new JButton("ajouter nouvelle bouteille");
		ajout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.ecranAjoutBouteille();
				
			}
			
		});
		this.add(ajout);
	}

}
