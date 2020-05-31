package controller;

import data.CompilerEntity;
import form.CompilerForm;
import exception.CompilerException;
import logger.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import runner.CCodeRunner;
import runner.JavaCodeRunner;
import runner.OberonCodeRunner;
import util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstRequestRestService extends BaseRestService
{
	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response
	) throws IOException
	{
		request.setCharacterEncoding(Constants.CHAR_ENCODING);

		CompilerForm compilerForm = (CompilerForm) form;
		String code = fixRequest(compilerForm.getRequest());
		String vars = fixRequest(compilerForm.getVars());
		compilerForm.setRequest(code);
		compilerForm.setVars(vars);

		if (code.length() < 30)
		{
			fillResponse(compilerForm, "Not correct input data", response);
			return null;
		}

		BaseCodeRunner codeRunner = chooseRunner(compilerForm.getLanguage(), code);

		if (codeRunner == null)
		{
			fillResponse(compilerForm, "Not correct language", response);
			return null;
		}

		CompilerEntity compilerEntity;
		try
		{
			compilerEntity = codeRunner.start(code, vars, request.getSession().getId());
		} catch (CompilerException e)
		{
			Logger.fillLog(e);
			fillResponse(compilerForm, e.getMessage(), response);
			return null;
		}

		StringBuilder result = new StringBuilder();

		compilerEntity.getOut().forEach(line -> result.append(line).append("\r\n"));
		compilerForm.setComplete(compilerEntity.isCompleted());
		fillResponse(compilerForm, result.toString(), response);

		return null;
	}

	/**
	 * Выбор runner-а
	 *
	 * @param language - Язык программирования
	 * @param code     - Код программы
	 */
	private BaseCodeRunner chooseRunner(String language, String code)
	{
		BaseCodeRunner codeRunner = null;

		switch (language)
		{
			case "Java":
				if (code.contains("import ") || code.contains("class ") || code.contains("public "))
				{
					codeRunner = new JavaCodeRunner();
				}
				break;
			case "Oberon":
				if (code.contains("MODULE ") || code.contains("BEGIN"))
				{
					codeRunner = new OberonCodeRunner();
				}
				break;
			case "C":
				if (code.contains("#define ") || code.contains("#include "))
				{
					codeRunner = new CCodeRunner();
				}
				break;
		}

		return codeRunner;
	}
}
