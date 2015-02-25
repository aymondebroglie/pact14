import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


  
public class TestJena {  
  

		  
	    public static void main(String args[]) throws IOException  {  
	  
//	        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  
	//  
//	        URL url = new URL("http://fr.openfoodfacts.org");       
	//  
//	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();  
//	        InputStream in = url.openStream();  
//	        ontModel.read(in, "");  
//	        String queryString = "select distinct ?p where {"+
	//"graph <http://fr.openfoodfacts.org> {"+
	//"?s ?p ?o"+
	//"}}"
	//;  
	//  
//	        Query query = QueryFactory.create(queryString);  
//	        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);  
//	        ResultSet results = qe.execSelect();  
//	          
//	        while (results.hasNext()) {  
//	            QuerySolution qs = results.next();  
//	            System.out.println(qs.get("p"));  
//	        }  
	//  
//	        // ResultSetFormatter.out(System.out, results, query);  
//	        qe.close();  
//	    } 
	//    
	    	
	    	
	    	// Open the bloggers RDF graph from the filesystem
	    	InputStream in = new FileInputStream(new File("fr.openfoodfacts.org.products.rdf"));

	    	// Create an empty in-memory model and populate it from the graph
	    	Model model = ModelFactory.createMemModelMaker().createDefaultModel();
	    	model.read(in,null); // null base URI, since model URIs are absolute
	    	in.close();
	    	
	//FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
	//Model model = FileManager.get().loadModel("fr.openfoodfacts.org.products.rdf");
	    	// Create a new query
	    	String queryString = 
	    			"select distinct ?s ?name ?codebarre ?degree where "
	    					+ 	"{"
//	    					+		"graph <http://fr.openfoodfacts.org> "
//	    					+ 		"{"
	    					+ 			"?s <http://data.lirmm.fr/ontologies/food#name> ?name."
	    					+ 			"?s <http://data.lirmm.fr/ontologies/food#code> \"3147690061007\"."
	    					+ 			"?s <http://data.lirmm.fr/ontologies/food#code> ?codebarre."
	    					+ 			"?s <http://data.lirmm.fr/ontologies/food#alcoholPer100g> ?degree."
//	    					+ 		"}"
	    					+ 	"} " ;


	    	Query query = QueryFactory.create(queryString);

	    	// Execute the query and obtain results
	    	QueryExecution qe = QueryExecutionFactory.create(query, model);
	    	ResultSet results = qe.execSelect();

	    	// Output query results	
	    	ResultSetFormatter.out(System.out, results, query);

	    	// Important - free up resources used running the query
	    	qe.close();

}
}