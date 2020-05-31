package runner;

import data.CompilerEntity;
import exception.CompilerException;
import util.Constants;
import util.TerminalHelper;

import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class OberonCodeRunner extends BaseCodeRunner
{
	@Override
	boolean compileIsComplete(List<String> compileErrorList)
	{
		return compileErrorList.size() == 1;
	}

	@Override
	void preProcessing(String code) throws IOException, CompilerException
	{
		executionFileName = extractExecutionFileName(code);
		folderName = createFolder(executionFileName);

		File newFile = TerminalHelper.createOberonFile(folderName, executionFileName);
		TerminalHelper.modifyFileWith(folderName + "/" + executionFileName + ".mod").start();

		if (!newFile.createNewFile())
		{
			throw new CompilerException("Cant create new file");
		}

		FileOutputStream fileOutputStream = new FileOutputStream(newFile);
		fileOutputStream.write(code.getBytes());
		fileOutputStream.close();
	}

	@Override
	CompilerEntity executeCode(String vars, String sessionId) throws IOException
	{
		return executeWithWaiting(
				vars, sessionId, TerminalHelper.executeOberon(folderName, executionFileName)
		);
	}

	@Override
	List<String> compileCode() throws IOException
	{
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new ProcessBuilder("pwd").start().getInputStream()
				)
		);
		String sourcePath = reader.readLine();

		AtomicReference<Process> process = new AtomicReference<>();
		ExecutorService executor = startProcess(
				TerminalHelper.compileOberon(folderName, executionFileName), process
		);
		while (!executor.isTerminated())
		{
		}

		//new ProcessBuilder("cp", sourcePath + executionFileName, ConsoleHelper.PATH + folderName + "/").start();
		new ProcessBuilder(
				"cp", sourcePath + "/" + executionFileName, Constants.LocalRun.PATH + folderName + "/"
		).start();
		new ProcessBuilder("rm", sourcePath + executionFileName + ".c").start();
		new ProcessBuilder("rm", sourcePath + executionFileName).start();

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
