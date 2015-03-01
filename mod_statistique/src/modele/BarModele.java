package modele;

import java.lang.Math ;

public class BarModele 
{
	private static final double e = Math.exp(1) ;
	
	// temps d'échantillonage
	private int t ;
	
	// paramètres loi Poisson
	private double in ;
	private double out ;
    private double barman ;
    
    // capacités du bar
    private int capaciteSalle ;
    private int capaciteBarman ;
        
    public BarModele()
    {
    	// temps d'échantillonage
    	this.t = 600 ;
    	
    	// paramètres loi Poisson
    	this.in = 1.5 ;
    	this.out = 1.5 ;
    	this.barman = 0.5 ;
        
        // capacités du bar
    	this.capaciteSalle = 100 ;
    	this.capaciteBarman = 50 ;
    }
    
    	
	public int getTime()
	{
		return this.t ;
	}
	
	public int getCapaciteSalle()
	{
		return this.capaciteSalle ;
	}
	
	public int getCapaciteBarman()
	{
		return this.capaciteBarman ;
	}
	
	public double lambdaIN(double x)
	{	    	    
	    double t1 = (3./10.)*(double)t ;
	    double t2 = (5./10.)*(double)t ;
	    double t3 = (9.5/10.)*(double)t ;

	    
	    double y = 0 ;

	    if( (x >= 0) && (x < t1) )
	    {
	    	y = in*e*Math.exp( -1/( 1 - Math.pow((x/t1)-1 ,4) ) ) ;
	    }

	    if( (x >= t1) && (x < t2) )
	    {
	    	y = in ;
	    }
	    
	    if( (t2<= x) && (x <t3) )
	    {
	    	y = in*e*Math.exp( -1/( 1-Math.pow((x-t2)/(t3-t2), 4 ))) ;
	    }  
	    
	    return y ;	    
	}
	
	public double lambdaOUT(double x)
	{ 	    
	    double t1 = (0.5/10.)*(double)t ;
	    double t2 = (4./10.)*(double)t ;
	    double t3 = (9./10.)*(double)t ;
	    double t4 = (11./10.)*(double)t ;

	    double y = 0 ;

	    if( (x >= t1) && (x < t2))
	    {
	        y = out*e*Math.exp( -1/( 1-Math.pow(((x-t1)/(t2-t1))-1,2)) ) ;
	    }

	    if( (t2<= x) && (x <t) )
	    {
	    	y = out*e*Math.exp( -1/( 1-Math.pow(((x-t2)/(t4-t2)) ,4)) ) ;
	    } 
	    
	    if( (x >= t3) && (x < t) )
	    {
	    	y = y + Math.pow(10,-2)*Math.exp( (1-2*((x-t3)/(t-t3)))/(((x-t3)/(t-t3))*(((x-t3)/(t-t3))-1)) ) ;
	    }
	        
	    // les videurs sont trop costauds : lambdaOUT diverge en t, on protège le système avec un saturateur.
	    return Math.min(y,10) ; 
	}
	
	public double lambdaBarman(double x)
	{   
	    double t1 = (4./10.)*(double)t ;
	    double t2 = (8./10.)*(double)t ;
	    double t3 = (9.5/10.)*(double)t ;
	   
	    double y = 0 ;

	    if( (x >= 0) && (x < t1) )
	    {
	    	y = barman*e*Math.exp( -1/( 1-Math.pow((x/t1)-1 ,4)) ) ;
	    }

	    if( (x >= t1) && (x < t2) )
	    {
	    	y = barman ;
	    }
	    
	    if( (t2<= x) && (x <t3) )
	    {
	    	y = barman*e*Math.exp( -1/( 1-Math.pow(((x-t2)/(t3-t2)) ,4)) ) ;
	    }
	        
	    return y ;
	}		
}
