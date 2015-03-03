package main;

import bdd.*;
import websem.*;

public class Optibar {

	public static void main(String[] args) 
	{
		BDDInterface bdd = new BDD("BAR", "root", "12345678");
//        bdd.ajouterBoisson(007, "minami", "ke", 10, 0.1);
		
// test pour WebSemantique,bien marcher. (il faut connecter à internet)
		OFFInterface openfood=new OpenFoodFacts(bdd);
		openfood.ajouterBoisson(3147690059004L);
		
	}

}
