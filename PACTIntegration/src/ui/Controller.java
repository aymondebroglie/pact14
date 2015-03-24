package ui;

import bdd.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import stat.DataSet;
import stat.DimensionException;
import visu.graphique;
import websem.OpenFoodFacts;

//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
//interrogera la base de donnee

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
		ViewChangePassword vcp = new ViewChangePassword(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vcp) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		
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
				this.printMDP(cs1);
				JOptionPane.showMessageDialog(null,
						"Mot de passe changé avec succès ! Vous allez être redirigé vers l'écran d'accueil.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				
				ViewWelcome vw = new ViewWelcome(this);
				
				Container cp = new Container() ;		
				cp.setLayout(new GridBagLayout());
				cp.add(vw) ;
				window.setContentPane(cp);
				
				this.setActualView(cp);
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
			this.addPreviousView(actualview);
			ViewBossHome vbh = new ViewBossHome(this);
			
			Container cp = new Container() ;		
			cp.setLayout(new GridBagLayout());
			cp.add(vbh) ;
			window.setContentPane(cp);
			
			this.setActualView(cp);
			window.validate();
		} else 
		{
			JOptionPane.showMessageDialog(null, "Mot de passe invalide.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************************************************/
	/** Navigation */
	private Stack<Container> stackpreviousview = new Stack<Container>();
	private Stack<Container> stacknextview = new Stack<Container>();
	private Container actualview;

	public void setActualView(Container actualview) 
	{
		// System.out.println("before :" + actualview) ;
		this.actualview = actualview;
		//System.out.println("actual :" + actualview);
	}

	public void clearNextStacks()
	{
		System.out.println("is stacknextview empty ?"
				+ stacknextview.empty());
		while (!stacknextview.empty()) 
		{

			stacknextview.pop();
		}
		System.out.println("is stacknextview empty ?" + stacknextview.empty());
	}
	
	public void clearPreviousStacks()
	{
		System.out.println("is stackpreviousview empty ?"
				+ stackpreviousview.empty());
		while (!stackpreviousview.empty()) 
		{
			stackpreviousview.pop();
		}

		System.out.println("is stackpreviousview empty ?"
				+ stackpreviousview.empty());
	}
	
	public void clearStacks() 
	{
		this.clearNextStacks();
		this.clearPreviousStacks() ;
	}

	public Container getActualView() 
	{
		return actualview;
	}

	public void addPreviousView(Container previousview) 
	{
		if(!stacknextview.empty())
		{
			this.clearNextStacks() ;
		}
		stackpreviousview.push(previousview);
	}

	public void previousView(Container actualview) 
	{
		System.out.println("test " + stackpreviousview.empty()) ;
		
		if (stackpreviousview.empty()) 
		{
			JOptionPane.showMessageDialog(null,
					"L'opération demandée est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} 
		else 
		{
			Container previousview = stackpreviousview.pop();
			stacknextview.push(actualview);
			this.setActualView(previousview);
			window.setContentPane(previousview);
			window.validate();
		}
	}

	public void nextView(Container actualview) 
	{
		if (stacknextview.empty()) 
		{
			JOptionPane.showMessageDialog(null,
					"L'opération demandée est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else 
		{
			Container nextview = stacknextview.pop();
			stackpreviousview.push(actualview);
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
				"L'opération demandée n'est pas encore disonible.",
				"Attention", JOptionPane.WARNING_MESSAGE);
	}

	public void aProposOptibar() 
	{
		JOptionPane
				.showMessageDialog(
						null,

						"OptiBar a pour objectif de fournir à un bar des outils novateurs et pratiques d'utilisation permettant d'en faciliter la gestion. \n"
								+ "Il permettra au patron de gérer ses stocks et aussi de savoir quelles sont les habitudes de consommation de ses clients de façon claire et précise. \n"
								+ "Puisque la gestion des stocks est une dépense importante pour les bars, notre projet permettra au bar de minimiser ses stocks, sans jamais être à court. \n"
								+ "Enfin, notre système fournit aussi une aide au barman en lui indiquant les quantités qu'il a versées ce qui lui permet à la fois de préparer de meilleures boissons \n "
								+ "mais lui facilite également la production de l'addition, gain de temps toujours utile à l'heure de pointe."

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
		this.clearStacks() ;
		
		ViewBarmanHome vbh = new ViewBarmanHome(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vbh) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	public void boutonGestionnaire() 
	{
		// Methode appelee quand on appuie sur Barman sur l'ecran d'accueil
		this.clearStacks() ;
		
		ViewBossLogin vbh = new ViewBossLogin(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vbh) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
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
			this.addPreviousView(actualview);
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
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(pan) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();

	}

	public void attribution(ArrayList<String> tableauAffichage) 
	{
		
		String goulot = bdd.attributionDeGoulot();
		
		if (tableauAffichage.size() != 1)
		
		JOptionPane.showMessageDialog(null,
				"Veuillez choisir une boisson, s'il-vous-plaît", "Attention",
				JOptionPane.WARNING_MESSAGE);
		else if (goulot == "0000000000")
		{
			JOptionPane.showMessageDialog(null,
				"Pas de goulot disponible", "Attention",
				JOptionPane.WARNING_MESSAGE);
		}
		
		else 
		{
			JOptionPane.showMessageDialog(null,
					"Vous pouvez retirer le goulot" + goulot, "Information",
					JOptionPane.INFORMATION_MESSAGE);
			bdd.associerGoulot(goulot,
			bdd.codeBarreDeBoisson(tableauAffichage.get(0)));
		}
		
	}

	/****************************************************************************************************/
	/** ViewBossHome */
	public void ecranAjoutBarman() {
		this.addPreviousView(actualview);
		ViewAddBarman vab = new ViewAddBarman(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vab) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}
	
	public void consulterVosDonnees() 
	{
		this.addPreviousView(actualview);
		ViewSeeDatas vsd = new ViewSeeDatas(this);

		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vsd) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	public void gestionStocks() 
	{
		this.addPreviousView(actualview);
		ViewDelivery vd = new ViewDelivery(this);
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vd) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	/****************************************************************************************************/
	/** viewSeeDatas */
	public void obtenirstock() 
	{
		this.addPreviousView(actualview);
		
		ViewStocksManagement vsm = new ViewStocksManagement(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vsm) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	public void commande()
	{
		this.addPreviousView(actualview);
		
		ViewCommandManagement vsm = new ViewCommandManagement(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vsm);
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
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

	public void budget() 
	{

	}

	public ArrayList<String> obtenirAlcools() 
	{
		return bdd.listeDesBoissons();
	}
	
	/****************************************************************************************************/
	
	public ArrayList<ArrayList<HistoBoisson>> integrerModelisationStatistique(ArrayList<ArrayList<HistoBoisson>> data) throws DimensionException
	{
			ArrayList<HistoBoisson> liste_histo=data.get(0);				
			int length = liste_histo.size();
			
			ArrayList<Date> liste_date = new ArrayList<Date>() ;
			for(int i = 0 ; i<length ; i++)
			{
				liste_date.add(liste_histo.get(i).getDate()) ;
			}
			
			ArrayList<Integer> liste_volume = new ArrayList<Integer>() ;
			for(int i = 0 ; i<length ; i++)
			{
				liste_volume.add(liste_histo.get(i).getVolume()) ;
			}
			
			DataSet dataset = DataSet.volumeToDataSet(liste_volume) ;
			
			ArrayList<Double> delta = dataset.moindrecarre(liste_date) ;
			
			ArrayList<HistoBoisson> histo_modele = new ArrayList<HistoBoisson>() ;
			for(int i = 0 ; i<length ; i++)
			{
				HistoBoisson histo = new HistoBoisson(liste_date.get(i), (int) (liste_date.get(i).getTime()*delta.get(0) + delta.get(1)) ) ;
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

			
			ArrayList<ArrayList<HistoBoisson>> data= new ArrayList<ArrayList<HistoBoisson>>();
			for(String nom:tableauAffichage)
			{
				data.add(bdd.evolutionDesStocks(nom,debut,maintenant));
			}
			
		
			
			g=new graphique(data,tableauAffichage, true);
		}

		JFrame f = new JFrame();
		f.setBounds(10,10,500,500);
		f.add(g);
		f.setVisible(true);
	}
	
	public void visualiserStat(ViewStocksManagement vsm) throws DimensionException 
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
			ArrayList<ArrayList<HistoBoisson>> data_histo = new ArrayList<ArrayList<HistoBoisson>>() ;
			
		if(tableauAffichage.size()==1)
		{
			data.add(bdd.evolutionDesStocks(tableauAffichage.get(0),debut,maintenant));
			//data.add(bdd.boissonCommande(tableauAffichage.get(0),debut,maintenant));
			
			ArrayList<HistoBoisson> liste_histo=data.get(0);				
			int length = liste_histo.size();
			
			ArrayList<Date> liste_date = new ArrayList<Date>() ;
			for(int i = 0 ; i<length ; i++)
			{
				liste_date.add(liste_histo.get(i).getDate()) ;
			}
			
			ArrayList<Integer> liste_volume = new ArrayList<Integer>() ;
			for(int i = 0 ; i<length ; i++)
			{



				liste_volume.add(liste_histo.get(i).getVolume()) ;

			}
			
			DataSet dataset = DataSet.volumeToDataSet(liste_volume) ;
			
			ArrayList<Double> delta = dataset.moindrecarre(liste_date) ;
			
			ArrayList<HistoBoisson> histo_modele = new ArrayList<HistoBoisson>() ;
			for(int i = 0 ; i<length ; i++)
			{
				HistoBoisson histo = new HistoBoisson(liste_date.get(i), (int) (liste_date.get(i).getTime()*delta.get(0) + delta.get(1)) ) ;
				histo_modele.add(histo) ;
			}
			
			data_histo.add(histo_modele) ;
			
		
		//tableauAffichage.add("Modèle");
	}
		
			
			g=new graphique(data_histo,tableauAffichage, true);
		}

		JFrame f = new JFrame();
		f.setBounds(10,10,500,500);
		f.add(g);
		f.setVisible(true);
		
	}
	
	public void visualiserCommandes(ViewCommandManagement vcm) {
		ArrayList<String> tableauAffichage = vcm.obtenirBouttonAlcool();
		
		long dayMilli=86400000L;
		Date maintenant=new Date(),debut;
		graphique g=null;
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
			ArrayList<ArrayList<HistoBoisson>> data= new ArrayList<ArrayList<HistoBoisson>>();
			for(String nom:tableauAffichage)
			{
				data.add(bdd.boissonCommande(nom,debut,maintenant));
			}
			 g=new graphique(data,tableauAffichage,false);
		
		JFrame f = new JFrame();
		f.setBounds(10,10,500,500);
		f.add(g);
		f.setVisible(true);
		}
	}

	public void setDuree(String duree) 
	{
		this.duree = duree;
	}

	public void bouteilleFinie() 
	{
		bdd.bouteilleFinie("1");
		JOptionPane.showMessageDialog(null,
				"Le changement a bien été enregistré.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void ajoutBouteille(final String codeBarre) 
	{

		this.addPreviousView(actualview);
		final JPanel pan = new JPanel();
		final long code = Long.parseLong(codeBarre);
		System.out.println(code);
		//bdd.ajouterBoissonParWeb(code);
		
		if(OpenFoodFacts.Volume0==0)
		{
		pan.add(new JLabel("<html>Pas de volume, merci d'entrer a la main<br /> Volume de  la boisson <html/>"));
		final JTextField volume1 = new JTextField();
		volume1.setPreferredSize(new Dimension(300,25));
		pan.add(volume1);
		JButton ok3 = new JButton("OK");
		ok3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bdd.setVolumeDeBoisson(Integer.parseInt(volume1.getText()), code);
				JOptionPane.showMessageDialog(null,
						"Boisson ajoutée: "+codeBarre, "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		pan.add(ok3);
		}
		else{JOptionPane.showMessageDialog(null,
				"Boisson ajoutée: "+codeBarre, "Info",
				JOptionPane.INFORMATION_MESSAGE);}
		window.setContentPane(pan);
		this.setActualView(pan);
		window.validate();
	}

	public void ajoutBarman(String nom, String prenom, int age) 
	{
		int rFID = nom.hashCode();
		bdd.ajouterBarman(rFID, nom, prenom, age, new Date());
		JOptionPane.showMessageDialog(null,
				"Barman ajouté", "Information",
				JOptionPane.INFORMATION_MESSAGE);
		window.validate();
	}

	public void livraison(String boisson, int nombre) 
	{
		this.addPreviousView(actualview);
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		livraisons.add(new Livraison(bdd.codeBarreDeBoisson(boisson),nombre));
		bdd.livraison(livraisons);
		
		ViewDelivery vd = new ViewDelivery(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vd) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	public void ecranAjoutBouteille() 
	{
		this.addPreviousView(actualview);
		ViewAddBottle vab = new ViewAddBottle(this);
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vab) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}
	
	public void ecranAssocierGoulot()
	{
		this.addPreviousView(actualview);
		ViewAssocierGoulot vag = new ViewAssocierGoulot(this);

		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vag) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}
	
	public void associerGoulot(String boisson)
	{
		this.addPreviousView(actualview);
		bdd.associerGoulot("1", bdd.codeBarreDeBoisson(boisson));
		JOptionPane.showMessageDialog(null,
				"Le goulot a bien été associé. Vous allez être redirigé vers l'écran d'accueil.", "Information",
				JOptionPane.INFORMATION_MESSAGE);

		ViewBarmanHome vbh = new ViewBarmanHome(this);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vbh) ;
		window.setContentPane(cp);
		
		this.setActualView(cp);
		window.validate();
	}

	
	public void ajoutBouteilleMain(Long codeBarre,String nom,String marque,float degre,int volume )
	{
		JPanel pan = new JPanel();
		bdd.ajouterBoisson(codeBarre, nom, marque, volume, degre);
		pan.add(new JLabel("Boisson ajoutée : " + codeBarre));
		window.setContentPane(pan);
		window.validate();
		
	}

}
