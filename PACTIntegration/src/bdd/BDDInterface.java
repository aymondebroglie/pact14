package bdd;

import java.util.ArrayList;
import java.util.Date;



/*pour tous tables,on manque les méthodes affichant des dennées (attent des interfaces des autres modules)
Remarquez le type Date utilisé. Si vous voulez mettre la date actuelle, mettez juste new Date() (le package est java.util, pas java.sql)
Pour créer une autre date il faut faire comme suit.
	Calendar calendar=Calendar.getInstance();
	Date date = new Date();
calendar.set(2015,01,26,17,00);
date=calendar.getTime();
ATTENTION ici, la date créée est en février, les mois commencent à 0 !!!!!!!!!!!!!
pour utiliser des longs, il faut metter un L à la fin, exemple  :  		long test =3268840001008L;*/
public interface BDDInterface 
{
    boolean initialisation();
    boolean setPrixParBoisson(long codeBarre,float prix);
    
//Goulots
	boolean ajouterGoulot(String bluetooth);
	public boolean modifierGoulot(String bluetooth,int enCharge, float niveauDeCharge);
	public boolean supprimerGoulot(String bluetooth);
	boolean associerGoulot(String blutoothID, long codeBarre);
	public String attributionDeGoulot();
//Barman
	boolean ajouterBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche);
	public boolean modifierInformationBarman(int rFID,String nom,String prenom,int age,java.util.Date dateEmbauche);
	public boolean supprimerBarman(int rFID);
//Boisson
    public boolean ajouterBoisson(long codeBarre,String nom,String marque, int volume, float degre);
    public boolean boissonConnue(long codeBarre);
    public boolean ajouterBoissonParWeb(long codeBarre);
    public long codeBarreDeBoisson(String nom);
    public boolean setVolumeDeBoisson(int volume,long codebarre);
//Cocktail
    public boolean ajoueterCocktail(long coPK,String nom,float prix,ArrayList<DispoBoisson> recette);

//Commande
	boolean ajouterConsommation(String bluetoothID, int rFID, int volume ); //il faut mettre le volume en cL, un entier.
	float finDeCommande(int rFID); //retourne le prix à payer, pour l'instant c'est toujours 0
	public ArrayList<DetailDeCommand> imprimerCommande(int rFID);
//Stock
	boolean bouteilleFinie(String bluetoothID);
	boolean livraison(ArrayList<Livraison> livraison);
//Visu de données
	ArrayList<DispoBoisson> etatDesStocks(java.util.Date date); 
	ArrayList<HistoBoisson> evolutionDesStocks(String boisson, Date dateDebut, Date dateFin); //évolution des stocks pour une boisson donnée.
	ArrayList<String> listeDesBoissons();

	int volumeDateBoisson(java.util.Date date, String boisson);
	

	ArrayList<HistoBoisson> boissonCommande(String boisson, Date dateDebut, Date dateFin);

	
	
}
