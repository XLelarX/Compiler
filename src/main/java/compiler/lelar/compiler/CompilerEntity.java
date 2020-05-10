package compiler.lelar.compiler;

import java.util.Collections;
import java.util.List;

/**
 * Сущность для взаимодействия {@link CompilerAction} и наследников {@link runner.BaseCodeRunner}
 */
public class CompilerEntity
{
	/**
	 * Вывод выполненной программы
	 */
	private List<String> out;

	/**
	 * Ошибки при выполнении программы
	 */
	private List<String> errors;

	/**
	 * Завершилась ли программы
	 */
	private boolean completed;

	public CompilerEntity(List<String> out, List<String> errors)
	{
		this.out = out;
		this.errors = errors;
	}

	public CompilerEntity()
	{
		out = Collections.emptyList();
		errors = Collections.emptyList();
	}

	List<String> getOut()
	{
		return out;
	}

	void setOut(List<String> out)
	{
		this.out = out;
	}

	List<String> getErrors()
	{
		return errors;
	}

	void setErrors(List<String> errors)
	{
		this.errors = errors;
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
