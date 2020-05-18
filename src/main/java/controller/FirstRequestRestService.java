package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerEntity;
import compiler.lelar.compiler.CompilerForm;
import exception.CompilerException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import runner.CCodeRunner;
import runner.JavaCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FirstRequestRestService extends BaseRestService
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String code = compilerForm.getRequest()
				.replace("\\plus", "+")
				.replace("\\enter", "\n")
				.replace("\\tab", "\t");
		compilerForm.setRequest(code);
		String vars = compilerForm.getVars()
				.replace("\\plus", "+");

		CompilerEntity compilerEntity;
		BaseCodeRunner codeRunner = null;
		switch (compilerForm.getLanguage())
		{
			case "Java":
				codeRunner = new JavaCodeRunner();
				break;
			case "C":
				codeRunner = new CCodeRunner();
		}


		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try
		{
			compilerEntity = codeRunner.start(code, vars, request);
		} catch (CompilerException e)
		{
			compilerForm.setResponse(e.getMessage());

			String jsonString = gson.toJson(compilerForm);

			PrintWriter writer = response.getWriter();
			writer.print(jsonString);
			writer.flush();
			response.setContentType("application/json");

			return null;
		}

		StringBuilder result = new StringBuilder();

		if (compilerEntity == null)
		{
			result.append("Ожидаются данные");
		} else
		{
			compilerEntity.getOut().forEach(
					line -> result.append(line.replace("+", "\\plus")).append("\r\n")
			);
			compilerForm.setComplete(compilerEntity.isCompleted());
		}
		String resultString = result.toString();
		compilerForm.setResponse(resultString);

		String jsonString = gson.toJson(compilerForm);

		PrintWriter writer = response.getWriter();
		writer.print(jsonString);
		writer.flush();
		response.setContentType("application/json");

		return null;
	}
}
