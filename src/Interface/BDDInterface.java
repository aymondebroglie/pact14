package Interface;

import java.util.ArrayList;



/*pour tous tables,on manque les m�thodes affichant des donn�es (attent des interfaces des autres modules)
Remarquez le type Date utilis�. Si vous voulez mettre la date actuelle, mettez juste new Date() (le package est java.util, pas java.sql)
Pour cr�er une autre date il faut faire comme suit.
	Calendar calendar=Calendar.getInstance();
	Date date = new Date();
calendar.set(2015,01,26,17,00);
date=calendar.getTime();
ATTENTION ici, la date cr��e est en f�vrier, les mois commencent � 0 !!!!!!!!!!!!!
pour utiliser des longs, il faut metter un L � la fin, exemple  :  		long test =3268840001008L;*/
public interface BDDInterface 
{

//Goulots
	boolean ajouterGoulot(int bluetooth);
	public boolean modifierGoulot(int bluetooth,int enCharge, float niveauDeCharge);
	public boolean supprimerGoulot(int bluetooth);
	boolean associerGoulot(int blutoothID, long codeBarre);
	public int attributionDeGoulot();
//Barman
	boolean ajouterBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche);
	public boolean modifierInformationBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche);
	public boolean supprimerBarman(int rFID);
//Boisson
    public boolean ajouterBoisson(long codeBarre,String nom,String marque, int volume, int degre);
    public boolean boissonConnue(long codeBarre);
//Cocktail
    public boolean ajoueterCocktail(long coPK,String nom,float prix,ArrayList<DispoBoisson> recette);

//Commande
	boolean ajouterConsommation(int bluetoothID, int rFID, int volume ); //il faut mettre le volume en cL, un entier.
	float finDeCommande(int rFID); //retourne le prix � payer, pour l'instant c'est toujours 0
//Stock
	boolean bouteilleFinie(int bluetoothID);
	boolean livraison(ArrayList<Livraison> livraison);
//Visu de donn�es
	ArrayList<DispoBoisson> etatDesStocks(java.util.Date date); 
	ArrayList<HistoBoisson> evolutionDesStocks(String boisson); //�volution des stocks pour une boisson donn�e.
	ArrayList<String> listeDesBoissons();
	
	
	
}
