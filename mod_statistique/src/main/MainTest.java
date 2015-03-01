package main;

import modele.Alea;
import modele.DataSet;
import modele_exceptions.DimensionException;
import modele_exceptions.TestException;

public class MainTest 
{

	public static void main(String[] args) throws DimensionException, TestException 
	{
		
		System.out.println("***************************************") ;		
		 
		for(int i = 0 ; i < 5 ; i++)
		{
			int a = Alea.randomPoisson(10) ;
			System.out.println(a);
		}
		
		System.out.println("***************************************") ;
		
		 DataSet X = DataSet.salle() ;
		System.out.println("X = " + X ) ; 
		DataSet x = DataSet.barman(X) ;
		System.out.println("x = " + x ) ;
		
		
		
	}

}
