package ui;

import bdd.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import stat.DataSet;
import stat.DimensionException;
import visu.graphique;
import websem.OpenFoodFacts;

//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
//interrogera la base de donne�e

public class Controller
{
	private  BDDInterface bdd;
	private Window window;
	private String duree;// Ce tableau permet de connaitre la duree a afficher

	/** private String temps = "mois"; */

	public Controller(Window window, BDDInterface bdd) 
	{
		this.window = window;
		this.bdd = bdd;
		this.loadMDP();
	}

	public BDDInterface getBDD() 
	{
		return this.bdd;
	}

	/****************************************************************************************************/
	/** MDP */
	String currentcryptedMDP;

	public void setMDP(String newcryptedMDP) 
	{
		this.currentcryptedMDP = newcryptedMDP;
	}

	public void loadMDP() 
	{
		FileReader fs = null;
		BufferedReader bs = null;
		String filename = "datas" + File.separator + "Password";

		try 
		{
			fs = new FileReader(filename);
		}
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}

		bs = new BufferedReader(fs);

		try 
		{
			String savedcryptedMDP = bs.readLine();
			this.setMDP(savedcryptedMDP);
		} 
		catch (Exception e) 
		{
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	public void printMDP(char[] cs) 
	{
		String filename = "datas" + File.separator + "Password";
		String word = String.valueOf(cs);
		String savingcryptedMDP = String.valueOf(word.hashCode());
		try 
		{
			PrintWriter ps = new PrintWriter(filename);
			ps.print(savingcryptedMDP);
			ps.close();
		} catch (Exception e) 
		{
			System.err.println(e);
			e.printStackTrace(System.err);
		}

		this.setMDP(savingcryptedMDP);

	}

	// teste juste l'egalite du mot de passe et de la chaine entree
	private Boolean verifMDP(char[] cs) 
	{
		String word = String.valueOf(cs);
		String cryptedword = Integer.toString(word.hashCode());
		return (cryptedword.equals(currentcryptedMDP));
	}

	// changer de mot de passe

	// renvoie sur la page de chgt de MDP
	public void changementMDP() 
	{
		ViewChangePassword cp = new ViewChangePassword(this);
		window.setContentPane(cp);
		window.validate();
	}

	// prend en charge le chgt de mot de passe apres remplissage des champs
	// necessaires et validation.
	public void changerMDP(char[] cs0, char[] cs1, char[] cs2) 
	{
		if (this.verifMDP(cs0)) 
		{
			if (Arrays.equals(cs1, cs2)) 
			{
				JPanel panneau = new JPanel();

				this.printMDP(cs1);
				JOptionPane.showMessageDialog(null,
						"Mot de passe changé avec succès !", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				panneau.add(new JLabel("Mot de Passe changé"));
				window.setContentPane(panneau);
				window.validate();
			} else 
			{
				JOptionPane
						.showMessageDialog(
								null,
								"Les deux derniers champs saisis ne correspondent pas.",
								"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else 
		{
			JOptionPane.showMessageDialog(null, "Mot de Passe Invalide.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void motDePasse(char[] cs) 
	{
		if (this.verifMDP(cs)) 
		{
			ViewBossHome vbh = new ViewBossHome(this);
			window.setContentPane(vbh);
			window.validate();
		} else 
		{
			JOptionPane.showMessageDialog(null, "Mot de passe invalide.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************************************************/
	/** Navigation */
	private Stack<JPanel> stackpreviousview = new Stack<JPanel>();
	private Stack<JPanel> stacknextview = new Stack<JPanel>();
	private JPanel actualview;

	public void setActualView(JPanel actualview) 
	{
		// System.out.println("before :" + actualview) ;
		this.actualview = actualview;
		System.out.println("actual :" + actualview);
	}

	public void clearStacks() 
	{
		while (!stackpreviousview.empty()) 
		{
			stackpreviousview.pop();
		}
		System.out.println("is stackpreviousview empty ?"
				+ stackpreviousview.empty());
		while (!stacknextview.empty()) 
		{
			stacknextview.pop();
		}
		System.out.println("is stacknextview empty ?" + stacknextview.empty());
		// this.setActualView(null);
	}

	public JPanel getActualView() 
	{
		return actualview;
	}

	public void addPreviousView(JPanel previousview) 
	{
		stackpreviousview.push(previousview);

	}

	public void previousView(JPanel actualview) 
	{
		if (stackpreviousview.empty()) 
		{
			JOptionPane.showMessageDialog(null,
					"L'op�ration demand�e est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} 
		else 
		{
			JPanel previousview = stackpreviousview.pop();
			stacknextview.push(actualview);
			System.out.println("new next : " + actualview);
			this.setActualView(previousview);
			window.setContentPane(previousview);
			window.validate();
		}
	}

	public void nextView(JPanel actualview) 
	{
		if (stacknextview.empty()) 
		{
			JOptionPane.showMessageDialog(null,
					"L'op�ration demandée est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else 
		{
			JPanel nextview = stacknextview.pop();
			stackpreviousview.push(actualview);
			System.out.println("new previous : " + actualview);
			this.setActualView(nextview);
			window.setContentPane(nextview);
			window.validate();
		}
	}

	/*
	 * L'idee est la suivante :
	 * 
	 * 1) a chaque fois qu'on quitte un page par un lien normal (sans passer par
	 * les outils de navigations), il faut ajouter la page quittee a la pile
	 * stackpreviousview. 2) on renseigne au controller la nouvelle page sur
	 * laquelle on se trouve : elle est stockee dans l'attribut actualview ; 3)
	 * si on decide de faire marche arriere la page quittee est stockee dans
	 * stacknextview. 4) si on decide de faire un retour arriere apres 3), la
	 * page quittee est de nouveau stockee dans stacknextview
	 * 
	 * Autrement dit :
	 * 
	 * 1) actual view -> stackpreviousview 2) actual view <- newactualview 3)
	 * actual view -> stacknextview 4) actual view -> stackpreviousview
	 */
	/****************************************************************************************************/
	/** Fonctionnalites barre de menu */
	private void fonctionnaliteNonImplementee(String methodname) 
	{
		System.out.println("WARNING : the method " + methodname
				+ "hasn't been implemented yet !!!");
		JOptionPane.showMessageDialog(null,
				"L'op�ration demand�e n'est pas encore disonible.",
				"Attention", JOptionPane.WARNING_MESSAGE);
	}

	public void aProposOptibar() 
	{
		JOptionPane
				.showMessageDialog(
						null,

						"OptiBar a pour objectif de fournir � un bar des outils novateurs et pratiques d'utilisation permettant d'en faciliter la gestion. \n"
								+ "Il permettra au patron de g�rer ses stocks et aussi de savoir quelles sont les habitudes de consommation de ses clients de fa�on claire et pr�cise. \n"
								+ "Puisque la gestion des stocks est une d�pense importante pour les bars, notre projet permettra au bar de minimiser ses stocks, sans jamais �tre �court. \n"
								+ "Enfin, notre syst�me fournit aussi une aide au barman en lui indiquant les quantit�s qu'il a vers�es ce qui lui permet �la fois de pr�parer de meilleures boissons \n "
								+ "mais lui facilite �galement la production de l'addition, gain de temps toujours utile �l'heure de pointe."

						, "A propos d'OptiBar", JOptionPane.INFORMATION_MESSAGE);

	}

	public void afficherAide() 
	{
		this.fonctionnaliteNonImplementee("afficherAide()");

	}

	public void changementSystemeMetrique() 
	{
		this.fonctionnaliteNonImplementee("changementSystemeMetrique()");

	}

	public void changementSystemeMonetaire() 
	{
		this.fonctionnaliteNonImplementee("changementSystemeMonetaire()");

	}

	public void changementLanguage() 
	{
		this.fonctionnaliteNonImplementee("changementLanguage()");

	}

	/****************************************************************************************************/
	/** ViewWelcome */
	public void boutonBarman() 
	{
		// Methode appelee quand on appuie sur Barman sur l'ecran d'accueil
		this.addPreviousView(actualview);
		ViewBarmanHome vbh = new ViewBarmanHome(this);
		window.setContentPane(vbh);
		window.validate();
	}

	public void boutonGestionnaire() 
	{
		// Methode appelee quand on appuie sur Barman sur l'ecran d'accueil
		this.addPreviousView(actualview);
		ViewBossLogin vbh = new ViewBossLogin(this);
		window.setContentPane(vbh);
		window.validate();
	}

	/****************************************************************************************************/
	/** ViewBarmanHome */
	public void imprimerNote() 
	{
		// Methode appelee si on appuie sur imprimer note dans l'ecran du Barman
		// Code pour le test, il faudra demander a la base de donnee de nous
		// fournir la note pour le serveur donne

		ArrayList<DetailDeCommand> list = bdd.imprimerCommande(1);// suposse
																	// rfid ets
																	// 1
		if (list.size() == 0) 
		{
			JOptionPane.showMessageDialog(null,
					"Pas de commande en cours pour vous", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} 
		else 
		{
			ViewNote pan = new ViewNote(this);
			window.setContentPane(pan);
			this.setActualView(pan);
			window.validate();
		}
	}

	public void retirerGoulot() 
	{
		// Demander quel goulot il faut prendre pour l'instant il ferme la
		// fenetre pour le test
		this.addPreviousView(actualview);
		ViewRetirerGoulot pan = new ViewRetirerGoulot(this);
		window.setContentPane(pan);
		this.setActualView(pan);
		window.validate();

	}

	public void attribution(ArrayList<String> tableauAffichage) 
	{
		int goulot = bdd.attributionDeGoulot();
		ViewRetirerGoulot pan = new ViewRetirerGoulot(this);
		if (tableauAffichage.size() != 1)
			pan.add(new JLabel("Veuillez choisir une boisson SVP"));
		else if (goulot == 0)
			pan.add(new JLabel("Pas de goulots disponible"));
		else 
		{
			pan.add(new JLabel("Vous pouvez retirer le goulot " + goulot));
			bdd.associerGoulot(goulot,
					bdd.codeBarreDeBoisson(tableauAffichage.get(0)));
		}
		window.setContentPane(pan);
		this.setActualView(pan);
		window.validate();
	}

	/****************************************************************************************************/
	/** ViewBossHome */
	public void consulterVosDonnees() 
	{
		this.addPreviousView(actualview);
		ViewSeeDatas vsd = new ViewSeeDatas(this);
		window.setContentPane(vsd);
		window.validate();
	}

	public void gestionStocks() 
	{
		ViewDelivery vsm = new ViewDelivery(this);
		window.setContentPane(vsm);
		window.validate();
	}

	/****************************************************************************************************/

	public void obtenirstock() 
	{
		this.addPreviousView(actualview);
		ViewStocksManagement vsm = new ViewStocksManagement(this);
		Container cp = window.getContentPane();
		cp.removeAll();
		window.validate();
		ViewStockManagementSouth pan2 = new ViewStockManagementSouth(this);

		window.add(vsm, BorderLayout.EAST);
		window.add(pan2, BorderLayout.SOUTH);
		window.validate();
	}

	public void commande()
	{
		this.addPreviousView(actualview);
		ViewCommandManagement vsm = new ViewCommandManagement(this);
		Container cp = window.getContentPane();
		cp.removeAll();
		window.validate();
		ViewStockManagementSouth pan2 = new ViewStockManagementSouth(this);

		window.add(vsm, BorderLayout.EAST);
		window.add(pan2, BorderLayout.SOUTH);
		window.validate();
	}

	public void budget() 
	{

	}

	public ArrayList<String> obtenirAlcools() 
	{
		return bdd.listeDesBoissons();
	}
	
	/****************************************************************************************************/
	
	/**
	private static ArrayList<Date> montee(ArrayList<Date> liste_date, int p)
	{
		ArrayList<Date> result = liste_date ;
		
		int i = p ;
		Date clef = result.get(p) ;
		while( i >= 2 && clef.after(result.get(i/2)))
		{
			result.set(i, result.get(i/2));
			i = i/2 ;
		}
		result.set(i, clef) ;
		
		return result ;

	}
	
	private static ArrayList<Date> descente(ArrayList<Date> liste_date, int q, int p)
	{
		ArrayList<Date> result = liste_date ;
		
		boolean found = false ;
		int indice_grand ;
		int i = q ;
		Date clef = result.get(q) ;
		
		
		while(!found && 2*i <= p)
		{
			if(2*i == p)
			{
				indice_grand = p ;
			}
			else
			{
				if( T[2i]>= T[2i+1] )
				{
					indice_grand = 2*i ;
				}
				else
				{
					indice_grand = 2*p+1 ;
				}
			}
			if(clef<T[indice_grand])
			{
				result.set(i, result.get(indice_grand)) ;
				i = indice_grand ;
			}
			else
			{
				found = true ;
			}
		}
		
		result.set(i, clef) ;
		
		return result ;
	}
	
	private static ArrayList<Date> swap(ArrayList<Date> liste_date , int i , int j)
	{
		ArrayList<Date> result = liste_date ;
		result.set(i, liste_date.get(j)) ;
		result.set(j, liste_date.get(i)) ;
		
		return result ;
		
	}
	
	
	
	private static ArrayList<Date> heapSortDates(ArrayList<Date> liste_date)
	{
		int p ;
		int n = liste_date.size() ;
		ArrayList<Date> result = liste_date ;
		
		
		for(p = 2 ; p<=n ; p++ )
		{
			result = Controller.montee(result, p) ;
		}
		for(p = n ; p>=2 ; p--)
		{
			Controller.swap(result, 1,p) ;
			Controller.descente(liste_date, 1, p-1) ;
		}
		
		return result ;
	}*/
	// ****************************************************************************************
	/**
	private static long getTho(ArrayList<Date> liste_date)
	{
		int length = liste_date.size() ;
		ArrayList<Long> liste_delta = new ArrayList<Long>() ;
		for(int i = 0 ; i<length ; i++)
		{
			liste_delta.add(liste_date.get(i).getTime()) ;
		}
		
		for(int i = 0 ; i<length ; i++)
		{
			liste_delta = liste_data - 
		}
		
		// Controller.heapSortDates(liste_date) ;
		 
		
		int tho ;
		
		
		
		return tho ;
		
	} 
	 * @throws DimensionException */

	
	public ArrayList<ArrayList<HistoBoisson>> integrerModelisationStatistique(ArrayList<ArrayList<HistoBoisson>> data, ViewStocksManagement vsm, ArrayList<String> tableauAffichage) throws DimensionException
	{
			String nom_boisson = tableauAffichage.get(0);
			
			ArrayList<HistoBoisson> liste_histo=data.get(0);				
			ArrayList<Date> liste_date = new ArrayList<Date>() ;
			int length = liste_histo.size();
			
			
			
			for(long i = 0 ; i<length ; i++)
			{
				liste_date.add(liste_histo.get( (int) i).getDate()) ;
			}
			
			Date debut = liste_date.get(0);
			Date fin = liste_date.get(length-1) ;
			
			long n = 60000L ;
			long tho = (fin.getTime()-debut.getTime())/n ;
			
					
			DataSet dataset = new DataSet() ;
			for(long i = 0 ; i<n ; i++)
			{
				Date date = new Date(debut.getTime() + i*tho) ;
				double volume = (double) bdd.volumeDateBoisson(date, nom_boisson) ;
				dataset.add(volume) ;					
			}
			for(int j = 0 ; j<10 ; j++)
			{
				System.out.println(dataset.get(j));
			}
			
			
				ArrayList<Double> delta = dataset.moindrecarre() ;
			
			ArrayList<HistoBoisson> histo_modele = new ArrayList<HistoBoisson>() ;
			for(int i = 0 ; i<length ; i++)
			{
				HistoBoisson histo = new HistoBoisson(liste_date.get(i), (int)((liste_date.get(i).getTime()-debut.getTime())/tho*delta.get(0)+delta.get(1))) ;
				histo_modele.add(histo) ;
			}
			
			data.add(histo_modele) ;
			
		return data;
	}
	
	public void visualiser(ViewStocksManagement vsm) throws DimensionException 
	{
		long dayMilli=86400000L;
		Date maintenant=new Date(),debut;
		graphique g=null;
		ArrayList<String> tableauAffichage = vsm.obtenirBouttonAlcool();
		if(duree!=null)
		{
			switch(duree)
			{
			case "soiree":
				debut=new Date(maintenant.getTime()-dayMilli);
				break;
			case "semaine":
				debut=new Date(maintenant.getTime()-dayMilli*7);
				break;
			case "mois":
				debut=new Date(maintenant.getTime()-dayMilli*30);
				break;
			case "annee":
				debut=new Date(maintenant.getTime()-dayMilli*365);
				break;
			default :
				debut=new Date(0);
			}

			
			ArrayList<ArrayList<HistoBoisson>> data = new ArrayList<ArrayList<HistoBoisson>>();
			

			for(String nom:tableauAffichage)
			{
				data.add(bdd.boissonCommande(nom,debut,maintenant));
			}

			if(vsm.isStat() && tableauAffichage.size()==1)
			{
			data = integrerModelisationStatistique( data, vsm, tableauAffichage) ;
			tableauAffichage.add("Mod�le");
			}
		
			
			g=new graphique(data,tableauAffichage, true);
		}

		JFrame f = new JFrame();
		f.setBounds(10,10,500,500);
		f.add(g);
		f.setVisible(true);
	}
	

	public void setDuree(String duree) 
	{
		this.duree = duree;
	}

	public void etatDesStocks()
	{
		Date maintenant = new Date();
		graphique g = new graphique(bdd.etatDesStocks(maintenant), maintenant);
		JFrame f = new JFrame();
		f.setBounds(10, 10, 500, 500);
		f.add(g);
		f.setVisible(true);
	}

	public void bouteilleFinie() 
	{
		bdd.bouteilleFinie(1);
		window.add(new JLabel("Changement enregistr�"), BorderLayout.CENTER);
		window.validate();
	}

	public void ajoutBouteille(String codeBarre) 
	{
		JPanel pan = new JPanel();
		long code = Long.parseLong(codeBarre);
		System.out.println(code);
		bdd.ajouterBoissonParWeb(code);
		pan.add(new JLabel("Boisson ajout�e : " + codeBarre));
		window.setContentPane(pan);
		window.validate();
	}

	public void ecranAjoutBarman() {
		ViewAddBarman vab = new ViewAddBarman(this);
		window.setContentPane(vab);
		window.validate();
	}

	public void ajoutBarman(String nom, String prenom, int age) {
		int rFID = nom.hashCode();
		bdd.ajouterBarman(rFID, nom, prenom, age, new Date());
		window.add(new JLabel("Barman enregistré"), BorderLayout.CENTER);
		window.validate();
	}

	public void livraison(String boisson, int nombre) {
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		livraisons.add(new Livraison(bdd.codeBarreDeBoisson(boisson),nombre));
		bdd.livraison(livraisons);
		ViewDelivery vd = new ViewDelivery(this);
		window.setContentPane(vd);
		window.add(new JLabel("Livraison enregistrée"));
		window.validate();
	}

	public void ecranAjoutBouteille() {
        ViewAddBottle vab = new ViewAddBottle(this);
        window.setContentPane(vab);
        window.validate();
	}
	
	public void ecranAssocierGoulot(){
		ViewAssocierGoulot vag = new ViewAssocierGoulot(this);
		window.setContentPane(vag);
		window.validate();
	}
	
	public void associerGoulot(String boisson){
		bdd.associerGoulot(1, bdd.codeBarreDeBoisson(boisson));
		ViewBarmanHome vbh = new ViewBarmanHome(this);
		window.setContentPane(vbh);
		window.validate();
	}
}
