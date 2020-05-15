package execution;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import runner.BaseCodeRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ExecutionTest
{
	private static final String SESSION_ID = "testSessionId";
	private static Process process;

	@BeforeEach
	void init() throws IOException
	{
		ProcessBuilder builder = new ProcessBuilder("java", "-cp", "/Users/Lelar/Desktop/JavaProjects/test/ExecutionTest/", "ExecutionTest");
		builder.redirectErrorStream(true);
		process = builder.start();
		builder.redirectError().file();
	}

	@Test
	void test2StepExecution() throws IOException
	{

		//AtomicReference<Process> atomicReference = new AtomicReference<>();
		//atomicReference.set(process);

		BaseCodeRunner.processes.put(SESSION_ID, process);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("2").build()
		);

		List<String> stdout = BaseCodeRunner.getResultFor(SESSION_ID);

		new ProcessBuilder("kill", "-9", String.valueOf(process.pid())).start();

		System.out.println(stdout);
		checkResult(stdout);
	}

	@Test
	void testExecution() throws IOException
	{
		//AtomicReference<Process> atomicReference = new AtomicReference<>();
		//atomicReference.set(process);

		BaseCodeRunner.processes.put(SESSION_ID, process);


		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1", "2").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		List<String> stdout = BaseCodeRunner.getResultFor(SESSION_ID);

		new ProcessBuilder("kill", "-9", String.valueOf(process.pid())).start();

		checkResult(stdout);
	}

	@Test
	void testExecutionWithError() throws IOException
	{
		//AtomicReference<Process> atomicReference = new AtomicReference<>();
		//atomicReference.set(process);

		BaseCodeRunner.processes.put(SESSION_ID, process);

		BaseCodeRunner.writeInProcess(
				SESSION_ID, ImmutableList.<String>builder().add("1", "afsaf").build()
		);
		BaseCodeRunner.readFrom(SESSION_ID);

		List<String> stdout = BaseCodeRunner.getResultFor(SESSION_ID);

		new ProcessBuilder("kill", "-9", String.valueOf(process.pid())).start();

		System.out.println(stdout);
		//checkResult(stdout);
	}

	private void checkResult(List<String> realList)
	{
		List<String> expectedList =
				ImmutableList.<String>builder().add("1", "first answer", "2", "second answer").build();

		Assertions.assertEquals(expectedList, realList);
	}
}
