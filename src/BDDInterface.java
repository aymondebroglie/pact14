//pour tous tables,on manque les méthodes affichant des dennées (attent des interfaces des autres modules)
public interface BDDInterface 
{

//Goulots
	boolean ajouterGoulot(int bluetooth);
	public boolean modifierGoulot(int bluetooth,int enCharge, float niveauDeCharge);
	public boolean supprimerGoulot(int bluetooth);
//Barman
	boolean ajouterBarman(String rFID,String nom,String prenom,int age,String dateEmbauche
			,int cPK);
	public boolean modifierInformationBarman(String rFID,String nom,String prenom,int age,String dateEmbauche
			,int cPK);
	public boolean modifierCommandeDeBarman(String rFID,int cPK);
	public boolean supprimerBarman(String rFID);
//Boisson
    public boolean ajouterBoisson(int codeBarre,String nom,String marque, int degre);
//Cocktail
    
//Commande
	boolean ajouterConsommation(int bluetoothID, int rFID, int volume );
	boolean finDeCommande(int rFID);
//Stock
	
}
