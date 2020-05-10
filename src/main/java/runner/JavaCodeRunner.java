package runner;

import compiler.lelar.compiler.CompilerEntity;
import exception.CompilerException;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * Runner java-кода
 */
public class JavaCodeRunner extends BaseCodeRunner
{
	/**
	 * Имя папки с исполняемыми файлами
	 */
	private String folderName;

	/**
	 * Имя исполняемого файла
	 */
	private String executionFileName;

	@Override
	CompilerEntity run(String code, String vars, HttpServletRequest request)
			throws InterruptedException, IOException, CompilerException
	{
		prepareExecutionFile(code);

		InputStream stderr = compileAndReturnErrors();

		CompilerEntity outEntity;
		if (stderr.available() == 0)
		{
			outEntity = executeCode(vars);
		} else
		{
			List<String> errList = readFrom(stderr);
			errList.add(0, COMPILE_ERRORS);

			outEntity = new CompilerEntity(Collections.emptyList(), errList);
		}

		new ProcessBuilder("rm", "-R", PATH + folderName).start();

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
		executionFileName = extractExecutionFileName(code);
		folderName = createFolder(executionFileName);

		File newFile = new File(PATH + folderName + "/" + executionFileName + ".java");

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
	private CompilerEntity executeCode(String vars) throws IOException
	{
		Process process;
		InputStream stderr;
		CompilerEntity outEntity;
		process = new ProcessBuilder("java", "-cp", PATH + folderName + "/", executionFileName).start();

		OutputStream stdin = process.getOutputStream();
		stdin.write(vars.getBytes());
		stdin.close();

		InputStream stdout = process.getInputStream();
		stderr = process.getErrorStream();

		List<String> errList = readFrom(stderr);
		if (!errList.isEmpty())
		{
			errList.add(0, EXECUTION_ERRORS);
		}

		outEntity = new CompilerEntity(readFrom(stdout), errList);
		return outEntity;
	}

	/**
	 * Компиляция кода
	 *
	 * @return - Ошибки компиляции
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private InputStream compileAndReturnErrors() throws IOException, InterruptedException
	{
		Process process;
		//process = (Process) request.getSession().getAttribute("process");
		//if (process == null)
		//{
		process = new ProcessBuilder("javac", PATH + folderName + "/" + executionFileName + ".java").start();
		//}
		Thread.sleep(COMPILE_TIME);

		return process.getErrorStream();
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