package prise_en_main;

import openFoodFacts.OpenFoodFacts;
import BDD.BDDInterface;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MainTest {

	public static void main(String[] args) 
	{
		BDDInterface bdd = null;
		
		OpenFoodFacts openFoodFacts = new OpenFoodFacts(bdd);
		
		long codebarre = 3147690059004L ;
		ResultSet result = openFoodFacts.getResults(codebarre);
		
		String name = openFoodFacts.getName(result);
		
		
		System.out.println(name);
	}

}
