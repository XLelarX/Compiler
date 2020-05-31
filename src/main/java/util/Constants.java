package util;

/**
 * Константы приложения
 */
public interface Constants
{
	String CHAR_ENCODING = "UTF-8";

	/**
	 * Константы для локального запуска
	 */
	interface LocalRun
	{
		String PATH = "/Users/Lelar/Desktop/JavaProjects/execution/";
		String VOC_PATH = "/Users/Lelar/Desktop/Oberon/voc/install/bin/voc";
		String LOG_FILE_PATH = "/Users/Lelar/Desktop/JavaProjects/log/log";
	}

	/**
	 * Константы для удаленного запуска
	 */
	interface RemoteRun
	{
		String PATH = "/classes/";
		String VOC_PATH = "/voc/voc";
		String LOG_FILE_PATH = "/log/log";
	}

	/**
	 * Форматы файлов
	 */
	interface FileFormat
	{
		String JAVA_FILE_FORMAT = ".java";
		String C_FILE_FORMAT = ".c";
		String OBERON_FILE_FORMAT = ".mod";
	}
}
