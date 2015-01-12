package windows;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class ViewBottlesManagement extends JPanel 
{

	private static final long serialVersionUID = 1L;

	// Base de donnée fournit type de bouteille et quantité disponible et tou
	// les autres paramètres (lequel?)
	
	public ViewBottlesManagement() {
		Object[][] donnees = { { "Vodka", 3 }, { "gin", 4 } };
		String [] etiquettes = {"Boisson","Quantité"};
		add(new JTable(donnees,etiquettes));

	}
}
