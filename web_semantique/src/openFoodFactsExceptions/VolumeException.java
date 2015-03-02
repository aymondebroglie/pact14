package openFoodFactsExceptions;

public class VolumeException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public VolumeException()
	{
		super("WARNING : no informations about contenant's volume detected") ;
	}
}
