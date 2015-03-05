package stat;

import java.util.ArrayList;
import java.util.Random;



public class Alea 
{
	private static Random alea = new Random() ;
	
	public static double uniforme()
	{
		return alea.nextDouble();
	}
	
	public static int factorielle(int k)
	{
		int result = 1 ;
		
		for(int i = 0 ; i<k ; i++)
		{
			result = result*(i+1) ;
		}
		
		return result ;
	}
	
	public static double loiPoisson(double lambda , int k)
	{
		return (  Math.pow(lambda, k)/(Alea.factorielle(k))*Math.exp(-lambda)  ) ;
	}
	
	public static ArrayList<Double> distributionPoisson(double lambda)
	{
		ArrayList<Double> distribution = new ArrayList<Double>() ;
		
		double poids = 0 ;
		int i = 0 ;
		
		while(poids < 0.95)
		{
			double p_i = Alea.loiPoisson(lambda, i) ;
			distribution.add( p_i ) ;
			poids = poids + p_i ;
			i++ ;
		}
	
		distribution.add(0, distribution.get(0) + (1 - poids) ) ;

		
		return distribution ;
	}
	
	public static int randomPoisson(double lambda) throws TestException
	{
		double u_n = Alea.uniforme() ;
		int i = 0 ;
		
		ArrayList<Double> distribution = Alea.distributionPoisson(lambda) ;
		int length = distribution.size() ;
		
		double r =  0 ;
		double s = r + distribution.get(i) ;
		

				while(  !(r<u_n) || !(s>=u_n)  )
			{
				// apr�s length-1, on est s�r d'�tre all� trop loin : 
				if(i < length-1)
				{
					r = s ;
					i++ ;
					s = r + distribution.get(i) ;
				}
				else
				{
					i = -1 ;
					throw new TestException("randomPoisson") ;
				}
				
			}
				
		return i ;
	}	
}