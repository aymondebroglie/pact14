package controller;

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
	
	ViewBossHome vbh = new ViewBossHome(this);
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

}
