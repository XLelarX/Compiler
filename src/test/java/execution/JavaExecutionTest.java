package execution;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.*;
import runner.BaseCodeRunner;
import utils.TerminalHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JavaExecutionTest extends BaseExecutionTest
{
	@BeforeAll
	static void compile() throws InterruptedException
	{
		TerminalHelper.compileTestJava(fileName);
		Thread.sleep(1000);
	}

	@BeforeEach
	void init() throws IOException
	{
		ProcessBuilder builder = TerminalHelper.executeTestJava(fileName);
		builder.redirectErrorStream(true);
		process = builder.start();
		builder.redirectError().file();
	}

	@AfterAll
	static void delete()
	{
		new File(TESTS_PATH + "java/" + fileName + ".class").delete();
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

		TerminalHelper.killProcess(process.pid());

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

		TerminalHelper.killProcess(process.pid());

		checkResult(stdout);
	}

	@Test
	void testExecutionWithError() throws IOException
	{
		BaseCodeRunner.getProcesses().put(SESSION_ID, process);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1", "afsaf").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		List<String> stdout = BaseCodeRunner.getOut().get(SESSION_ID);

		TerminalHelper.killProcess(process.pid());

		checkResultWithException(stdout);
	}

	private void checkResult(List<String> realList)
	{
		List<String> expectedList =
				ImmutableList.<String>builder().add("1", "first answer", "2", "second answer").build();

		Assertions.assertEquals(expectedList, realList);
	}

	private void checkResultWithException(List<String> realList)
	{
		List<String> expectedList =
				ImmutableList.<String>builder()
						.add("1", "first answer")
						.add("Exception in thread \"main\" java.util.InputMismatchException")
						.add("\tat java.base/java.util.Scanner.throwFor(Scanner.java:860)")
						.add("\tat java.base/java.util.Scanner.next(Scanner.java:1497)")
						.add("\tat java.base/java.util.Scanner.nextInt(Scanner.java:2161)")
						.add("\tat java.base/java.util.Scanner.nextInt(Scanner.java:2115)")
						.add("\tat ExecutionTest.main(ExecutionTest.java:10)")
						.build();

		Assertions.assertEquals(expectedList, realList);
	}
}
