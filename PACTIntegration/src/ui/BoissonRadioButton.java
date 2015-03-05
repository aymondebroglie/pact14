package ui;

import javax.swing.JRadioButton;

public class BoissonRadioButton extends JRadioButton{
	private static final long serialVersionUID = 1L;
	private String nom;

	public BoissonRadioButton(String nom) {
		super(nom);
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}
}
