package ui;

import bdd.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import visu.graphique;

//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
//interrogera la base de donne�e
public class Controller {
	private BDDInterface bdd;
	private Window window;
	private String duree;// Ce tableau permet de connaitre la duree a afficher

	/** private String temps = "mois"; */

	public Controller(Window window, BDDInterface bdd) {
		this.window = window;
		this.bdd = bdd;
		this.loadMDP();
	}

	public BDDInterface getBDD() {
		return this.bdd;
	}

	/****************************************************************************************************/
	/** MDP */
	String currentcryptedMDP;

	public void setMDP(String newcryptedMDP) {
		this.currentcryptedMDP = newcryptedMDP;
	}

	public void loadMDP() {
		FileReader fs = null;
		BufferedReader bs = null;
		String filename = "datas" + File.separator + "Password";

		try {
			fs = new FileReader(filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		bs = new BufferedReader(fs);

		try {
			String savedcryptedMDP = bs.readLine();
			this.setMDP(savedcryptedMDP);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	public void printMDP(char[] cs) {
		String filename = "datas" + File.separator + "Password";
		String word = String.valueOf(cs);
		String savingcryptedMDP = String.valueOf(word.hashCode());
		try {
			PrintWriter ps = new PrintWriter(filename);
			ps.print(savingcryptedMDP);
			ps.close();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}

		this.setMDP(savingcryptedMDP);

	}

	// teste juste l'egalite du mot de passe et de la chaine entree
	private Boolean verifMDP(char[] cs) {
		String word = String.valueOf(cs);
		String cryptedword = Integer.toString(word.hashCode());
		return (cryptedword.equals(currentcryptedMDP));
	}

	// changer de mot de passe

	// renvoie sur la page de chgt de MDP
	public void changementMDP() {
		ViewChangePassword cp = new ViewChangePassword(this);
		window.setContentPane(cp);
		window.validate();
	}

	// prend en charge le chgt de mot de passe apres remplissage des champs
	// necessaires et validation.
	public void changerMDP(char[] cs0, char[] cs1, char[] cs2) {
		if (this.verifMDP(cs0)) {
			if (Arrays.equals(cs1, cs2)) {
				JPanel panneau = new JPanel();

				this.printMDP(cs1);
				JOptionPane.showMessageDialog(null,
						"Mot de passe changé avec succès !", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				panneau.add(new JLabel("Mot de Passe changé"));
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

	public void motDePasse(char[] cs) {
		if (this.verifMDP(cs)) {
			ViewBossHome vbh = new ViewBossHome(this);
			window.setContentPane(vbh);
			window.validate();
		} else {
			JOptionPane.showMessageDialog(null, "Mot de passe invalide.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************************************************/
	/** Navigation */
	private Stack<JPanel> stackpreviousview = new Stack<JPanel>();
	private Stack<JPanel> stacknextview = new Stack<JPanel>();
	private JPanel actualview;

	public void setActualView(JPanel actualview) {
		// System.out.println("before :" + actualview) ;
		this.actualview = actualview;
		System.out.println("actual :" + actualview);
	}

	public void clearStacks() {
		while (!stackpreviousview.empty()) {
			stackpreviousview.pop();
		}
		System.out.println("is stackpreviousview empty ?"
				+ stackpreviousview.empty());
		while (!stacknextview.empty()) {
			stacknextview.pop();
		}
		System.out.println("is stacknextview empty ?" + stacknextview.empty());
		// this.setActualView(null);
	}

	public JPanel getActualView() {
		return actualview;
	}

	public void addPreviousView(JPanel previousview) {
		stackpreviousview.push(previousview);

	}

	public void previousView(JPanel actualview) {
		if (stackpreviousview.empty()) {
			JOptionPane.showMessageDialog(null,
					"L'opération demandée est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JPanel previousview = stackpreviousview.pop();
			stacknextview.push(actualview);
			System.out.println("new next : " + actualview);
			this.setActualView(previousview);
			window.setContentPane(previousview);
			window.validate();
		}
	}

	public void nextView(JPanel actualview) {
		if (stacknextview.empty()) {
			JOptionPane.showMessageDialog(null,
					"L'opération demandée est impossible", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else {
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
	private void fonctionnaliteNonImplementee(String methodname) {
		System.out.println("WARNING : the method " + methodname
				+ "hasn't been implemented yet !!!");
		JOptionPane.showMessageDialog(null,
				"L'opération demandée n'est pas encore disonible.",
				"Attention", JOptionPane.WARNING_MESSAGE);
	}

	public void aProposOptibar() {
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

	public void afficherAide() {
		this.fonctionnaliteNonImplementee("afficherAide()");

	}

	public void changementSystemeMetrique() {
		this.fonctionnaliteNonImplementee("changementSystemeMetrique()");

	}

	public void changementSystemeMonetaire() {
		this.fonctionnaliteNonImplementee("changementSystemeMonetaire()");

	}

	public void changementLanguage() {
		this.fonctionnaliteNonImplementee("changementLanguage()");

	}

	/****************************************************************************************************/
	/** ViewWelcome */
	public void boutonBarman() {
		// Methode appelee quand on appuie sur Barman sur l'ecran d'accueil
		this.addPreviousView(actualview);
		ViewBarmanHome vbh = new ViewBarmanHome(this);
		window.setContentPane(vbh);
		window.validate();
	}

	public void boutonGestionnaire() {
		// Methode appelee quand on appuie sur Barman sur l'ecran d'accueil
		this.addPreviousView(actualview);
		ViewBossLogin vbh = new ViewBossLogin(this);
		window.setContentPane(vbh);
		window.validate();
	}

	/****************************************************************************************************/
	/** ViewBarmanHome */
	public void imprimerNote() {
		// Methode appelee si on appuie sur imprimer note dans l'ecran du Barman
		// Code pour le test, il faudra demander a la base de donnee de nous
		// fournir la note pour le serveur donne

		ArrayList<DetailDeCommand> list = bdd.imprimerCommande(1);// suposse
																	// rfid ets
																	// 1
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Pas de commande en cours pour vous", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else {
			ViewNote pan = new ViewNote(this);
			window.setContentPane(pan);
			this.setActualView(pan);
			window.validate();
		}
	}

	public void retirerGoulot() {
		// Demander quel goulot il faut prendre pour l'instant il ferme la
		// fenetre pour le test
		this.addPreviousView(actualview);
		ViewRetirerGoulot pan = new ViewRetirerGoulot(this);
		window.setContentPane(pan);
		this.setActualView(pan);
		window.validate();

	}

	public void attribution(ArrayList<String> tableauAffichage) {
		int goulot = bdd.attributionDeGoulot();
		ViewRetirerGoulot pan = new ViewRetirerGoulot(this);
		if (tableauAffichage.size() != 1)
			pan.add(new JLabel("Veuillez choisir une boisson SVP"));
		else if (goulot == 0)
			pan.add(new JLabel("Pas de goulots disponible"));
		else {
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
	public void consulterVosDonnees() {
		this.addPreviousView(actualview);
		ViewSeeDatas vsd = new ViewSeeDatas(this);
		window.setContentPane(vsd);
		window.validate();
	}

	public void gestionStocks() {
		ViewDelivery vsm = new ViewDelivery(this);
		window.setContentPane(vsm);
		window.validate();
	}

	/****************************************************************************************************/

	public void obtenirstock() {
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

	public void commande() {
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

	public void budget() {

	}

	public ArrayList<String> obtenirAlcools() {
		return bdd.listeDesBoissons();
	}

	public void visualiser(ViewStocksManagement vsm) {
		long dayMilli = 86400000L;
		Date maintenant = new Date(), debut;
		graphique g = null;
		if (duree != null) {
			switch (duree) {
			case "soiree":
				debut = new Date(maintenant.getTime() - dayMilli);
				break;
			case "semaine":
				debut = new Date(maintenant.getTime() - dayMilli * 7);
				break;
			case "mois":
				debut = new Date(maintenant.getTime() - dayMilli * 30);
				break;
			case "annee":
				debut = new Date(maintenant.getTime() - dayMilli * 365);
				break;
			default:
				debut = new Date(0);
			}
			ArrayList<ArrayList<HistoBoisson>> data = new ArrayList<ArrayList<HistoBoisson>>();

			ArrayList<String> tableauAffichage = vsm.obtenirBouttonAlcool();
			for (String nom : tableauAffichage) {
				data.add(bdd.evolutionDesStocks(nom, debut, maintenant));
			}
			g = new graphique(data, tableauAffichage);

			JFrame f = new JFrame();
			f.setBounds(10, 10, 500, 500);
			f.add(g);
			f.setVisible(true);
		}
		/*
		 * vsm.add(g); window.setContentPane(vsm); window.validate();
		 */
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

	public void etatDesStocks() {
		Date maintenant = new Date();
		graphique g = new graphique(bdd.etatDesStocks(maintenant), maintenant);
		JFrame f = new JFrame();
		f.setBounds(10, 10, 500, 500);
		f.add(g);
		f.setVisible(true);
	}

	public void bouteilleFinie() {
		bdd.bouteilleFinie(1);
		window.add(new JLabel("Changement enregistré"), BorderLayout.CENTER);
		window.validate();
	}

	public void ajoutBouteille(String codeBarre) {
		JPanel pan = new JPanel();
		long code = Long.parseLong(codeBarre);
		System.out.println(code);
		bdd.ajouterBoissonParWeb(code);
		pan.add(new JLabel("Boisson ajoutée : " + codeBarre));
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
		ViewDelivery vd = new ViewDelivery(this);
		window.setContentPane(vd);
		window.validate();
		System.out.println(boisson + nombre);
	}

	public void ecranAjoutBouteille() {
        ViewAddBottle vab = new ViewAddBottle(this);
        window.setContentPane(vab);
        window.validate();
	}
}
