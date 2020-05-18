package execution;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import runner.BaseCodeRunner;
import utils.ConsoleHelper;

import java.io.IOException;
import java.util.List;

public class CExecutionTest
{
	private static final String SESSION_ID = "testSessionId";
	private static Process process;

	@BeforeEach
	void init() throws IOException
	{
		ProcessBuilder builder = ConsoleHelper.executeTestC("ExecutionTest");
		builder.redirectErrorStream(true);
		process = builder.start();
		builder.redirectError().file();
	}

	@Test
	void test2StepExecution() throws IOException
	{
		BaseCodeRunner.getProcesses().put(SESSION_ID, process);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("2").build()
		);

		List<String> stdout = BaseCodeRunner.getOut().get(SESSION_ID);

		ConsoleHelper.killProcess(process.pid());

		checkResult(stdout);
	}

	@Test
	void testExecution() throws IOException
	{
		BaseCodeRunner.getProcesses().put(SESSION_ID, process);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1", "2").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		List<String> stdout = BaseCodeRunner.getOut().get(SESSION_ID);

		ConsoleHelper.killProcess(process.pid());

		checkResult(stdout);
	}

	private void checkResult(List<String> realList)
	{
		List<String> expectedList =
				ImmutableList.<String>builder().add("Insert x: ", "Value of x=1.000000", "Insert y: ", "Value of y=2.000000").build();

		Assertions.assertEquals(expectedList, realList);
	}

}
