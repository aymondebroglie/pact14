package main;

import java.util.ArrayList;

import modele.DataSet;
import modele_exceptions.DimensionException;

public class MainTest 
{

	public static void main(String[] args) throws DimensionException 
	{
		DataSet A = DataSet.stairs(4);
		DataSet B = DataSet.ones(6);
		

		
		
		ArrayList<Double> Y = A.moindrecarre();
		System.out.println(Y);
		
		Y = B.moindrecarre();
		System.out.println(Y) ;
		
		int t = 2 ;
		
		double t1 = 1.5 ;
		
		double m = Math.min(t ,t1) ;
		
		System.out.println(m);
	}

}
