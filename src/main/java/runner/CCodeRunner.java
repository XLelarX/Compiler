package runner;

import data.CompilerEntity;
import exception.CompilerException;
import util.TerminalHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class CCodeRunner extends BaseCodeRunner
{
	@Override
	boolean compileIsComplete(List<String> compileErrorList)
	{
		return compileErrorList.isEmpty();
	}

	@Override
	void preProcessing(String code) throws IOException, CompilerException
	{
		executionFileName = "ExecutionFile";
		folderName = createFolder(executionFileName);

		File newFile = TerminalHelper.createCFile(folderName, executionFileName);

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
		return executeWithWaiting(
				vars, sessionId, TerminalHelper.executeC(folderName, executionFileName)
		);
	}

	@Override
	List<String> compileCode() throws IOException
	{
		AtomicReference<Process> process = new AtomicReference<>();
		ExecutorService executor = startProcess(
				TerminalHelper.compileC(folderName, executionFileName), process
		);
		while (!executor.isTerminated())
		{
		}

		return readFrom(process.get());
	}
}
