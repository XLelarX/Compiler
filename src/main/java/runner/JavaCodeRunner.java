package runner;

import data.CompilerEntity;
import exception.CompilerException;
import util.TerminalHelper;

import java.io.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Runner java-кода
 */
public class JavaCodeRunner extends BaseCodeRunner
{
	@Override
	boolean compileIsComplete(List<String> compileErrorList)
	{
		return compileErrorList.isEmpty();
	}

	@Override
	void preProcessing(String code) throws IOException, CompilerException
	{
		executionFileName = extractExecutionFileName(code);
		folderName = createFolder(executionFileName);

		File newFile = TerminalHelper.createJavaFile(folderName, executionFileName);

		if (!newFile.createNewFile())
		{
			throw new CompilerException("File don't exists");
		}

		FileOutputStream fileOutputStream = new FileOutputStream(newFile);
		fileOutputStream.write(code.getBytes());
		fileOutputStream.close();
	}

	@Override
	CompilerEntity executeCode(String vars, String sessionId) throws IOException
	{
		CompilerEntity compilerEntity = executeWithWaiting(
				vars, sessionId, TerminalHelper.executeJava(folderName, executionFileName)
		);

		List<String> output = compilerEntity.getOut();
		if (output.contains("NOTE: Picked up JDK_JAVA_OPTIONS:  " +
				"--add-opens=java.base/java.lang=ALL-UNNAMED " +
				"--add-opens=java.base/java.io=ALL-UNNAMED " +
				"--add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED")
		)
		{
			output.remove(0);
		}

		return compilerEntity;
	}

	@Override
	List<String> compileCode() throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();
		ExecutorService executor = startProcess(
				TerminalHelper.compileJava(folderName, executionFileName), process
		);
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