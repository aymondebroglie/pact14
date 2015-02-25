package prise_en_main;

import java.net.URISyntaxException;

import openFoodFacts.OpenFoodFacts;
import BDD.BDDInterface;



import com.hp.hpl.jena.query.ResultSet;


public class MainTest {

	public static void main(String[] args) 
	{
		BDDInterface bdd = null;
		
		OpenFoodFacts openFoodFacts = new OpenFoodFacts(bdd);
		
		long codebarre = 3147690059004L ;
		ResultSet result;
		
		try 
		{
			
			result = openFoodFacts.getResults(codebarre);
		
		
		String name = openFoodFacts.getName(result);
		
		
		System.out.println(name);
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
