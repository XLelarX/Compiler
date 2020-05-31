package data;

import java.util.List;

/**
 * Данные пользователя
 */
public class UserData
{
	/**
	 * Процесс с которым работает пользователь
	 */
	private Process process;

	/**
	 * Результат выполнения программы пользователя
	 */
	private List<String> out;

	/**
	 * Название папки с файлами пользователя
	 */
	private String folderName;

	/**
	 * Время выполнения программы
	 */
	private Long executionTime;

	public UserData()
	{
	}

	public UserData(Process process)
	{
		this.process = process;
	}

	public Process getProcess()
	{
		return process;
	}

	public void setProcess(Process process)
	{
		this.process = process;
	}

	public List<String> getOut()
	{
		return out;
	}

	public void setOut(List<String> out)
	{
		this.out = out;
	}

	public String getFolderName()
	{
		return folderName;
	}

	public void setFolderName(String folderName)
	{
		this.folderName = folderName;
	}

	public Long getExecutionTime()
	{
		return executionTime;
	}

	public void setExecutionTime(Long executionTime)
	{
		this.executionTime = executionTime;
	}
}
