package stat;

import java.util.ArrayList;
import java.util.Date;



public interface DataSetInterface
{
	/**************************************************************************************/
	// methodes de manipulation de vecteurs (produit scalaire, multiplication par un scalaire,
	//addition de deux vecteurs, etc...)
	
	// multiplication par un scalaire alpha : A.smultiply(alpha) <=> alpha*A
	public DataSet smultiply(double alpha) ;
	
	// soustraction : A.minus(B) <=> A-B
	public DataSet minus(DataSet otherdataset) throws DimensionException ;
	
	// produit scalaire : A.dot(B) <=> B.dot(A) <=> (A|B)
	public double dot(DataSet otherdataset) throws DimensionException ;
	
	// norme : A.norm() <=> ||A||
	public double norm() throws DimensionException ;
	
	/**************************************************************************************/
	// methode moindrecarre
	
	public ArrayList<Double> moindrecarre(ArrayList<Date> liste_date) throws DimensionException ;
	
	
	
	
		
	
	
}
