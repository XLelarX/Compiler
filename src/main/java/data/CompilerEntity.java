package data;

import java.util.Collections;
import java.util.List;

/**
 * Данные для взаимодействия наследников {@link controller.BaseRestService}
 * 		с наследниками {@link runner.BaseCodeRunner}
 */
public class CompilerEntity
{
	/**
	 * Результат выполненной программы
	 */
	private List<String> out;

	/**
	 * Завершилась ли программа
	 */
	private boolean completed;

	public CompilerEntity(List<String> out, boolean completed)
	{
		this.out = out;
		this.completed = completed;
	}

	public CompilerEntity(List<String> out)
	{
		this.out = out;
	}

	public CompilerEntity()
	{
		out = Collections.emptyList();
		completed = false;
	}

	public List<String> getOut()
	{
		return out;
	}

	void setOut(List<String> out)
	{
		this.out = out;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}
}
