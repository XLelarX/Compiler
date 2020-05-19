package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;
import utils.ConsoleHelper;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class OberonCodeRunner extends BaseCodeRunner
{
	@Override
	CompilerEntity run(String code, String vars, String sessionId)
			throws IOException, CompilerException
	{
		prepareExecutionFile(code);

		long compileTime = System.currentTimeMillis();
		List<String> errList = compileCode();
		compileTime = System.currentTimeMillis() - compileTime;

		CompilerEntity outEntity;
		long executionTime = 0;
		if (errList.size() == 1)
		{
			outEntity = executeCode(vars, sessionId);
		} else
		{
			errList.add(0, COMPILE_ERRORS);

			executionTime = System.currentTimeMillis();
			outEntity = new CompilerEntity(Collections.emptyList(), errList, true);
			if (outEntity.isCompleted())
			{
				executionTime = System.currentTimeMillis() - executionTime;
			}
		}

		if (outEntity.isCompleted())
		{
			outEntity.getOut().add(0, "Compile time: " + compileTime / 1000 + " sec");
			outEntity.getOut().add("Execution time: " + executionTime);
			processes.remove(sessionId);
			out.remove(sessionId);
			ConsoleHelper.deleteTemporaryData(folderName);
		} else
		{
			out.get(sessionId).add(0, "Compile time: " + compileTime / 1000 + " sec");
			folderNames.put(sessionId, folderName);
		}

		return outEntity;
	}

	/**
	 * Подготовка исполняемого файла
	 *
	 * @param code - Код программы
	 * @throws IOException
	 * @throws CompilerException - Ошибка создания файла
	 */
	private void prepareExecutionFile(String code) throws IOException, CompilerException
	{
		executionFileName = extractExecutionFileName(code);
		folderName = createFolder(executionFileName);

		File newFile = ConsoleHelper.createOberonFile(folderName, executionFileName);

		if (!newFile.createNewFile())
		{
			throw new CompilerException("File don't exists");
		}

		FileOutputStream fileOutputStream = new FileOutputStream(newFile);
		fileOutputStream.write(code.getBytes());
		fileOutputStream.close();
	}

	/**
	 * Выполнение скомпилировааной программы
	 *
	 * @param vars - Переменные, передаваемые в программу, во время ее выполнения
	 * @return - Результат выполнения программы
	 * @throws IOException
	 */
	private CompilerEntity executeCode(String vars, String sessionId) throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();
		ProcessBuilder processBuilder =
				ConsoleHelper.executeOberon(folderName, executionFileName);
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

		long time = System.currentTimeMillis();
		while (!executor.isTerminated() && System.currentTimeMillis() - time < EXECUTION_TIME)
		{
		}

		if (!vars.equals("") && !executor.isTerminated())
		{
			writeInProcess(process.get(), Collections.singletonList(vars));

			while (!executor.isTerminated() && System.currentTimeMillis() - time < EXECUTION_TIME)
			{
			}
		}

		boolean complete = true;

		if (!executor.isTerminated())
		{
			processes.put(sessionId, process.get());
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

		return new CompilerEntity(output, Collections.emptyList(), complete);
	}

	/**
	 * Компиляция кода
	 *
	 * @return - Ошибки компиляции
	 */
	private List<String> compileCode() throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();

		Process innerProcess = new ProcessBuilder("pwd").start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(innerProcess.getInputStream()));
		String sourcePath = reader.readLine();

		ProcessBuilder processBuilder = ConsoleHelper.compileOberon(folderName, executionFileName);
		ExecutorService executor = Executors.newCachedThreadPool();

		executor.submit(
				new FutureTask(
						(Callable<Integer>) () -> {
							process.set(processBuilder.start());
							return process.get().waitFor();
						})
		);
		executor.shutdown();
		while (!executor.isTerminated())
		{
		}
		new ProcessBuilder("cp", sourcePath + "/" + executionFileName, "/Users/Lelar/Desktop/JavaProjects/execution/" + folderName + "/").start();

		return readFrom(process.get());
	}

	/**
	 * Определение названия исполняемого файла для временного сохранения на сервере
	 *
	 * @param code - Код программы
	 * @return - Название файла
	 */
	private String extractExecutionFileName(String code) throws CompilerException
	{
		StringBuilder className = new StringBuilder();
		int indexOfClassName = code.indexOf("MODULE ") + 6;

		if (indexOfClassName == 5)
		{
			throw new CompilerException(CREATE_ERRORS + "\n   Code textarea is empty");
		}

		while (code.charAt(indexOfClassName) == ' ')
			indexOfClassName++;

		while ((code.charAt(indexOfClassName) != ';') && (code.charAt(indexOfClassName) != '\r') &&
				(code.charAt(indexOfClassName) != '\n') && (code.charAt(indexOfClassName) != ' '))
		{
			className.append(code.charAt(indexOfClassName));
			indexOfClassName++;
		}
		return className.toString();
	}
}
