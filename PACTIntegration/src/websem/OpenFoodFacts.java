package websem;


import java.util.List;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import bdd.BDDInterface;


public class OpenFoodFacts implements OFFInterface
{   
	public static int Volume0;
	private BDDInterface bdd ;
	private final static HttpAuthenticator authenticator = new SimpleAuthenticator("off", "off".toCharArray());	
	private final
	static String ENDPOINT = "http://opendata1.opendata.u-psud.fr:8890/sparql-auth/" ;
	
	//	static String ENDPOINT = "http://virtuoso02.calcul.u-psud.fr/sparql-auth/" ;
	

	
	public OpenFoodFacts(BDDInterface bdd) 
	{
		this.bdd = bdd ;
	}
	
	public List<QuerySolution> getResults(long codebarre) throws BarCodeException  
	{
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
		+ 			"OPTIONAL{?s food:alcoholPer100g ?degree}."
		+ 		"}"
		+ 	"}" ;

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

	// renvoie le nom de la boisson
	public String getName(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		
		String name = qs.getLiteral("name").getString();
		return name ;
	}
	
	// renvoie (si possible) le volume de la bouteille 
	public int getVolume(String name) //throws VolumeException
	{
		System.out.println(name);
		/*// on v�rifie la pr�sence d'un volume par la pr�sence de " cl " (volumes donn�s en centilitres)
		if(!name.contains(" cl "))
		{
			throw new VolumeException() ;
		}
		
		// l'endroit o� se trouve " cl " nous donne la fin de la s�quence de chiffres correspondants au volume.
		int i_fin = name.indexOf(" cl ")  ;
		int k = i_fin  ;
		
		// on descend jusqu'au prochain espace
		char chartest ;
		do
		{
			k-- ;
			chartest = name.charAt(k) ;
		}
		while( chartest != ' ' ) ;
		int i_debut = k+1 ;
		// Rq : i_debut = k+1 pour ne pas r�cup�rer l'espace � gauche
		
		// on a l'indice de d�but, l'indice de fin : on r�cup�re la portion de string correspondante
		String svolume = name.subSequence(i_debut, i_fin).toString() ;
		
		// on retourne cette portion convertie en int		
		return Integer.parseInt(svolume) ;*/
		
		String[] minus=name.split("[0123456789]+[ ]*(cl|cL|L|litre|Litre|litres|Litres)");
		for(int i=0; i<minus.length;i++)
		{
			name=name.replaceAll(minus[i], "");
		}
		name=name.replaceAll("(cl|cL|L|litre|Litre|litres|Litres)", "");
		
		name=name.replaceAll(" ","");
		if(!name.matches("[0123456789]+"))
		{
//			try {
//				throw new VolumeException();
//			} catch (VolumeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return -1;
//			}
		Volume0=0;
		return 0;	
		}
		return Integer.parseInt(name);
			
	}
	
	// renvoie le codebarre de la boisson
	public long getCode(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		
		long codebarre = qs.getLiteral("codebarre").getLong();
		return codebarre ;
	}
	
	// renvoie le degr� d'alcool de la boisson
	public float getDegree(List<QuerySolution> solutionlist)
	{
		QuerySolution qs = solutionlist.get(0) ;
		if(!qs.contains("degree")) return 0;
		
		float degree = qs.getLiteral("degree").getFloat();
		return degree ;
	}
	
	// ajoute les donn�es r�colt�es � la base de donn�es
	public boolean ajouterBoisson(long codebarre)
	{   try
	   {
		List<QuerySolution> solutionlist=this.getResults(codebarre);
		long code = this.getCode(solutionlist);
		String nom = this.getName(solutionlist) ;
		int volume = this.getVolume(nom) ;
		float degree = this.getDegree(solutionlist);
		return bdd.ajouterBoisson(code, nom, "in the name",volume, degree) ;
	   }
	  catch(BarCodeException e)
	  {
		  e.printStackTrace();
		  return false;
	  }
	}
}