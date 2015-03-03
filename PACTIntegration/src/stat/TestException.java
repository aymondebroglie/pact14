package stat;

public class TestException extends Exception
{
	private static final long serialVersionUID = 1L;

	public TestException(String test)
	{
		super("ERROR : test in method "+ test +" failed because of too much occurences");
	}
}
