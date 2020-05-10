package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;
import logger.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Базовый класс Runner-ов
 */
public abstract class BaseCodeRunner
{
	final static String PATH = "/Users/Lelar/Desktop/JavaProjects/execution/";
	final static String EXECUTION_ERRORS = "Execution errors: ";
	final static String COMPILE_ERRORS = "Compile errors: ";
	final static String CREATE_ERRORS = "Create errors: ";
	final static long COMPILE_TIME = 2500;

	/**
	 * Компиляция и выполнение программы
	 *
	 * @param code    - Код программы
	 * @param vars    - Переменные, передаваемые в программу, во время ее выполнения
	 * @param request - Входящий запрос
	 * @return - Результат запуска программы
	 */
	abstract CompilerEntity run(String code, String vars, HttpServletRequest request)
			throws InterruptedException, IOException, CompilerException;

	/**
	 * Запуск корректного runner-a
	 *
	 * @param code    - Код программы
	 * @param vars    - Переменные, передаваемые в программу, во время ее выполнения
	 * @param request - Входящий запрос
	 * @return - Результат запуска программы
	 * @throws CompilerException
	 */
	public CompilerEntity start(String code, String vars, HttpServletRequest request)
			throws CompilerException
	{
		ExecutorService service = Executors.newSingleThreadExecutor();
		AtomicReference<CompilerEntity> atomicCompilerEntity = new AtomicReference<>();

		try
		{
			service.submit(() -> {
				try
				{
					atomicCompilerEntity.set(run(code, vars == null ? "" : vars, request));
				} catch (InterruptedException | IOException | CompilerException e)
				{
					Logger.fillLog(e);
					atomicCompilerEntity.set(
							new CompilerEntity(Collections.emptyList(), Collections.singletonList(e.getMessage()))
					);
				}
			}).get(1000, TimeUnit.SECONDS);// attempt the task for two minutes
		} catch (InterruptedException | TimeoutException | ExecutionException e)
		{
			throw new CompilerException(EXECUTION_ERRORS + '\n' + e);
		} finally
		{
			service.shutdown();
		}

		return atomicCompilerEntity.get();
	}

	/**
	 * Чтение данных из входного стрима
	 *
	 * @param inputStream - Входной стрим
	 * @return - Обрабатываемый в будущем код
	 */
	List<String> readFrom(InputStream inputStream)
	{
		String line;
		List<String> out = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		try
		{
			while ((line = reader.readLine()) != null)
				out.add(line);
			reader.close();
		} catch (IOException e)
		{
			Logger.fillLog(e);
		}

		return out;
	}

	/**
	 * Создание папки с исполняемыми файлами
	 *
	 * @param executionFileName - Имя основного исполняемого файла
	 * @return - Имя папки с исполняемыми файлами
	 * @throws IOException - Ошибка при чтении из стрима
	 */
	String createFolder(String executionFileName) throws IOException
	{
		Process process;
		List<String> mkdirErr;
		String folderName;

		do
		{
			folderName = executionFileName + Math.round(Math.random() * 100);
			process = new ProcessBuilder("mkdir", PATH + folderName).start();
			mkdirErr = readFrom(process.getErrorStream());
		}
		while (!mkdirErr.isEmpty() && mkdirErr.get(0).equals("mkdir: " + PATH + folderName + ": File exists"));

		return folderName;
	}
}
