package windows;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class ViewBottlesManagement extends JPanel 
{

	private static final long serialVersionUID = 1L;

	// Base de donn�e fournit type de bouteille et quantit� disponible et tou
	// les autres param�tres (lequel?)
	
	public ViewBottlesManagement() {
		Object[][] donnees = { { "Vodka", 3 }, { "gin", 4 } };
		String [] etiquettes = {"Boisson","Quantit�"};
		add(new JTable(donnees,etiquettes));

	}
}
