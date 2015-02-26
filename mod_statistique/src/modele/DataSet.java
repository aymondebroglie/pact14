package modele;


import java.util.ArrayList ;
import java.lang.Math ;
import modele_exceptions.DimensionException ;

public class DataSet extends ArrayList<Double>
{
	private static final long serialVersionUID = 1L ;
		
	public DataSet()
	{
		super() ;
	} 
	
	/**************************************************************************************/
	// methode de manipulation de vecteurs (produit scalaire, multiplication par un scalaire,
	//addition de deux vecteurs, etc...)
	
	// multiplication par un scalaire alpha : A.smultiply(alpha) <=> alpha*A
	public DataSet smultiply(double alpha)
	{
		int length = this.size() ;
		DataSet result = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			double coeff = this.get(i) ;
			coeff = coeff*alpha ;
			
			result.add(coeff) ;
		}
		
		return result ;
	}
	
	// soustraction : A.minus(B) <=> A-B
	public DataSet minus(DataSet otherdataset) throws DimensionException
	{
		int length = this.size() ;
		int length_otherdataset = otherdataset.size() ;
		DataSet result = new DataSet() ;
		
		if(length == length_otherdataset)
		{
			for(int i = 0 ; i < length ; i++)
			{
				double a = this.get(i) ;
				double b = otherdataset.get(i) ;
				double coeff = a - b ;
				
				result.add(coeff) ;
			}
		}
		else
		{
			throw new DimensionException() ;
		}
		
		return result ;
	}
	
	// produit scalaire : A.dot(B) <=> B.dot(A) <=> (A|B)
	public double dot(DataSet otherdataset) throws DimensionException
	{
		int length = this.size() ;
		int length_otherdataset = otherdataset.size() ;
		double dotproduct = 0.0 ;
		
		if(length == length_otherdataset)
		{
			for(int i = 0 ; i < length ; i++ )
			{
				double a = this.get(i) ;
				double b = otherdataset.get(i)  ;
				
				dotproduct = dotproduct + a*b ;
			}
		}
		else
		{
			throw new DimensionException();
		}
		
		return dotproduct ;
	}
	
	// norme : A.norm() <=> ||A||
	public double norm() throws DimensionException
	{
		double normcarre = this.dot(this) ;
		return(Math.sqrt(normcarre)) ;
	}
	
	/**************************************************************************************/
	// methode de construction de vecteurs particuliers ones et stairs
	
	public static DataSet ones(int length)
	{
		DataSet ones = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			ones.add(1.0) ;
		}
		
		return ones ;
	}
	
	public static DataSet stairs(int length)
	{
		DataSet stairs = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			stairs.add((double) i) ;
		}
		
		return stairs ;
	}
	
	/**************************************************************************************/
	// methode moindrecarre
	
	public ArrayList<Double> moindrecarre() throws DimensionException
	{
		ArrayList<Double> Y = new ArrayList<Double>(2) ;
		
		int length = this.size() ;
				
		DataSet A = DataSet.stairs(length) ; 
		DataSet B = DataSet.ones(length) ;
		double nB = B.norm() ;
		
		/*
		 * L'id�e est de trouver des coefficients a et b pour minimiser Sum(|X(t)-(at + b)|^2)
		 * Cela revient donc � trouver la projection orthogonale de [X(0) X(1) ...
		 * X(t-1)] sur le plan engendr� par A et B (cf. ci-dessus)
		 *
		 * Un petit coup de Gram-Schmidt :
		 */
		
		DataSet B1 = B.smultiply(1/nB) ;
				
		double d = A.dot(B1) ;
		DataSet A0 = A.minus(B1.smultiply(d)) ;
		double nA0 = A0.norm() ;
		DataSet A1 = A0.smultiply(1/nA0) ;
		
		// (A1,B1) est une base orthonorm�e du plan engendr� par (A,B). 
		
		double a1 = this.dot(A1) ;
		double b1 = this.dot(B1) ;
		
		/*
		 * Y= [a1 b1] est exprim� dans la base (A1,B1) i.e Y = a1.A1 + b1.B1
		 * Ne reste plus qu'� retourner les coefficients recherch�s :
		 */
		
		Y.add(a1/nA0) ;
		Y.add(b1/nB - d*a1/(nB*nA0)) ;
		
		return Y ;
	}
	
	
	
	
}
