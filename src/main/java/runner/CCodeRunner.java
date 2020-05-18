package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;
import utils.ConsoleHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class CCodeRunner extends BaseCodeRunner
{
	@Override
	CompilerEntity run(String code, String vars, String sessionId)
			throws IOException, CompilerException
	{
		prepareExecutionFile(code);

		List<String> errList = compileCode();

		CompilerEntity outEntity;
		if (errList.isEmpty())
		{
			outEntity = executeCode(vars, sessionId);
		} else
		{
			errList.add(0, COMPILE_ERRORS);

			outEntity = new CompilerEntity(Collections.emptyList(), errList, true);
		}

		if (outEntity.isCompleted())
		{
			processes.remove(sessionId);
			out.remove(sessionId);
			ConsoleHelper.deleteTemporaryData(folderName);
		} else
		{
			folderNames.put(sessionId, folderName);
		}

		return outEntity;
	}

	/**
	 * Подготовка исполняемого файла
	 *
	 * @param code - Код программы
	 * @throws IOException
	 * @throws CompilerException - Ошибкаы создания файла
	 */
	private void prepareExecutionFile(String code) throws IOException, CompilerException
	{
		executionFileName = "ExecutionFile";//extractExecutionFileName(code);
		folderName = createFolder(executionFileName);

		File newFile = ConsoleHelper.createCFile(folderName, executionFileName);

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
				ConsoleHelper.executeC(folderName, executionFileName);
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
		ProcessBuilder processBuilder = ConsoleHelper.compileC(folderName, executionFileName);
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
		int indexOfClassName = code.indexOf("class ") + 5;

		if (indexOfClassName == 4)
		{
			throw new CompilerException(CREATE_ERRORS + "\n   Code textarea is empty");
		}

		while (code.charAt(indexOfClassName) == ' ')
			indexOfClassName++;

		while ((code.charAt(indexOfClassName) != '{') && (code.charAt(indexOfClassName) != '\r') &&
				(code.charAt(indexOfClassName) != '\n') && (code.charAt(indexOfClassName) != ' '))
		{
			className.append(code.charAt(indexOfClassName));
			indexOfClassName++;
		}
		return className.toString();
	}
}