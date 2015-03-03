package prise_en_main;

import java.util.List;
import openFoodFacts.OpenFoodFacts;
import openFoodFactsExceptions.BarCodeException;
import openFoodFactsExceptions.VolumeException;
import BDD.BDDInterface;
import com.hp.hpl.jena.query.QuerySolution;



public class MainTest {

	public static void main(String[] args) throws BarCodeException, VolumeException 
	{
		BDDInterface bdd = null;
		
		OpenFoodFacts openFoodFacts = new OpenFoodFacts(bdd);
		
		// 3147690059004
		long codebarre = 3147690059004L ;
					
			List<QuerySolution> test = openFoodFacts.getResults(codebarre);
			
			System.out.println("test : " + test) ;
			
			String name = openFoodFacts.getName(test) ;
			long code = openFoodFacts.getCode(test) ;
			double degree = openFoodFacts.getDegree(test) ;
			
			System.out.println("name = " + name + " ; codebarre = " + code + " ; degree = " + degree + " ;") ;
			
			/*
			String nametest = "fgfhgddgs rzgzfger gg ggr 00123456 cl ddfgjkdgh dfjkdfhkfdhk" ;			
			
			int volume = openFoodFacts.getVolume(nametest) ;
			System.out.println("volume =" + volume) ;
			
			int inttest = Integer.parseInt("&230") ;
			System.out.println("inttest = " + inttest) ; */
			
			int volume = openFoodFacts.getVolume(name) ;
			System.out.println("volume = " + volume + " cl.") ;
	}

}