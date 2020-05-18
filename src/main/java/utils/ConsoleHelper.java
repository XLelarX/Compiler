package utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConsoleHelper
{
	private static final String PATH = "/Users/Lelar/Desktop/JavaProjects/execution/";
	private static final String TESTS_PATH = "/Users/Lelar/Desktop/JavaProjects/test/ExecutionTest/";
	private static final String JAVA_FILE_FORMAT = ".java";
	private static final String C_FILE_FORMAT = ".c";

	public static ProcessBuilder createDirectoryWith(String name)
	{
		return new ProcessBuilder("mkdir", PATH + name);
	}

	public static boolean directoryIsExists(List<String> errorList, String folderName)
	{
		return errorList.get(0).equals("mkdir: " + PATH + folderName + ": File exists");
	}

	public static void deleteTemporaryData(String folderName) throws IOException
	{
		new ProcessBuilder("rm", "-R", PATH + folderName).start();
	}

	public static void killProcess(long pid) throws IOException
	{
		new ProcessBuilder("kill", "-9", String.valueOf(pid)).start();
	}

	public static ProcessBuilder executeJava(String folderName, String fileName)
	{
		return new ProcessBuilder("java", "-cp", PATH + folderName + "/", fileName);
	}

	public static ProcessBuilder executeC(String folderName, String fileName)
	{
		return new ProcessBuilder(PATH + folderName + "/" + fileName);
	}

	public static ProcessBuilder executeTestJava(String fileName)
	{
		return new ProcessBuilder("java", "-cp", TESTS_PATH + "java/", fileName);
	}

	public static ProcessBuilder executeTestC(String fileName)
	{
		return new ProcessBuilder(TESTS_PATH + "c/" + fileName);
	}

	public static ProcessBuilder compileJava(String folderName, String fileName)
	{
		return new ProcessBuilder("javac", PATH + folderName + "/" + fileName + JAVA_FILE_FORMAT);
	}

	public static ProcessBuilder compileC(String folderName, String fileName)
	{
		return new ProcessBuilder(
				"gcc -o " + fileName + " " + PATH + folderName + "/" + fileName + C_FILE_FORMAT
		);
	}

	public static File createJavaFile(String folderName, String fileName)
	{
		return new File(PATH + folderName + "/" + fileName + JAVA_FILE_FORMAT);
	}

	public static File createCFile(String folderName, String fileName)
	{
		return new File(PATH + folderName + "/" + fileName + C_FILE_FORMAT);
	}
}
