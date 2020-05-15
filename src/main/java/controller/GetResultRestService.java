package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerEntity;
import compiler.lelar.compiler.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import runner.BaseCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetResultRestService extends DispatchAction
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String sessionId = request.getSession().getId();
		Process sessionProcess = BaseCodeRunner.processes.get(sessionId);

		if (sessionProcess != null)
		{
			StringBuilder result = new StringBuilder();
			BaseCodeRunner.getResultFor(sessionId)
					.forEach(line -> result.append(line.replace("+", "\\plus")).append("\r\n"));

			if (sessionProcess.isAlive())
			{
				compilerForm.setComplete(true);
			}

			compilerForm.setResponse(result.toString());

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String jsonString = gson.toJson(compilerForm);

			PrintWriter writer = response.getWriter();
			writer.print(jsonString);
			writer.flush();

			if (!sessionProcess.isAlive())
			{
				sessionProcess.getInputStream().close();
				sessionProcess.getErrorStream().close();
				BaseCodeRunner.processes.remove(sessionId);
			}

		}

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
