package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Runner java-кода
 */
public class JavaCodeRunner extends BaseCodeRunner
{
	private static final String FILE_FORMAT = ".java";

	/**
	 * Имя папки с исполняемыми файлами
	 */
	private String folderName;

	/**
	 * Имя исполняемого файла
	 */
	private String executionFileName;

	@Override
	CompilerEntity run(String code, String vars, String sessionId)
			throws IOException, CompilerException
	{
		prepareExecutionFile(code, sessionId);

		List<String> errList = compileAndReturnErrors();

		CompilerEntity outEntity;
		if (errList.isEmpty())
		{
			outEntity = executeCode(vars, sessionId);
		} else
		{
			//List<String> errList = readFrom(sessionId);
			errList.add(0, COMPILE_ERRORS);

			outEntity = new CompilerEntity(Collections.emptyList(), errList, true);
		}

		if (outEntity.isCompleted())
		{
			removeTemporaryData(folderName);
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
	private void prepareExecutionFile(String code, String sessionId) throws IOException, CompilerException
	{
		executionFileName = extractExecutionFileName(code);
		folderName = createFolder(executionFileName, sessionId);

		File newFile = new File(PATH + folderName + "/" + executionFileName + FILE_FORMAT);

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
//		AtomicReference<Process> process = processes.get(sessionId);

//		if (process.get() == null)
//		{
		//process = new AtomicReference<>();
//		}

		//InputStream stderr;
		//CompilerEntity outEntity;
		ProcessBuilder processBuilder =
				new ProcessBuilder("java", "-cp", PATH + folderName + "/", executionFileName);
		processBuilder.redirectErrorStream(true);

		ExecutorService executor = Executors.newCachedThreadPool();
//		AtomicReference<Process> finalProcess = process;
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

//			OutputStream stdin = process.get().getOutputStream();
//			stdin.write(vars.getBytes());
//			stdin.close();
//			time = System.currentTimeMillis();
			while (!executor.isTerminated() && System.currentTimeMillis() - time < EXECUTION_TIME)
			{
			}
		}

		boolean complete = true;

		if (!executor.isTerminated())
		{
			processes.put(sessionId, process.get());//finalProcess);
			//executor.shutdownNow();
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
		//Collections.singletonList("Ожидаются данные"));
//		InputStream stdout = process.get().getInputStream();
//		stderr = process.get().getErrorStream();
//
//		List<String> errList = readFrom(sessionId);
//		if (!errList.isEmpty())
//		{-
//			errList.add(0, EXECUTION_ERRORS);
//		}

//		outEntity = new CompilerEntity(readFrom(sessionId), errList);
//		return outEntity;
	}

	/**
	 * Компиляция кода
	 *
	 * @return - Ошибки компиляции
	 */
	private List<String> compileAndReturnErrors() throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();
		ProcessBuilder processBuilder =
				new ProcessBuilder("javac", PATH + folderName + "/" + executionFileName + FILE_FORMAT);
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
		int indexOfClassName = code.indexOf("class ") + 5;//TODO если текст пустой

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