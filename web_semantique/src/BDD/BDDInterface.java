package BDD ;

import java.util.ArrayList;

//pour tous tables,on manque les méthodes affichant des dennées (attent des interfaces des autres modules)
//Remarquez le type Date utilisé. Si vous voulez mettre la date actuelle, mettez juste new java.util.Date(), sinon allez voir la doc
public interface BDDInterface 
{

//Goulots
	boolean ajouterGoulot(int bluetooth);
	public boolean modifierGoulot(int bluetooth,int enCharge, float niveauDeCharge);
	public boolean supprimerGoulot(int bluetooth);
	boolean associerGoulot(int blutoothID, long codeBarre);
//Barman
	boolean ajouterBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche
			,int cPK);
	public boolean modifierInformationBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche
			,int cPK);
	public boolean modifierCommandeDeBarman(int rFID,int cPK);
	public boolean supprimerBarman(int rFID);
//Boisson
    public boolean ajouterBoisson(long codeBarre,String nom, int degre);
//Cocktail
    
//Commande
	boolean ajouterConsommation(int bluetoothID, int rFID, int volume ); //il faut mettre le volume en cL, un entier.
	boolean finDeCommande(int rFID);
//Stock
	boolean bouteilleFinie(int bluetoothID);
	boolean livraison(ArrayList<Livraison> livraison);
}
