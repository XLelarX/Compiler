package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;
import utils.ConsoleHelper;
import logger.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Базовый класс Runner-ов
 */
public abstract class BaseCodeRunner
{
	final static String EXECUTION_ERRORS = "Execution errors: ";
	final static String COMPILE_ERRORS = "Compile errors: ";
	final static String CREATE_ERRORS = "Create errors: ";
	final static long EXECUTION_TIME = 2000;

	static Map<String, Process> processes = new HashMap<>();
	volatile static Map<String, List<String>> out = new HashMap<>();
	volatile static Map<String, String> folderNames = new HashMap<>();

	/**
	 * Имя папки с исполняемыми файлами
	 */
	String folderName;

	/**
	 * Имя исполняемого файла
	 */
	String executionFileName;

	/**
	 * Компиляция и выполнение программы
	 *
	 * @param code      - Код программы
	 * @param vars      - Переменные, передаваемые в программу, во время ее выполнения
	 * @param sessionId - id пользователя
	 * @return - Результат запуска программы
	 */
	abstract CompilerEntity run(String code, String vars, String sessionId)
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
					atomicCompilerEntity.set(run(code, vars == null ? "" : vars, request.getSession().getId()));
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
	 * @return - Выходные данные из потока
	 */
	public static List<String> readFrom(String sessionId)
	{
		Process atomicProcess = processes.get(sessionId);

		if (atomicProcess != null)
		{
			List<String> output = new ArrayList<>();
			out.put(sessionId, output);
			BufferedReader reader = new BufferedReader(new InputStreamReader(atomicProcess.getInputStream()));//process.getInputStream()));
			Thread thread =
					new Thread(() -> {
						try
						{
							String line;
							while ((line = reader.readLine()) != null)
								output.add(line);
							reader.close();
						} catch (IOException e)
						{
							Logger.fillLog(e);
						}
					});
			thread.setPriority(6);
			thread.start();

			long time = System.currentTimeMillis();
			while (thread.isAlive() && System.currentTimeMillis() - time < EXECUTION_TIME)
			{
			}
			return output;
		}

		return null;
	}

	public static List<String> readFrom(Process process) throws IOException
	{
		List<String> output = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		while ((line = reader.readLine()) != null)
			output.add(line);
		reader.close();

		return output;
	}

	public static void writeInProcess(String sessionId, List<String> input)
			throws IOException
	{
		Process atomicProcess = processes.get(sessionId);

		if (atomicProcess != null)
		{

			BufferedWriter writer =
					new BufferedWriter(new OutputStreamWriter(atomicProcess.getOutputStream()));
			for (String s : input)
			{
				writer.write(s);
				writer.newLine();
				writer.flush();
			}
		}
	}

	public static void writeInProcess(Process process, List<String> input)
			throws IOException
	{
		BufferedWriter writer =
				new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		for (String s : input)
		{
			writer.write(s);
			writer.newLine();
			writer.flush();
		}
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
			process = ConsoleHelper.createDirectoryWith(folderName).start();
			mkdirErr = readFrom(process);
		}
		while (!mkdirErr.isEmpty() && ConsoleHelper.directoryIsExists(mkdirErr, folderName));

		return folderName;
	}

	public static Map<String, String> getFolderNames()
	{
		return folderNames;
	}

	public static Map<String, Process> getProcesses()
	{
		return processes;
	}

	public static Map<String, List<String>> getOut()
	{
		return out;
	}
}
