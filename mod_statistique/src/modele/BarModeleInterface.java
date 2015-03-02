package modele;

public interface BarModeleInterface 
{
	/**************************************************************************************/
	// getters
	public int getTime() ;
	public int getCapaciteSalle() ;
	public int getCapaciteBarman() ;
	
	/**************************************************************************************/
	// paramètres de lois de Poisson variables
	public double lambdaIN(double x) ;
	public double lambdaOUT(double x) ;
	public double lambdaBarman(double x) ;
	
}
