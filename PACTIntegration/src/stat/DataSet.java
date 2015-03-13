package stat;


import java.util.ArrayList ;
import java.util.Date;
import java.lang.Math ;


public class DataSet extends ArrayList<Double> implements DataSetInterface
{
	private static final long serialVersionUID = 1L ;
	
	public DataSet() 
	{
		super() ;
	}
		
	/**************************************************************************************/
	// methodes de manipulation de vecteurs (produit scalaire, multiplication par un scalaire,
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
		
	/**
	 * @param liste_date ************************************************************************************/
	// methode de construction de vecteurs particuliers ones, stairs, salle, barman, commandes ...
	
	public static DataSet dateToDataSet(ArrayList<Date> liste_date)
	{
		int length = liste_date.size() ; 
		DataSet dataset = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			double converted_data = (double) liste_date.get(i).getTime();
			dataset.add(converted_data) ;
		}
		
		return dataset ;
		
	}
	
	public static DataSet volumeToDataSet(ArrayList<Integer> liste_volume)
	{
		int length = liste_volume.size() ;
		DataSet dataset = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			double converted_data = (double) liste_volume.get(i) ;
			dataset.add(converted_data) ;
		}
		
		return dataset ;
	}
	
	public static DataSet ones(int length)
	{
		DataSet constant = new DataSet() ;
		
		for(int i = 0 ; i < length ; i++)
		{
			constant.add(1.0) ;
		}
		
		return constant ;
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
	
	public static DataSet salle() throws TestException
	{
		BarModele barModele = new BarModele() ;
		
		// Etude sur un creneau de 10h (entre 19h00 et 5h00), soit 600 minutes. (cf def. Modele)
		int t = barModele.getTime() ;

		// Capacite du bar a accueillir ses clients. (en theorie un entier, en pratique un reel)
		double capacite = barModele.getCapaciteSalle() ;

		// On discretise l'ensemble.
		DataSet l_IN = new DataSet() ;

		for( int k = 0 ; k < t ; k++)
		{
			l_IN.add(barModele.lambdaIN((double)k));
		}
		        
		DataSet l_OUT = new DataSet() ;

		for( int k = 0 ; k < t ; k++)
		{
		   	l_OUT.add(barModele.lambdaOUT((double)k));
		}

		/*
		 * Cr�ation du vecteur X(t) qui contiendra les nombres de personnes dans le
		 * bar aux instants k = 0, 1, ... , (t-1) .
		 */
		DataSet X = new DataSet() ;
		X.add(0.0) ;
		
		/*
	     * Le nombre X[k] de personnes dans le bar � un instant k vaut ce qu'il y avait
		 * au temps (k-1) plus ce qui rentre ( poissrnd(l_IN(k-1)) ) ce qui sort (
		 * poissrnd(l_OUT(k-1)) ). L'utilisation du max est pour forcer la
		 * positivite du resultat sans passer par une valeur absolue qui inverserait
		 * le role de lambdaIN et lambdaOUT.
		 */
		    
		for(int k=1 ; k<t ; k++)
		{
		  	X.add(  Math.min(capacite, Math.max(0,X.get(k-1) + Alea.randomPoisson(l_IN.get(k-1)) - Alea.randomPoisson(l_OUT.get(k-1))))  ) ;
		}
		    
		return X ;
	}
	
	public static DataSet barman(DataSet X) throws TestException
	{
		// X : nombre de clients dans le bar.

		BarModele barModele = new BarModele() ;
		
		// Etude sur un cr�neau de 10h (entre 19h00 et 5h00), soit 600 minutes.
		int t = barModele.getTime() ;

		// Capacit� du bar � servir ses clients.
		int capacite = barModele.getCapaciteBarman() ;

		// On discr�tise. 
		DataSet l_Barman = new DataSet() ;

		for( int k = 0 ; k < t ; k++)
		{
			l_Barman.add(barModele.lambdaBarman((double)k));
		}

		/*
		 * Cr�ation du vecteur x(t) qui contiendra les nombres de commandes pass�es
		 * dans le bar aux instants k = 0, 1, ... , (t-1) .
		 */
				
		DataSet x = new DataSet() ;
		x.add(0.0) ;
				    
		/*
		 * Le nombre x[k] ne peut d�j� pas �tre plus grand que le nombre de
		 * personnes dans la salle un instant plus t�t : ce qui explique la pr�sence
		 * du min. Ensuite le nombre de commandes est proportionnel au nombre de
		 * personnes dans le bar un instant plus t�t.
		 */
		
		for(int k = 1 ; k<t ; k++)
		{
		   	x.add(  Math.min(Math.min(capacite, X.get(k-1)), Alea.randomPoisson(l_Barman.get(k-1)* X.get(k-1)))  );
		}     
				        
		return x ;
	}
	
	// evolution de la quantite totale d'alcool servie
	public static DataSet commandes(DataSet x)
	{
		int t = x.size() ;
		
		DataSet commandes = new DataSet() ;
		
		double coeff = 0.0 ;
		commandes.add(coeff) ;
		
		for (int k= 1 ; k<t ; k++ )
		{
			coeff = coeff + x.get(k) ; 
		    commandes.add(coeff) ; 
		}
		
		return commandes ;
		        
	}
	
	/**************************************************************************************/
	// methode moindrecarre
	
	public ArrayList<Double> moindrecarre(ArrayList<Date> liste_date) throws DimensionException
	{
		ArrayList<Double> Y = new ArrayList<Double>(2) ;
		
		int length = this.size() ;
				
		DataSet A = DataSet.dateToDataSet(liste_date) ; 
		
		if(length != A.size())
		{
			throw new DimensionException() ;
		}
		
		DataSet B = DataSet.ones(length) ;
		double nB = B.norm() ;
		
		/*
		 * L'id�e est de trouver des coefficients a et b pour minimiser Sum(|X(t)-(at + b)|^2)
		 * Cela revient donc a trouver la projection orthogonale de [X(0) X(1) ...
		 * X(t-1)] sur le plan engendre par A et B (cf. ci-dessus)
		 *
		 * Un petit coup de Gram-Schmidt :
		 */
		
		DataSet B1 = B.smultiply(1/nB) ;
				
		double d = A.dot(B1) ;
		DataSet A0 = A.minus(B1.smultiply(d)) ;
		double nA0 = A0.norm() ;
		DataSet A1 = A0.smultiply(1/nA0) ;
		
		// (A1,B1) est une base orthonormee du plan engendre par (A,B). 
		
		double a1 = this.dot(A1) ;
		double b1 = this.dot(B1) ;
		
		/*
		 * Y= [a1 b1] est exprime dans la base (A1,B1) i.e Y = a1.A1 + b1.B1
		 * Ne reste plus qu'a retourner les coefficients recherches :
		 */
		
		Y.add(a1/nA0) ;
		Y.add(b1/nB - d*a1/(nB*nA0)) ;
		
		return Y ;
	}

}
