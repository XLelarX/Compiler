package runner;

import data.CompilerEntity;
import data.UserData;
import exception.CompilerException;
import util.TerminalHelper;
import logger.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Базовый класс Runner-ов
 */
public abstract class BaseCodeRunner
{
	final static String CREATE_ERRORS = "Create errors: ";
	private final static long EXECUTION_TIME = 2000;

	private volatile static Map<String, UserData> userDataMap = new HashMap<>();

	/**
	 * Имя папки с исполняемыми файлами
	 */
	String folderName;

	/**
	 * Имя исполняемого файла
	 */
	String executionFileName;

	/**
	 * Подготовка к выполнению
	 *
	 * @param code - Код программы
	 * @throws IOException
	 * @throws CompilerException - Ошибка создания файла
	 */
	abstract void preProcessing(String code) throws IOException, CompilerException;

	/**
	 * Компиляция кода
	 *
	 * @return - Ошибки компиляции
	 */
	abstract List<String> compileCode() throws IOException;

	/**
	 * Показатель успешности выполнения компиляции
	 *
	 * @param compileErrorList - ошибки компиляции
	 * @return - true - успешно, иначе нет
	 */
	abstract boolean compileIsComplete(List<String> compileErrorList);

	/**
	 * Выполнение скомпилировааной программы
	 *
	 * @param vars - Переменные, передаваемые в программу, во время ее выполнения
	 * @return - Результат выполнения программы
	 * @throws IOException
	 */
	abstract CompilerEntity executeCode(String vars, String sessionId) throws IOException;

	/**
	 * Запуск компиляции и выполнения
	 *
	 * @param code      - Код программы
	 * @param vars      - Переменные, передаваемые в программу, во время ее выполнения
	 * @param sessionId - id пользователя
	 * @return - Результат запуска программы
	 */
	private CompilerEntity run(String code, String vars, String sessionId)
			throws IOException, CompilerException
	{
		preProcessing(code);

		long compileTime = System.currentTimeMillis();
		List<String> compileErrorList = compileCode();
		compileTime = System.currentTimeMillis() - compileTime;

		CompilerEntity compilerEntity;
		long executionTime;
		if (compileIsComplete(compileErrorList))
		{
			executionTime = System.currentTimeMillis();
			compilerEntity = executeCode(vars, sessionId);
		} else
		{
			compileErrorList.add(0, "Compile errors: ");
			TerminalHelper.deleteTemporaryData(folderName);
			return new CompilerEntity(compileErrorList, true);
		}

		postProcessing(compilerEntity, compileTime, executionTime, sessionId, folderName);

		return compilerEntity;
	}

	/**
	 * Запуск корректного runner-a
	 *
	 * @param code      - Код программы
	 * @param vars      - Переменные, передаваемые в программу, во время ее выполнения
	 * @param sessionId - Входящий запрос
	 * @return - Результат запуска программы
	 * @throws CompilerException - Исключение во время работы программы
	 */
	public CompilerEntity start(String code, String vars, String sessionId)
			throws CompilerException
	{
		ExecutorService service = Executors.newSingleThreadExecutor();
		AtomicReference<CompilerEntity> atomicCompilerEntity = new AtomicReference<>();

		try
		{
			service.submit(() -> {
				try
				{
					atomicCompilerEntity.set(run(code, vars == null ? "" : vars, sessionId));
				} catch (IOException | CompilerException e)
				{
					Logger.fillLog(e);
					atomicCompilerEntity.set(new CompilerEntity(Collections.singletonList(e.getMessage())));
				}
			}).get(10000, TimeUnit.SECONDS);// attempt the task for two minutes
		} catch (InterruptedException | TimeoutException | ExecutionException e)
		{
			Logger.fillLog(e);
			throw new CompilerException("Execution errors: \n" + e);
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
		UserData userData = userDataMap.get(sessionId);
		Process process = userData.getProcess();

		if (process != null)
		{
			List<String> output = new ArrayList<>();
			userData.setOut(output);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
		Process atomicProcess = userDataMap.get(sessionId).getProcess();

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
			process = TerminalHelper.createDirectoryWith(folderName).start();
			while (process.isAlive())
			{
			}
			Process modifyProcess = TerminalHelper.modifyFileWith(folderName).start();
			while (modifyProcess.isAlive())
			{
			}

			mkdirErr = readFrom(process);
		}
		while (!mkdirErr.isEmpty() && TerminalHelper.directoryIsExists(mkdirErr, folderName));

		return folderName;
	}

	private static void postProcessing(
			CompilerEntity compilerEntity, long compileTime,
			long executionTime, String sessionId, String folderName
	) throws IOException
	{
		if (compilerEntity.isCompleted())
		{
			List<String> out = compilerEntity.getOut();
			out.add(0, "Compile time: " + compileTime / 1000 + " sec");
			out.add("Execution time: " + (System.currentTimeMillis() - executionTime) / 1000 + " sec");
			userDataMap.remove(sessionId);
			TerminalHelper.deleteTemporaryData(folderName);
		} else
		{
			UserData userData = userDataMap.get(sessionId);
			userData.getOut().add(0, "Compile time: " + compileTime / 1000 + " sec");
			userData.setExecutionTime(executionTime);
			userData.setFolderName(folderName);
		}
	}

	static ExecutorService startProcess(ProcessBuilder processBuilder, AtomicReference<Process> process)
	{
		processBuilder.redirectErrorStream(true);

		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(
				new FutureTask(
						(Callable<Integer>) () -> {
							process.set(processBuilder.start());
							return process.get().waitFor();
						})
		);
		executor.shutdown();

		return executor;
	}

	static CompilerEntity executeWithWaiting(
			String vars, String sessionId, ProcessBuilder processBuilder
	) throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();

		ExecutorService executor = startProcess(processBuilder, process);

		long time = System.currentTimeMillis();
		while (!executor.isTerminated() && System.currentTimeMillis() - time < EXECUTION_TIME)
		{
		}

		if (!vars.equals("") && !executor.isTerminated())
		{
			writeInProcess(process.get(), Collections.singletonList(vars));

			time = System.currentTimeMillis();
			while (!executor.isTerminated() && System.currentTimeMillis() - time < EXECUTION_TIME)
			{
			}
		}

		boolean complete = true;

		if (!executor.isTerminated())
		{
			userDataMap.put(sessionId, new UserData(process.get()));
			complete = false;
		}

		List<String> output;
		if (complete)
		{
			output = readFrom(process.get());
		} else
		{
			output = readFrom(sessionId);
		}

		return new CompilerEntity(output, complete);
	}

	public static Map<String, UserData> getUserDataMap()
	{
		return userDataMap;
	}
}
