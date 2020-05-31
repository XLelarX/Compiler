package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TerminalHelper
{
	private static final String TESTS_PATH = "/Users/Lelar/Desktop/JavaProjects/test/ExecutionTest/";

	public static ProcessBuilder createDirectoryWith(String name)
	{
		return new ProcessBuilder("mkdir", Constants.LocalRun.PATH + name);
	}

	public static ProcessBuilder modifyFileWith(String name)
	{
		return new ProcessBuilder("chmod", "o+rwx", Constants.LocalRun.PATH + name);
	}

	public static boolean directoryIsExists(List<String> errorList, String folderName)
	{
		return errorList.get(0).equals("mkdir: " + Constants.LocalRun.PATH + folderName + ": File exists");
	}

	public static void deleteTemporaryData(String folderName) throws IOException
	{
		new ProcessBuilder("rm", "-R", Constants.LocalRun.PATH + folderName).start();
	}

	public static void killProcess(long pid) throws IOException
	{
		new ProcessBuilder("kill", "-9", String.valueOf(pid)).start();
	}

	public static ProcessBuilder executeJava(String folderName, String fileName)
	{
		return new ProcessBuilder("java", "-cp", Constants.LocalRun.PATH + folderName + "/", fileName);
	}

	public static ProcessBuilder executeC(String folderName, String fileName)
	{
		return new ProcessBuilder(Constants.LocalRun.PATH + folderName + "/" + fileName);
	}

	public static ProcessBuilder executeOberon(String folderName, String fileName)
	{
		return new ProcessBuilder(Constants.LocalRun.PATH + folderName + "/" + fileName);
	}

	public static ProcessBuilder executeTestJava(String fileName)
	{
		return new ProcessBuilder("java", "-cp", TESTS_PATH + "java/", fileName);
	}

	public static ProcessBuilder executeTestC(String fileName)
	{
		return new ProcessBuilder(TESTS_PATH + "c/" + fileName);
	}

	public static ProcessBuilder executeTestOberon(String fileName)
	{
		return new ProcessBuilder(TESTS_PATH + "oberon/" + fileName);
	}

	public static ProcessBuilder compileJava(String folderName, String fileName)
	{
		return new ProcessBuilder("javac", Constants.LocalRun.PATH + folderName + "/" + fileName + Constants.FileFormat.JAVA_FILE_FORMAT);
	}

	public static ProcessBuilder compileTestJava(String fileName)
	{
		return new ProcessBuilder("javac", TESTS_PATH + "java/" + fileName + Constants.FileFormat.JAVA_FILE_FORMAT);
	}

	public static ProcessBuilder compileC(String folderName, String fileName)
	{
		return new ProcessBuilder(
				"gcc", Constants.LocalRun.PATH + folderName + "/" + fileName + Constants.FileFormat.C_FILE_FORMAT, "-o", Constants.LocalRun.PATH + folderName + "/" + fileName
		);
	}

	public static ProcessBuilder compileTestC(String fileName)
	{
		return new ProcessBuilder(
				"gcc", TESTS_PATH + "c/" + fileName + Constants.FileFormat.C_FILE_FORMAT, "-o", TESTS_PATH + "c/" + fileName
		);
	}

	public static ProcessBuilder compileOberon(String folderName, String fileName)
	{
		return new ProcessBuilder(
				Constants.LocalRun.VOC_PATH, Constants.LocalRun.PATH + folderName + "/" + fileName + ".mod", "-m"
		);
	}

	public static Process compileTestOberon(String fileName) throws IOException
	{
		Process innerProcess = new ProcessBuilder("pwd").start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(innerProcess.getInputStream()));
		String sourcePath = reader.readLine();

		innerProcess = new ProcessBuilder(
				Constants.LocalRun.VOC_PATH, TESTS_PATH + "oberon/" + fileName + ".mod", "-m"
		).start();
		new ProcessBuilder("cp", sourcePath + "/ExecutionTest", TESTS_PATH).start();

		return innerProcess;
	}

	public static File createJavaFile(String folderName, String fileName)
	{
		return new File(Constants.LocalRun.PATH + folderName + "/" + fileName + Constants.FileFormat.JAVA_FILE_FORMAT);
	}

	public static File createCFile(String folderName, String fileName)
	{
		return new File(Constants.LocalRun.PATH + folderName + "/" + fileName + Constants.FileFormat.C_FILE_FORMAT);
	}

	public static File createOberonFile(String folderName, String fileName)
	{
		return new File(Constants.LocalRun.PATH + folderName + "/" + fileName + Constants.FileFormat.OBERON_FILE_FORMAT);
	}
}
