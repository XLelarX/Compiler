package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerEntity;
import compiler.lelar.compiler.CompilerForm;
import exception.CompilerException;
import logger.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import runner.CCodeRunner;
import runner.JavaCodeRunner;
import runner.OberonCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FirstRequestRestService extends BaseRestService
{
	private Gson gson = new GsonBuilder().create();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String code = compilerForm.getRequest()
				.replace("/plus", "+")
				.replace("/enter", "\n")
				.replace("/tab", "\t")
				.replace("/cell", "#")
				.replace("/percent", "%")
				.replace("/and", "&")
				.replace("/lcb", "{")
				.replace("/rcb", "}")
				.replace("/lbb", "[")
				.replace("/rbb", "]")
				.replace("/slash", "\\");
		compilerForm.setRequest(code);
		String vars = compilerForm.getVars()
				.replace("/plus", "+")
				.replace("/tab", "\t")
				.replace("/cell", "#")
				.replace("/percent", "%")
				.replace("/and", "&")
				.replace("/lcb", "{")
				.replace("/rcb", "}")
				.replace("/lbb", "[")
				.replace("/rbb", "]")
				.replace("/slash", "\\");

		CompilerEntity compilerEntity;
		BaseCodeRunner codeRunner = null;
		StringBuilder result = new StringBuilder();
		String language = compilerForm.getLanguage();

		boolean correctLanguage = true;

		if (code.length() < 30)
		{
			fillResponse(compilerForm, "Not correct input data", response);
			return null;
		}

		switch (language)
		{
			case "Java":
				if (code.contains("import ") || code.contains("class ") || code.contains("public "))
				{
					codeRunner = new JavaCodeRunner();
				} else
				{
					correctLanguage = false;
				}
				break;
			case "Oberon":
				if (code.contains("MODULE ") || code.contains("BEGIN"))
				{
					codeRunner = new OberonCodeRunner();
				} else
				{
					correctLanguage = false;
				}
				break;
			case "C":
				if (code.contains("#define ") || code.contains("#include "))
				{
					codeRunner = new CCodeRunner();
				} else
				{
					correctLanguage = false;
				}
				break;
			default:
				correctLanguage = false;
		}

		if (!correctLanguage)
		{
			fillResponse(compilerForm, "Not correct language", response);
			return null;
		}

		try
		{
			compilerEntity = codeRunner.start(code, vars, request);
		} catch (CompilerException e)
		{
			Logger.fillLog(e);
			fillResponse(compilerForm, e.getMessage(), response);
			return null;
		}


		if (compilerEntity == null)
		{
			result.append("Ожидаются данные");
		} else
		{
			compilerEntity.getOut().forEach(
					line -> result.append(line.replace("+", "/plus")).append("\r\n")
			);
			compilerForm.setComplete(compilerEntity.isCompleted());
		}

		fillResponse(compilerForm, result.toString(), response);
		return null;
	}

	private void fillResponse(CompilerForm compilerForm, String result, HttpServletResponse response) throws IOException
	{
		compilerForm.setResponse(result);
		PrintWriter writer = response.getWriter();
		writer.print(gson.toJson(compilerForm));
		writer.flush();
		response.setContentType("application/json");
	}
}
