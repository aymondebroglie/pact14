package websem;

public interface OFFInterface 
{
	// permet de lancer une recherche pour un codebarre donn�
//	public List<QuerySolution> getResults(long codebarre) throws BarCodeException;
//	
//	// extraient respectivement nom, volume, codebarre et degr� du r�sultat de la requ�te obtenu par la m�thode pr�c�dente
//	public String getName(List<QuerySolution> solutionlist) ;
//	
//	public int getVolume(String name) throws VolumeException ;
//	
//	public long getCode(List<QuerySolution> solutionlist) ;
//	
//	public double getDegree(List<QuerySolution> solutionlist) ;
	
	// stocke donn�es extraites dans BDD
	public boolean ajouterBoisson(long codebarre);
	
	
	
}
