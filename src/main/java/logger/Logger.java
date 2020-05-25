package logger;

import java.io.*;
import java.util.Objects;

/**
 * Класс, отвечающий за логирование ошибок
 */
public class Logger
{
	private final static String LOG_FILE_PATH = "/log/log";
	//private final static String LOG_FILE_PATH = "/Users/Lelar/Desktop/JavaProjects/log/log";
	private static PrintStream log;

//	static
//	{
//		initLog();
//	}

	public Logger()
	{
		throw new IllegalStateException("don't create instance of this class");
	}

	/**
	 * Заполнить log-файл ошибками из исключения
	 *
	 * @param exception - Исключение с ошибками
	 */
	public static void fillLog(Exception exception)
	{
		Objects.requireNonNull(exception, "exception can't be null!!!");

		if (new File(LOG_FILE_PATH).exists())
		{
			exception.printStackTrace(log);
			log.flush();
		} else
		{
			initLog();
			fillLog(exception);
			//exception.printStackTrace();
		}
	}

	/**
	 * Инициализация log-файла
	 */
	static void initLog()
	{
		if (log != null)
		{
			log.close();
		}

		try
		{
			log = new PrintStream(LOG_FILE_PATH);
		} catch (FileNotFoundException e)
		{
			try
			{
				if (new File(LOG_FILE_PATH).createNewFile())
				{
					log = new PrintStream(LOG_FILE_PATH);
				}
			} catch (IOException exception)
			{
				exception.printStackTrace();
				//logFileExists = false;
			}
		}
	}

	static String getLogFilePath()
	{
		return LOG_FILE_PATH;
	}
}
