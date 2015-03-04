package controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import windows.*;
import BDD.BDDInterface;

//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
//interrogera la base de donn�e
public class Controller
{
	private BDDInterface bdd;
	private Window window;
	private String duree;// Ce tableau permet de connaitre la dur�e � afficher
	/** private String temps = "mois"; */

	public Controller(Window window, BDDInterface bdd) 
	{
		this.window = window ;
		this.bdd = bdd ;
		this.loadMDP() ;
		
	}
	
	/****************************************************************************************************/
	// gestion du mot de passe pour la session gestionnaire
	// attribut + setter
	String currentcryptedMDP ;
	
	public void setMDP(String newcryptedMDP)
	{
		this.currentcryptedMDP = newcryptedMDP ;
	}
	
	public void loadMDP()
	{
		FileReader fs = null ;
        BufferedReader bs = null ;
        String filename = "datas" + File.separator + "Password" ;
		
		try 
		{
			fs = new FileReader(filename) ;
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		bs = new BufferedReader(fs) ;
		
		try 
		{
            String savedcryptedMDP = bs.readLine();   
            this.setMDP(savedcryptedMDP) ;
		}
		catch (Exception e) 
		{
            System.err.println(e) ;
            e.printStackTrace(System.err);
		}
	}
	
	public void printMDP(char[] cs)
	{
		String filename = "datas" + File.separator + "Password" ;
		String word = String.valueOf(cs) ;
		String savingcryptedMDP = String.valueOf(word.hashCode()) ;
		try
        {
			PrintWriter ps = new PrintWriter(filename) ; 
        	ps.print(savingcryptedMDP) ;
        	ps.close() ;
        }
        catch(Exception e)
        {
        	System.err.println(e) ;
            e.printStackTrace(System.err) ;
        }
		
		this.setMDP(savingcryptedMDP) ;
		
	}
	
	// teste juste l'�galit� du mot de passse et de la chaine entr�e
	private Boolean verifMDP(char[] cs)
	{
		String word = String.valueOf(cs) ;
		String cryptedword = Integer.toString(word.hashCode()) ;
		return (cryptedword.equals(currentcryptedMDP));		
	}
	
	// changer de mot de passe
	

	public void changementMDP()
	{
		ViewChangePassword cp = new ViewChangePassword(this);
		window.setContentPane(cp);
		window.validate();
	}
	
	// changer de mot de passe
		public void changerMDP(char[] cs0, char[] cs1, char[] cs2) {
			if (this.verifMDP(cs0)) {
				if (Arrays.equals(cs1, cs2)) {
					JPanel panneau = new JPanel();
					
					this.printMDP(cs1);
					JOptionPane.showMessageDialog(null,
							"Mot de passe chang� avec succ�s !", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					panneau.add(new JLabel("Mot de Passe chang�"));
					window.setContentPane(panneau);
					window.validate();
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Les deux derniers champs saisis ne correspondent pas.",
									"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Mot de Passe Invalide.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
  
	/****************************************************************************************************/
	
	private Stack<JPanel> stackpreviousview = new Stack<JPanel>() ; 
	private Stack<JPanel> stacknextview = new Stack<JPanel>() ;
	private JPanel actualview ;
	
	public void setActualView(JPanel actualview)
	{
		this.actualview = actualview ;
	}
	
	public JPanel getActualView()
	{
		return actualview ;
	}
	
	public void addPreviousView(JPanel previousview)
	{
		stackpreviousview.push(previousview) ;
	}
	
	public void previousView(JPanel actualview)
	{
		if(stackpreviousview.empty())
		{
			JOptionPane.showMessageDialog(null, "L'op�ration demand�e est impossible", "Attention", JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			JPanel previousview = stackpreviousview.pop() ;
			stacknextview.push(actualview) ;
			window.setContentPane(previousview);
			window.validate();
		}
	}
	
	public void nextView(JPanel actualview)
	{
		if(stacknextview.empty())
		{
			JOptionPane.showMessageDialog(null, "L'op�ration demand�e est impossible", "Attention", JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			JPanel nextview = stacknextview.pop() ;
			stackpreviousview.push(actualview) ;
			window.setContentPane(nextview);
			window.validate();
		}
	}

	
	/****************************************************************************************************/

	


/** ViewWelcome */
public void boutonBarman()
{
	//M�thode appel�e quand on appuie sur Barman sur l'�cran d'accueil
	ViewBarmanHome vbh = new ViewBarmanHome(this);
	this.setActualView(vbh) ;
	window.setContentPane(vbh);
	window.validate();
}

public void boutonGestionnaire()
{
	//M�thode appel�e quand on appuie sur Barman sur l'�cran d'accueil
	ViewBossLogin vbh = new ViewBossLogin(this);
	window.setContentPane(vbh);
	this.setActualView(vbh) ;
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
	this.setActualView(pan) ;
	window.validate();
}

/** ViewBossHome */
public void consulterVosDonnees() 
{
	ViewSeeDatas vsd = new ViewSeeDatas(this);
	 window.setContentPane(vsd);
	 this.setActualView(vsd) ;
	 window.validate();
}

public void gestionStocks() 
{
	ViewStocksManagement vsm = new ViewStocksManagement(this);
	 window.setContentPane(vsm);
	 this.setActualView(vsm) ;
	 window.validate();
}

public void retirerGoulot()
{
	//Demander quel goulot il faut prendre pour l'instant il ferme la fen�tre pour le test
	JPanel pan = new JPanel();
	pan.add(new JLabel("test retirerGoulot() r�ussi"));
	window.setContentPane(pan);
	this.setActualView(pan) ;
	window.validate();
	
}

/** View Login*/
public void login() 
{
	ViewBossLogin vbl = new ViewBossLogin(this);
	 window.setContentPane(vbl);
	 this.setActualView(vbl) ;
	 window.validate();
}


		

public void motDePasse(char[] cs)
{
	if(this.verifMDP(cs))
	{
		ViewBossHome vbh = new ViewBossHome(this);
		window.setContentPane(vbh);
		this.setActualView(vbh) ;
		window.validate();
	}
	else
	{
		JOptionPane.showMessageDialog(null, "Mot de passe invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}
	

	



	public void obtenirstock() {
		ViewStocksManagement vsm = new ViewStocksManagement(this);
		Container cp = window.getContentPane();
		cp.removeAll();
		window.validate();
		ViewStockManagementSouth pan2 = new ViewStockManagementSouth(this);

		window.add(vsm, BorderLayout.EAST);
		window.add(pan2, BorderLayout.SOUTH);
		window.validate();
	}

	public void commande() {
		ViewCommandManagement vsm = new ViewCommandManagement(this);
		Container cp = window.getContentPane();
		cp.removeAll();
		window.validate();
		ViewStockManagementSouth pan2 = new ViewStockManagementSouth(this);

		window.add(vsm, BorderLayout.EAST);
		window.add(pan2, BorderLayout.SOUTH);
		window.validate();
	}

	public void budget() {

	}

	public ArrayList<String> obtenirAlcools() {
		return bdd.listeDesBoissons();
	}

	public void visualiser(ViewStocksManagement vsm) {
		ArrayList<String> tableauAffichage = vsm.obtenirBouttonAlcool();
		System.out.println(tableauAffichage);
		System.out.println(duree);
	}

	public void visualiserCommandes(ViewCommandManagement vcm) {
		ArrayList<String> tableauAffichage = vcm.obtenirBouttonAlcool();
		System.out.println("Commande");
		System.out.println(tableauAffichage);
		System.out.println(duree);
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}



}
