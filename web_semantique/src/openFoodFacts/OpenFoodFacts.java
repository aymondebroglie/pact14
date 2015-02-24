package openFoodFacts;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class OpenFoodFacts 
{
	public ResultSet getResults(long codebarre)
	{
		// Create an empty in-memory model and populate it from the graph
					Model model = ModelFactory.createDefaultModel();
					model.read("http://opendata1.opendata.u-psud.fr:8890/sparql/"); 
						
					// Create a new query
					String queryString = 
			"select distinct ?s ?name ?codebarre ?degree where "
		+ 	"{"
		+		"graph <http://fr.openfoodfacts.org> "
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
		ResultSet m_results =results ;
		m_results.next();
		
		String name = m_results.next().getLiteral("name").getString();
		return name ;
	}
	
	public String getTypeAlcohol(String name)
	{
		return "0" ;
	}
	
	public int getVolume(String name)
	{
		return 0 ;
	}
	
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
		
		long codebarre = m_results.next().getLiteral("codebarre").getLong();
		return codebarre ;
	}
	
	// renvoie le degr� d'alcool de la boisson (quatri�me r�sultat)
	public long getDegree(ResultSet results)
	{
		ResultSet m_results = results ;
		m_results.next();
		m_results.next();
		m_results.next();
		
		long degree = m_results.next().getLiteral("degree").getInt();
		return degree ;
	}
}