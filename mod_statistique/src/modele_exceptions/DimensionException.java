package modele_exceptions;

public class DimensionException  extends Exception
{
	private static final long serialVersionUID = 1L;

	public DimensionException()
	{
		super("ERROR : you are trying to manipulate DataSets with different dimensions");
	}
}