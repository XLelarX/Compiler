package logger;

import exception.CompilerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static logger.Logger.*;

class LoggerTest
{
	private static final String FIRST_EXCEPTION_MESSAGE = "first test exception";
	private static final String SECOND_EXCEPTION_MESSAGE = "second test exception";

	@BeforeEach
	void beforeTest()
	{
		initLog();
	}

	@Test
	void testFillLogWithNullException()
	{
		Assertions.assertThrows(NullPointerException.class, () -> Logger.fillLog(null));
	}

	@Test
	void testFillLogWithSeveralExceptions() throws IOException
	{
		try
		{
			throw new CompilerException(FIRST_EXCEPTION_MESSAGE);
		} catch (CompilerException e)
		{
			fillLog(e);
		}

		try
		{
			throw new CompilerException(SECOND_EXCEPTION_MESSAGE);
		} catch (CompilerException e)
		{
			fillLog(e);
		}


		BufferedReader fileReader = new BufferedReader(new FileReader(getLogFilePath()));
		String line;
		boolean firstExists = false;
		boolean secondExists = false;

		while ((!firstExists || !secondExists) && ((line = fileReader.readLine()) != null))
		{
			if (line.contains(FIRST_EXCEPTION_MESSAGE))
			{
				firstExists = true;
			}
			if (line.contains(SECOND_EXCEPTION_MESSAGE))
			{
				secondExists = true;
			}
		}

		Assertions.assertTrue(firstExists);
		Assertions.assertTrue(secondExists);
	}

	@Test
	void testFillLogWithOneException()
	{
		try
		{
			throw new CompilerException("test exception");
		} catch (CompilerException e)
		{
			fillLog(e);
		}

		Assertions.assertTrue(new File(getLogFilePath()).length() != 0);
	}
}
