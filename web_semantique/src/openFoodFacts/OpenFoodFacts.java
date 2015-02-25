package openFoodFacts;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import BDD.BDDInterface;


public class OpenFoodFacts
{
	BDDInterface bdd;
	
	public OpenFoodFacts(BDDInterface bdd) 
	{
		this.bdd = bdd ;
	}
	
	public ResultSet getResults(long codebarre)
	{
		// Create an empty in-memory model and populate it from the graph
					Model model = ModelFactory.createDefaultModel();
					String backslash= System.getProperty("file.separator") ;
					model.read("C:"+ backslash +"Users"+ backslash +"Achille"+ backslash +"Documents"+ backslash +"Cours"+ backslash +"PACT"+ backslash +"Modules"+ backslash +"Web S�mantique"+ backslash +"fr.openfoodfacts.org.products.rdf"); 
						
					// Create a new query
					String queryString = 
			"select distinct ?s ?name ?codebarre ?degree where "
		+ 	"{"
		+		"graph <fr.openfoodfacts.org.products.rdf> "
		+ 		"{"
		+ 			"?s <http://data.lirmm.fr/ontologies/food#name> ?name."
		+ 			"?s <http://data.lirmm.fr/ontologies/food#code> " + codebarre + "."
		+ 			"?s <http://data.lirmm.fr/ontologies/food#code> ?codebarre."
		+ 			"?s <http://data.lirmm.fr/ontologies/food#alcoholPer100g> ?degree."
		+ 		"}"
		+ 	"}" ;

					Query query = QueryFactory.create(queryString);

					// Execute the query and obtain results
					QueryExecution qe = QueryExecutionFactory.create(query, model);
					ResultSet results = qe.execSelect();

					// Output query results	
					ResultSetFormatter.out(System.out, results, query);

					// Important - free up resources used running the query
					qe.close();
					
					return results ;
	}
	
	// renvoie le nom de la boisson (second r�sultat)
	public String getName(ResultSet results)
	{
		ResultSet m_results = results ;
		m_results.next();
		
		String name = m_results.next().getLiteral("name").getString();
		return name ;
	}
	
	/** lit le nom et en ressort le type */
	public String getTypeAlcohol(String name)
	{
		int i = 0 ;
		
		String X = "x";
		char x ;
			
		while(X.compareTo(" ") != 0)
		{
			x = name.charAt(i); 
			X = String.valueOf(x);
			i++;
		}

		String type = name.substring(0, i) ;
		return type ;
	}
	
	/** lit le nom et en ressort le volume */
	public int getVolume(String name)
	{
		String X = "x";
		char x ;
		
		int i = 0 ;	
		while(X.compareTo(" ") != 0)
		{
			x = name.charAt(i); 
			X = String.valueOf(x);
			i++;
		}
		
		int j = i+1 ;
		while(X.compareTo(" ") != 0)
		{
			x = name.charAt(i); 
			X = String.valueOf(x);
			j++;
		}
		
		String s_volume = name.substring(i,j) ;
		int volume = Integer.parseInt(s_volume);
		
		return volume ;
		
	}
	
	/** lit le nom et en ressort la marque*/
	public String getBrand(String name)
	{
		return "0" ;
	}
	
	// renvoie le codebarre de la boisson (troisi�me r�sultat)
	public long getCode(ResultSet results)
	{
		ResultSet m_results = results ;
		m_results.next();
		m_results.next();
		
		long code = m_results.next().getLiteral("codebarre").getLong();
		return code ;
	}
	
	// renvoie le degr� d'alcool de la boisson (quatri�me r�sultat)
	public int getDegree(ResultSet results)
	{
		ResultSet m_results = results ;
		m_results.next();
		m_results.next();
		m_results.next();
		
		int degree = m_results.next().getLiteral("degree").getInt();
		return degree ;
	}
	
	public boolean ajouterBoisson(ResultSet results)
	{
		String r_name = getName(results);
		String type = getTypeAlcohol(r_name);
		String brand = getBrand(r_name);
		int volume = getVolume(r_name);
		
		long code = getCode(results);
		int degree = getDegree(results);
				
		return bdd.ajouterBoisson(code, type, brand, volume, degree) ;
	}
}