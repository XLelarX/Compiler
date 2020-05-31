package form;

import org.apache.struts.action.ActionForm;

/**
 * Класс-отображение для страницы
 */
public class CompilerForm extends ActionForm
{
	/**
	 * Поле с кодом
	 */
	private String request;

	/**
	 * Поле с ответом
	 */
	private String response;

	/**
	 * Поле с передаваемыми параметрами
	 */
	private String vars;

	/**
	 * Поле со списком языков
	 */
	private String language;

	/**
	 * Невидимое поле, обозначающее конец выполнения программы
	 */
	private boolean complete;

	public String getRequest()
	{
		return request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

	public String getResponse()
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}

	public String getVars()
	{
		return vars;
	}

	public void setVars(String vars)
	{
		this.vars = vars;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}
}
