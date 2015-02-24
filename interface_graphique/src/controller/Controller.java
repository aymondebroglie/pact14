package controller;

import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

import windows.*;


public class Controller 
{
	//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
	//interrogera la base de donnée
	
	private Window window;

public Controller(Window window)
{
	this.window = window;
}

/** ViewWelcome */
public void boutonBarman()
{
	//Méthode appelée quand on appuie sur Barman sur l'écran d'accueil
	
	ViewBarmanHome vbh = new ViewBarmanHome(this);
	 window.setContentPane(vbh);
	 window.validate();
}

public void boutonGestionnaire()
{
	//Méthode appelée quand on appuie sur Barman sur l'écran d'accueil
	
	ViewBossLogin vbh = new ViewBossLogin(this);
	 window.setContentPane(vbh);
	 window.validate();
}

/** ViewBarmanHome */
public void imprimerNote()
{
	//Méthode appelée si on appuie sur imprimer note dans l'écran du Barman
	//Code pour le test, il faudra demander a la base de donnée de nous fournir la note pour le serveur donné
	
	JPanel pan = new JPanel();
	pan.add(new JLabel("réussi"));
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
	//Demander quel goulot il faut prendre pour l'instant il ferme la fenêtre pour le test
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
	//Il faut interroger la base de donnée pour savoir si le mot de passe est le bon 
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
	// Cette méthode sert a verifier que le mot de passe est le bon  
	char vraimdp[] = new char[5];
	//Normalemnt il faut demander à la base de donnée ici on prend 8 a 
	Arrays.fill(vraimdp, 'a');
	return Arrays.equals(cs,vraimdp);
}

public void changeMotDePasse(char[] cs1, char[] cs2){
	if(Arrays.equals(cs1,cs2)){
		JPanel paneau = new JPanel();
		paneau.add(new JLabel("Mot de Passe change"));
		window.setContentPane(paneau);
		window.validate();
		//Appeler la base de donnée pour changer le mot de passe
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




