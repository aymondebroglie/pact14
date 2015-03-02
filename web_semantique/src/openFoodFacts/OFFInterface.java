package openFoodFacts;

import java.util.List;

import openFoodFactsExceptions.BarCodeException;
import openFoodFactsExceptions.VolumeException;

import com.hp.hpl.jena.query.QuerySolution;

public interface OFFInterface 
{
	// permet de lancer une recherche pour un codebarre donné
	public List<QuerySolution> getResults(long codebarre) throws BarCodeException;
	
	// extraient respectivement nom, volume, codebarre et degré du résultat de la requête obtenu par la méthode précédente
	public String getName(List<QuerySolution> solutionlist) ;
	
	public int getVolume(String name) throws VolumeException ;
	
	public long getCode(List<QuerySolution> solutionlist) ;
	
	public double getDegree(List<QuerySolution> solutionlist) ;
	
	// stocke données extraites dans BDD
	public boolean ajouterBoisson(List<QuerySolution> solutionlist) throws VolumeException ;
	
	
	
}
