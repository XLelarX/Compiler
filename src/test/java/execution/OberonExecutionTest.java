package execution;

import com.google.common.collect.ImmutableList;
import data.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import runner.BaseCodeRunner;
import util.TerminalHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class OberonExecutionTest
{
	private static final String SESSION_ID = "testSessionId";
	private static Process process;
	private static final String TESTS_PATH = "/Users/Lelar/Desktop/JavaProjects/test/ExecutionTest/oberon/";


	@BeforeAll
	static void compile() throws IOException
	{
		Process innerProcess = new ProcessBuilder("pwd").start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(innerProcess.getInputStream()));
		String sourcePath = reader.readLine();

		TerminalHelper.compileTestOberon("ExecutionTest");

		new ProcessBuilder("cp", sourcePath + "/ExecutionTest", TESTS_PATH).start();
	}

	@BeforeEach
	void init() throws IOException
	{
		ProcessBuilder builder = TerminalHelper.executeTestOberon("ExecutionTest");
		builder.redirectErrorStream(true);
		process = builder.start();
		builder.redirectError().file();
	}

	@Test
	void test2StepExecution() throws IOException
	{
		Map<String, UserData> userDataMap = BaseCodeRunner.getUserDataMap();
		userDataMap.put(SESSION_ID, new UserData(process));

		BaseCodeRunner.readFrom(SESSION_ID);

		List<String> stdout = userDataMap.get(SESSION_ID).getOut();

		TerminalHelper.killProcess(process.pid());

		checkResult(stdout);
	}

	private void checkResult(List<String> realList)
	{
		Assertions.assertEquals(ImmutableList.<String>builder().add("Hello.").build(), realList);
	}
}
