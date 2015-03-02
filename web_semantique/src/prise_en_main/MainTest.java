package prise_en_main;

import java.net.URISyntaxException;
import java.util.List;

import openFoodFacts.BarCodeException;
import openFoodFacts.OpenFoodFacts;
import BDD.BDDInterface;






import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


public class MainTest {

	public static void main(String[] args) throws BarCodeException 
	{
		BDDInterface bdd = null;
		
		OpenFoodFacts openFoodFacts = new OpenFoodFacts(bdd);
		
		// 3147690059004
		long codebarre = 3147690059004L ;
		ResultSet result;
		

			
			List<QuerySolution> test = openFoodFacts.getResults(codebarre);
			
			System.out.println("test : " + test) ;
			
			String name = openFoodFacts.getName(test) ;
			long code = openFoodFacts.getCode(test) ;
			double degree = openFoodFacts.getDegree(test) ;
			
			System.out.println("name = " + name + " ; codebarre = " + code + " ; degree = " + degree + " ;") ;
	}

}
