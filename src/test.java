
public class test {


	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
BDDInterface bdd = new BDD("BAR", "root","12345678"); 
/*bdd.ajouterGoulot(24688);
bdd.modifierGoulot(24688,1,(float) 0.5);
bdd.supprimerGoulot(24688);
*/
/*bdd.ajouterBarman("002", "Yunzhi", "MINAMI", 22, "2015.1.1", 0);
//bdd.modifierInformationBarman("001", "Yunzhi2", "MINAMI2", 21, "2015.1.2", 1); avec problème inconnue.
bdd.modifierCommandeDeBarman("001", 1);
	*/
bdd.ajouterBoisson(201201, "coca", "coca", 20);
	}
}
