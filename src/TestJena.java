import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;


  
public class TestJena {  
  
    public static void main(String args[]) throws IOException {  
  
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  
  
        URL url = new URL("http://fr.openfoodfacts.org");       
  
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();  
        InputStream in = url.openStream();  
        ontModel.read(in, "");  
        String queryString = "select distinct ?p where {"+
"graph <http://fr.openfoodfacts.org> {"+
"?s ?p ?o"+
"}}"
;  
  
        Query query = QueryFactory.create(queryString);  
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);  
        ResultSet results = qe.execSelect();  
          
        while (results.hasNext()) {  
            QuerySolution qs = results.next();  
            System.out.println(qs.get("p"));  
        }  
  
        // ResultSetFormatter.out(System.out, results, query);  
        qe.close();  
    }  
  
}  