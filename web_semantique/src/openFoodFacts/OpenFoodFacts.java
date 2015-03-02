package openFoodFacts;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.PreemptiveBasicAuthenticator;
import org.apache.jena.atlas.web.auth.ScopedAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import BDD.BDDInterface;


public class OpenFoodFacts
{
	private BDDInterface bdd ;
	private final static HttpAuthenticator authenticator = new SimpleAuthenticator("off", "off".toCharArray());	
	private final static String ENDPOINT = "http://opendata1.opendata.u-psud.fr:8890/sparql-auth/" ;

	
	public OpenFoodFacts(BDDInterface bdd) 
	{
		this.bdd = bdd ;
	}
	
	public List<QuerySolution> getResults(long codebarre) throws BarCodeException  
	{
		
		// Create an empty in-memory model and populate it from the graph
		//Model model = ModelFactory.createDefaultModel();
		//model.read(ENDPOINT); 
						
		// Create a new query
		String queryString =
				
		"prefix food: <http://data.lirmm.fr/ontologies/food#>\n " +	
			"select distinct ?s ?name ?codebarre ?degree where "
		+ 	"{"
		+		"graph <http://fr.openfoodfacts.org> "
		+ 		"{"
		+ 			"?s food:name ?name."
		+ 			"?s food:code '" + codebarre + "'."
		+ 			"?s food:code ?codebarre."
		+ 			"?s food:alcoholPer100g ?degree."
		+ 		"}"
		+ 	"}" ;

		
		/** System.out.println(queryString) ; */

		// Execute and authenticate the query and obtain results 
		QueryExecution query_execution = QueryExecutionFactory.sparqlService(ENDPOINT, queryString, authenticator);
		ResultSet results = query_execution.execSelect();
				        
		// Output query results
		List<QuerySolution> solutionlist = ResultSetFormatter.toList(results);		
		
		// Important - free up resources used running the query					
		query_execution.close();
		
		int length = solutionlist.size() ;
		
		if( length != 1)
		{
			throw new BarCodeException(codebarre) ;
		}
							
		return solutionlist ;		
	}
	
	// renvoie le nom de la boisson (second r�sultat)
	public String getName(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		
		String name = qs.getLiteral("name").getString();
		return name ;
	}
		
	// renvoie le codebarre de la boisson (troisi�me r�sultat)
	public long getCode(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		
		long codebarre = qs.getLiteral("codebarre").getLong();
		return codebarre ;
	}
	
	// renvoie le degr� d'alcool de la boisson (quatri�me r�sultat)
	public double getDegree(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		
		double degree = qs.getLiteral("degree").getDouble();
		return degree ;
	}
	
	public boolean ajouterBoisson(List<QuerySolution> solutionlist)
	{
		String nom = getName(solutionlist) ;
		long code = getCode(solutionlist);
		double degree = getDegree(solutionlist);
				
		return bdd.ajouterBoisson( code, nom, degree) ;
	}
}