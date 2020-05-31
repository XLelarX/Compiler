package logger;

import util.Constants;

import java.io.*;
import java.util.Objects;

/**
 * Класс, отвечающий за логирование ошибок
 */
public class Logger
{
	private static final File LOG_FILE = new File(Constants.LocalRun.LOG_FILE_PATH);
	private static PrintStream log;

	public Logger()
	{
		throw new IllegalStateException("Don't create instance of this class");
	}

	/**
	 * Заполнить log-файл ошибками из исключения
	 *
	 * @param exception - Исключение с ошибками
	 */
	public static void fillLog(Exception exception)
	{
		Objects.requireNonNull(exception, "Exception can't be null!!!");

		if (LOG_FILE.exists() && log != null)
		{
			exception.printStackTrace(log);
			log.flush();
		} else
		{
			createLogFile();
			fillLog(exception);
		}
	}

	/**
	 * Инициализация log-файла
	 */
	private static void createLogFile()
	{
		try
		{
			log = new PrintStream(LOG_FILE);
		} catch (FileNotFoundException e)
		{
			try
			{
				if (LOG_FILE.createNewFile())
				{
					log = new PrintStream(LOG_FILE);
				}
			} catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
}
