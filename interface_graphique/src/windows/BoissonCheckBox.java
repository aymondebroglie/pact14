package windows;

import javax.swing.JCheckBox;

public class BoissonCheckBox extends JCheckBox {
	private static final long serialVersionUID = 1L;
	private String nom;

	public BoissonCheckBox(String nom) {
		super(nom);
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}
}
