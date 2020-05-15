package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerEntity;
import compiler.lelar.compiler.CompilerForm;
import exception.CompilerException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import runner.BaseCodeRunner;
import runner.JavaCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileRestService extends DispatchAction
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		StringBuilder code = new StringBuilder(compilerForm.getRequest());
		String vars = compilerForm.getVars().replace("\\plus", "+");

		CompilerEntity compilerEntity;
		BaseCodeRunner codeRunner = new JavaCodeRunner();

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try
		{
			compilerEntity = codeRunner.start(code.toString(), vars, request);
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
			compilerEntity.getOut().forEach(line -> result.append(line.replace("+", "\\plus")).append("\r\n"));
			//compilerEntity.getErrors().forEach(line -> result.append(line.replace("+", "\\plus")).append("\r\n"));
			compilerForm.setComplete(compilerEntity.isCompleted());
		}
		String resultString = result.toString();
		compilerForm.setResponse(resultString);
		//lastResponse.put(request.getSession().getId(), resultString);

		String jsonString = gson.toJson(compilerForm);

		PrintWriter writer = response.getWriter();
		writer.print(jsonString);
		writer.flush();
		response.setContentType("application/json");

		return null;
	}

	private void fixRequestedData(CompilerForm compilerForm)
	{
		Pattern p = Pattern.compile("\u200b|\n$");
		Matcher m = p.matcher(
				compilerForm.getRequest()
						.replace("\u00a0", " ")
						.replace("\\plus", "+"));
		compilerForm.setRequest(m.replaceAll(""));
	}
}
