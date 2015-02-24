package controller;

import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

import windows.*;


public class Controller 
{
	//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
	//interrogera la base de donn�e
	
	private Window window;

public Controller(Window window)
{
	this.window = window;
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
	pan.add(new JLabel("r�ussi"));
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
	window.dispose();
	
}

/** View Login*/
public void login() 
{
	ViewBossLogin vbl = new ViewBossLogin(this);
	 window.setContentPane(vbl);
	 window.validate();
}

public void motDePasse(char[] cs){
	//Il faut interroger la base de donn�e pour savoir si le mot de passe est le bon 
	if(this.verifMotDePAsse(cs)){
	ViewBossHome vbh = new ViewBossHome(this);
	window.setContentPane(vbh);
	window.validate();
	}
	else{
		ViewBossLogin vbh = new ViewBossLogin(this);
		vbh.add(new JLabel("Faux mdp"));
		window.setContentPane(vbh);
		window.validate();
	}

	
	
}

private Boolean verifMotDePAsse(char[] cs){
	// Cette m�thode sert a verifier que le mot de passe est le bon  
	char vraimdp[] = new char[5];
	//Normalemnt il faut demander � la base de donn�e ici on prend 8 a 
	Arrays.fill(vraimdp, 'a');
	return Arrays.equals(cs,vraimdp);
}

public void changeMotDePasse(char[] cs1, char[] cs2){
	if(Arrays.equals(cs1,cs2)){
		JPanel paneau = new JPanel();
		paneau.add(new JLabel("Mot de Passe change"));
		window.setContentPane(paneau);
		window.validate();
		//Appeler la base de donn�e pour changer le mot de passe
	}
	else{
		JPanel paneau = new JPanel();
		paneau.add(new JLabel("Echec"));
		window.setContentPane(paneau);
		window.validate();
	}
}

public void ecranChangeMotDePasse(){
	ViewChangePassword cp = new ViewChangePassword(this);
	window.setContentPane(cp);
	window.validate();
}


public void obtenirstock(){
	window.dispose();
}

public void commande(){
	
}

public void budget(){
	
}
}




