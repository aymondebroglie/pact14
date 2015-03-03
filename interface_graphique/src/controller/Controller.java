package controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BDD.BDDInterface;
import windows.*;

//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
//interrogera la base de donn�e
public class Controller 
{
	// gestion du mot de passe pour la session gestionnaire
	// attribut + setter
	private char vraimdp[] = new char[5];
	
	public void setMDP(char[] newmdp)
	{
		this.vraimdp = newmdp ;
	}
  
	/****************************************************************************************************/
	
	private BDDInterface bdd;
	private Window window;
	private ArrayList<String> tableauAlcools;//Ce tableau permet de connaitre les alcools � afficher pour la gestion des stocks
	private String temps = "mois";

public Controller(Window window/*,BDDInterface bdd*/)
{
	this.window = window;
	Arrays.fill(vraimdp, 'a');
	this.bdd=bdd;
	this.tableauAlcools = new ArrayList<String>();
}

/** ViewWelcome */
public void boutonBarman()
{
	//M�thode appel�e quand on appuie sur Barman sur l'�cran d'accueil
	
	ViewBarmanHome vbh = new ViewBarmanHome(this);
	 window.setContentPane(vbh);
	 window.validate();
}

public void boutonGestionnaire()
{
	//M�thode appel�e quand on appuie sur Barman sur l'�cran d'accueil
	ViewBossLogin vbh = new ViewBossLogin(this);
	window.setContentPane(vbh);
	window.validate();
}

/** ViewBarmanHome */
public void imprimerNote()
{
	//M�thode appel�e si on appuie sur imprimer note dans l'�cran du Barman
	//Code pour le test, il faudra demander a la base de donn�e de nous fournir la note pour le serveur donn�
	
	JPanel pan = new JPanel();
	pan.add(new JLabel("test imprimerNote() r�ussi"));
	window.setContentPane(pan);
	window.validate();
}

/** ViewBossHome */
public void consulterVosDonnees() 
{
	ViewSeeDatas vsd = new ViewSeeDatas(this);
	 window.setContentPane(vsd);
	 window.validate();
}

public void gestionStocks() 
{
	ViewStocksManagement vsm = new ViewStocksManagement(this);
	 window.setContentPane(vsm);
	 window.validate();
}

public void retirerGoulot()
{
	//Demander quel goulot il faut prendre pour l'instant il ferme la fen�tre pour le test
	JPanel pan = new JPanel();
	pan.add(new JLabel("test retirerGoulot() r�ussi"));
	window.setContentPane(pan);
	window.validate();
	
}

/** View Login*/
public void login() 
{
	ViewBossLogin vbl = new ViewBossLogin(this);
	 window.setContentPane(vbl);
	 window.validate();
}

// v�rifie si la chaine de caract�res en entr�e correspond avec 
// le mot de passe en vigueur et retourne juste une fen�tre d'erreur sinon
public void motDePasse(char[] cs){

	if(this.verifMotDePasse(cs)){
	ViewBossHome vbh = new ViewBossHome(this);
	window.setContentPane(vbh);
	window.validate();
	}
	else{
		//Bo�te du message pr�ventif		
		JOptionPane.showMessageDialog(null, "Mot de passe invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	
	
}

// teste juste l'�galit� du mot de passse et de la chaine entr�e
private Boolean verifMotDePasse(char[] cs)
{
	return Arrays.equals(cs,vraimdp);
}

// changer de mot de passe
public void changeMotDePasse(char[] cs0, char[] cs1, char[] cs2){
	if(this.verifMotDePasse(cs0))
	{
		if(Arrays.equals(cs1,cs2))
		{
			JPanel panneau = new JPanel();
			this.setMDP(cs1);
			JOptionPane.showMessageDialog(null, "Mot de passe chang� avec succ�s !", "Information", JOptionPane.INFORMATION_MESSAGE);
			panneau.add(new JLabel("Mot de Passe chang�"));
			window.setContentPane(panneau);
			window.validate();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Les deux derniers champs saisis ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	else
	{
		JOptionPane.showMessageDialog(null, "Mot de Passe Invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}

public void ecranChangeMotDePasse(){
	ViewChangePassword cp = new ViewChangePassword(this);
	window.setContentPane(cp);
	window.validate();
}


public void obtenirstock(){
	ViewStocksManagement vsm = new ViewStocksManagement(this);
	Container cp = window.getContentPane();
	cp.removeAll();
	window.validate();
	ViewStockManagementSouth pan2 = new ViewStockManagementSouth(this);
	
	window.add(vsm,BorderLayout.EAST);
	window.add(pan2,BorderLayout.SOUTH);
	window.validate();
}

public void commande(){
	
}

public void budget(){
	
}

public void ajoutAlcool(String nom){
	tableauAlcools.add(nom);
    System.out.println(tableauAlcools);
}

public void enleverAlcool(String nom){
	for (String boisson : tableauAlcools){
		if (boisson == nom){
			tableauAlcools.remove(boisson);
		}
	}
	System.out.println(tableauAlcools);
}

public ArrayList<String> obtenirAlcools(){
	return bdd.listeDesBoissons();
}

}




