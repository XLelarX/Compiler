package exception;

/**
 * Исключение, которыми перебрасываются в приложении
 */
public class CompilerException extends Exception
{
	public CompilerException()
	{
		super();
	}

	public CompilerException(String message)
	{
		super(message);
	}
}
